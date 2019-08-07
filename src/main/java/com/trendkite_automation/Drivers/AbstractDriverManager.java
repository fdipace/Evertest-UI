package com.trendkite_automation.Drivers;

import org.openqa.selenium.WebDriver;

import java.net.URL;

/**
 * @author ignflo
 * Thread-Safe
 */
public abstract class AbstractDriverManager implements DriverManager {

    private ThreadLocal<WebDriver> driver = new InheritableThreadLocal<>();
    private URL gridUrl;

    AbstractDriverManager(final URL gridURL) {
        this.gridUrl = gridURL;
    }

    protected URL getGridUrl() {
        return gridUrl;
    }

    /***
     * Close driver
     */
    @Override
    public void quitDriver() {
        if (null != driver) {
            driver.remove();
            System.out.println("WebDriver quit");
            driver = null;
        }
    }

    /***
     * Get web driver Singleton
     * @return
     */
    @Override
    public WebDriver get() {

        if (driver.get()==null) {
            startService();
            WebDriver _driver=createDriver();
            setDriver(_driver);
        }
        return driver.get();
    }

    /***
     * Set driver to ThreadLocal driver
     * @param driver
     */
    final void setDriver(final WebDriver driver) {
        this.driver.set(driver);
    }

}
