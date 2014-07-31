package com.gmail.lifeofreilly.longtail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Unit test for simple Longtail.
 */
public class HARProcessorTest extends TestCase {

    private static final String TEST_FILES_DIR = "src/test/resources/testFiles/";
    private static final String TEMP_DIR = "src/test/resources/temp/";

    //Contents in this directory will be deleted prior to test execution
    private static final String ARCHIVE_DIR = "src/test/resources/archive";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HARProcessorTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HARProcessorTest.class );
    }

    public void test_extractHATDataAndArchive_Success()
    {
        File testFileDirectory = new File(TEST_FILES_DIR);
        File tempDirectory = new File(TEMP_DIR);
        File archiveDirectory = new File(ARCHIVE_DIR);
        Results resultsDatabase = new ResultsStub();

        //Clean archive directory
        try {
            FileUtils.cleanDirectory(archiveDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            fail("cleanDirectory operation failed. Aborting test.");
            return;

        }

        //Seed the temp directory with test files
        HARProcessorTest.copyFiles(testFileDirectory, tempDirectory);

        //Process the test files
        HARProcessor harProcessor = new HARProcessor(tempDirectory, archiveDirectory, resultsDatabase);
        int filesProcessed = harProcessor.processFiles();
        assertEquals(5, filesProcessed);

    }

    private static void copyFiles(final File source, final File destination) {
        try {
            FileUtils.copyDirectory(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
