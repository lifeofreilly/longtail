package com.gmail.lifeofreilly.longtail;

import org.openqa.selenium.WebDriver;

public class PageProfiler {
    private final WebDriver driver;

    PageProfiler(final PerformanceDriver performanceDriver) {
        this.driver = performanceDriver.getWebDriver();
    }

    public void profilePage(final String url, final int repeat) {
        for (int i = 0; i < repeat; i++) {
            driver.get(url);
            //allow time for har to be written
            SeleniumUtils.seleniumWait();
        }
    }
}
