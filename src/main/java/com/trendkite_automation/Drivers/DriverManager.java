package com.trendkite_automation.Drivers;

import com.google.inject.Provider;
import org.openqa.selenium.WebDriver;


/**
 * @author ignflo
 */
public interface DriverManager extends Provider<WebDriver> {

     void quitDriver();
     void startService();
     void stopService();
     WebDriver createDriver();
     WebDriver get();
}
