package com.gmail.lifeofreilly.longtail;

/*
* On load is triggered after the entire document, images and all, are loaded.
* Content Load events that are triggered after the HTML file is delivered, but before external files like images are loaded.
* http://www.softwareishard.com/har/viewer/
*/

import java.io.File;

public class Longtail {
    private static final String FIREBUG_PLUGIN_LOCATION = "src/main/resources/firebug-2.0.2-fx.xpi";
    private static final String NETEXPORT_PLUGIN_LOCATION = "src/main/resources/netExport-0.9b6.xpi";
    private static final String TEMP_DIR = "/Users/sreilly/longtail/temp"; //must be an absolute path
    private static final String ARCHIVE_DIR = "/Users/sreilly/longtail/archive";  //must be an absolute path
    private static final String MONGO_ADDRESS = "localhost";
    private static final String MONGO_DB_NAME = "mongo";
    private static final String MONGO_COLLECTION_NAME = "har";
    private static final int MONGO_PORT = 27017;
    private static final int SAMPLE_SIZE = 1;

    public static void main( String[] args )
    {
        String[] pagesToProfile = {
                "https://www.google.com/?gws_rd=ssl",
                "http://www.bing.com"
        };

        profilePages(pagesToProfile);
        processResults();
    }

    private static void profilePages(String[] pages) {
        PerformanceDriver performanceDriver = new PerformanceDriver(FIREBUG_PLUGIN_LOCATION, NETEXPORT_PLUGIN_LOCATION, TEMP_DIR);
        PageProfiler profiler = new PageProfiler(performanceDriver);

        for (String page : pages) {
            profiler.profilePage(page, SAMPLE_SIZE);
        }
        performanceDriver.shutdown();
    }

    private static void processResults() {
        File tempDir = new File(TEMP_DIR);
        File archiveDir = new File(ARCHIVE_DIR);

        Results resultsDB = new ResultsMongo(MONGO_ADDRESS, MONGO_PORT, MONGO_DB_NAME, MONGO_COLLECTION_NAME);
        HARProcessor harProcessor = new HARProcessor(tempDir, archiveDir, resultsDB);

        int filesProcessed = harProcessor.processFiles();
        System.out.println("Total Pages Profiled: " + filesProcessed);
    }


}
