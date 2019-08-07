package com.trendkite_automation.Drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author ignflo
 * Chrome Driver Manager
 */
public class ChromeDriverManager extends AbstractDriverManager implements DriverManager {

    private ChromeDriverService chromeService;
    private final File chromedriverExe;
    private final boolean isLocal;

    private final boolean isHeadless;

    ChromeDriverManager(final boolean isLocal,
                        final boolean isHeadless, final URL gridUrl) {
        super(gridUrl);
        WebDriverManager.chromedriver().setup();
        String path = WebDriverManager.chromedriver().getBinaryPath();
        chromedriverExe = new File(path);
        this.isLocal = isLocal;
        this.isHeadless = isHeadless;
    }

    /***
     * Manage a persistent instance of the ChromeDriver server
     */
    @Override
    public void startService() {
        if (!isLocal) {
            return;
        }
        if (null == chromeService) {
            try {
                chromeService = new ChromeDriverService.Builder()
                        .usingAnyFreePort()
                        .usingDriverExecutable(chromedriverExe)
                        .build();
                chromeService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("ChromeDriverService Started");
        }
    }

    /***
     * Stop service
     */
    @Override
    public void stopService() {
        if (null != chromeService && chromeService.isRunning()) {
            chromeService.stop();
            System.out.println("ChromeDriverService Stopped");
        }
    }

    /***
     * Create chrome web driver instance
     * @return
     */
    @Override
    public WebDriver createDriver() {
        WebDriver driver;
        final ChromeOptions options = new ChromeOptions()
                .setHeadless(isHeadless)
                .addArguments("--start-maximized");
        if (!isLocal) {
            driver=new RemoteWebDriver(getGridUrl(), options);
        } else {
            driver=new ChromeDriver(chromeService, options);

        }
        System.out.println("Chrome Driver Created");
        return driver;
    }
}
