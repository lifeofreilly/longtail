package com.gmail.lifeofreilly.longtail;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class HARProcessor {
    private final static Logger log = Logger.getLogger(HARProcessor.class);
    private static final String HAR_EXTENSION = ".har";
    private final File tempDirectory;
    private final File archiveDirectory;
    private final Results resultsDB;

    public HARProcessor(final File tempDirectory, final File archiveDirectory, final Results resultsDB) {
        this.tempDirectory = tempDirectory;
        this.archiveDirectory = archiveDirectory;
        this.resultsDB = resultsDB;
    }

    public int processFiles() {
        int filesProcessed = 0;
        File[] directoryListing = tempDirectory.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.getName().endsWith(HAR_EXTENSION)) {
                    log.info("Processing: " + child.getName());
                    saveMetadata(child);
                    archiveFile(child);
                    filesProcessed++;
                }
            }
        } else {
            log.error("There was a problem reading .har files from the temp directory: " + tempDirectory.getAbsolutePath());
            return 0;
        }

        return filesProcessed;
    }

    private void saveMetadata(File child) {
        HAR har = new HAR(child);
        Long status = har.getStatus();

        if(status == 200) {
            log.debug("Saving metadata: " + har.getMetaData());
            resultsDB.addResult(har.getMetaData());
        } else {
            log.error("Metadata not saved. The request to url: " + har.getURL() + " returned status code: " +  status);
        }
    }

    private void archiveFile(File child) {
        try {
            log.info("Archiving file: " + child.getName());
            FileUtils.copyFile(child, new File(archiveDirectory + "/" + child.getName()));
            FileUtils.forceDelete(child);
        } catch (IOException e) {
            log.error("An Exception occurred while attempting to copy the file to the archive directory.");
        }
    }

}
