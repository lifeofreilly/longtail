package com.gmail.lifeofreilly.longtail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * Unit test for simple Longtail.
 */
public class HARTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HARTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HARTest.class );
    }

    public void test_getOnContentLoad_Success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals((long) 2207, har.getOnContentLoad());
    }

    public void test_getOnLoad_success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals((long) 2603, har.getOnLoad());
    }

    public void test_getTitle_success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals("Google", har.getTitle());
    }

    public void test_getURL_success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals("https://www.google.com/?gws_rd=ssl", har.getURL());
    }

    public void test_getStartedDateTime_success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals("2014-07-28T20:43:48.533-07:00", har.getStartedDateTime());
    }

    public void test_getStatus_success()
    {
        HAR har = new HAR(new File("src/test/resources/testFiles/www.google.com+2014-07-28+20-43-52.har"));
        assertEquals((long) 200, har.getStatus());
    }

    public void test_newHar_BadPath_IllegalArgumentException(){
        String errorMessage = null;
        try {
            HAR har = new HAR(new File("foobar"));
            fail("IllegalArgumentException was not thrown as expected.");
        } catch (IllegalArgumentException ex) {
            errorMessage = ex.getMessage();
        }
        assertEquals("The path supplied must point to an existing .har file.", errorMessage);
    }

}
