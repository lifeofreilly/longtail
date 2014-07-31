package com.gmail.lifeofreilly.longtail;

import org.apache.log4j.Logger;

public class SeleniumUtils {
    private final static Logger log = Logger.getLogger(PageProfiler.class);
    private final static int STANDARD_WAIT = 10000;

    static void seleniumWait() {
        try {
            Thread.sleep(STANDARD_WAIT);
        } catch (InterruptedException ex) {
            log.error("InterruptedException thrown: " + ex);
            Thread.currentThread().interrupt();
        }
    }
}
