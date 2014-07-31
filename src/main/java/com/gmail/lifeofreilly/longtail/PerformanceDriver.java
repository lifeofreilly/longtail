package com.gmail.lifeofreilly.longtail;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PerformanceDriver {
    private final static Logger log = Logger.getLogger(PerformanceDriver.class);
    private WebDriver driver;
    private final String firebugPluginLocation;
    private final String netExportPluginLocation;
    private final String tempDirectory;
    private final FirefoxProfile profile = new FirefoxProfile();

    public PerformanceDriver(final String firebugPluginLocation, final String netExportPluginLocation, final String tempDirectory) {
        this.firebugPluginLocation = firebugPluginLocation;
        this.netExportPluginLocation = netExportPluginLocation;
        this.tempDirectory = tempDirectory;
        setupWebDriver();
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void setupWebDriver() {
        addExtensions();
        setFirefoxPreferences();
        setFirebugPreferences();
        setNetExportPreferences();
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
        //Allow time for plugins to load
        SeleniumUtils.seleniumWait();
    }

    private void setNetExportPreferences() {
        profile.setPreference("extensions.firebug.netexport.alwaysEnableAutoExport", true);
        profile.setPreference("extensions.firebug.netexport.showPreview", false);
        profile.setPreference("extensions.firebug.netexport.defaultLogDir", tempDirectory);
    }

    private void setFirebugPreferences() {
        profile.setPreference("extensions.firebug.currentVersion", "2.0.2");
        profile.setPreference("extensions.firebug.allPagesActivation", "on");
        profile.setPreference("extensions.firebug.defaultPanelName", "net");
        profile.setPreference("extensions.firebug.net.enableSites", true);
    }

    private void setFirefoxPreferences() {
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setEnableNativeEvents(false);
        profile.setPreference("app.update.enabled", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
    }

    private void addExtensions() {
        log.info("Expecting Firebug Plugin at" + firebugPluginLocation);
        log.info("Expecting NetExport Plugin at" + firebugPluginLocation);
        File firebug = new File(firebugPluginLocation);
        File netexport = new File(netExportPluginLocation);

        try {
            profile.addExtension(firebug);
            profile.addExtension(netexport);

        } catch (IOException ex) {
            log.fatal("A failure occurred while attempting to add the extensions: " + ex);
        }
    }

    public void shutdown() {
        driver.close();
    }
}
