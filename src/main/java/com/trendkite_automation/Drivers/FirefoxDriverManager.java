package com.trendkite_automation.Drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author ignflo
 */
public class FirefoxDriverManager extends AbstractDriverManager implements DriverManager{

    private GeckoDriverService firefoxService;
    private final File firefoxDriverExe;

    private final boolean isLocal;

    private final boolean isHeadless;

    FirefoxDriverManager(final boolean isLocal,
                         final boolean isHeadless, final URL gridUrl) {
        super(gridUrl);
        WebDriverManager.firefoxdriver().setup();
        String path = WebDriverManager.firefoxdriver().getBinaryPath();
        firefoxDriverExe = new File(path);
        this.isLocal = isLocal;
        this.isHeadless = isHeadless;
    }

    @Override
    public void startService() {
        if (!isLocal) {
            return;
        }
        if (null == firefoxService) {
            try {
                firefoxService = new GeckoDriverService.Builder()
                        .usingAnyFreePort()
                        .usingDriverExecutable(firefoxDriverExe)
                        .build();
                firefoxService.start();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            System.out.println("FirefoxDriverService Started");
        }
    }

    @Override
    public void stopService() {
        if (null != firefoxService && firefoxService.isRunning()) {
            firefoxService.stop();
            System.out.println("FirefoxDriverService Stopped");
        }
    }

    @Override
    public WebDriver createDriver() {
        WebDriver driver;
        final FirefoxOptions options = new FirefoxOptions()
                .setHeadless(isHeadless)
                .addArguments("--start-maximized");
        if (!isLocal) {
            driver=new RemoteWebDriver(getGridUrl(), options);
        } else {
            driver= new FirefoxDriver(firefoxService, options);
        }
        System.out.println("Firefox Driver Created");
        return driver;
    }
}
