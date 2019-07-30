package steps;

import base.BaseUtil;
import cucumber.api.Scenario;
import cucumber.api.java.*;
import dataProvider.ConfigFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Hook extends BaseUtil {

    private BaseUtil base;
    private ConfigFileReader configFileReader;

    public Hook(BaseUtil base) {

        this.base = base;
        configFileReader = new ConfigFileReader();
    }

    @Before
    public void InitializeTest(Scenario scenario) {
        String browser = configFileReader.getTestNGParameterValue("browserName");
        Boolean headless = Boolean.valueOf(configFileReader.getTestNGParameterValue("headless"));
        System.out.println("\nPreparing Browser...");
        if (browser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            base.chromeOptions = new ChromeOptions();
            base.chromeOptions.addArguments(configFileReader.getWebDriverWindowSizeOption());
            base.chromeOptions.addArguments(configFileReader.getWebDriverMaximizedOption());
            base.chromeOptions.setHeadless(headless);
            base.Driver = new ChromeDriver(base.chromeOptions);
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
        }

        else if (browser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            base.firefoxOptions = new FirefoxOptions();
            base.firefoxOptions.addArguments(configFileReader.getWebDriverWindowSizeOption());
            base.firefoxOptions.addArguments(configFileReader.getWebDriverMaximizedOption());
            base.firefoxOptions.setHeadless(headless);
            base.Driver = new FirefoxDriver(base.firefoxOptions);
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
        }
        else if (browser.equals("Internet Explorer")){
            WebDriverManager.iedriver().setup();
            base.ieOptions = new InternetExplorerOptions();
            base.ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            base.Driver = new InternetExplorerDriver(base.ieOptions);
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
            base.Driver.manage().window().maximize();
        }
        if (headless.equals(true))
        {
            System.out.println("\nBrowser '"+browser+"' in Headless mode is ready!");
        }
        else
            {
                System.out.println("\nBrowser '"+browser+"' is ready!");
            }

        System.out.print("\nExecuting Scenario: " + "'" + scenario.getName() + "'");
    }

    @After
    public void TearDownTest(Scenario scenario) {
        String browser = configFileReader.getTestNGParameterValue("browserName");
        String env = configFileReader.getTestNGParameterValue("env");
        String url = configFileReader.getApplicationUrl(env)+"logout";

        if (scenario.isFailed()) {
            System.out.print("\nInitiating Teardown...");
            System.out.print("\nClosing Browser '"+browser+"'...");
            try {
                final byte[] screenshot = ((TakesScreenshot) base.Driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (browser.equals("Internet Explorer"))
            {
                base.Driver.navigate().to(url);
            }
            System.out.print("\nFailed Scenario Name: " + "'" + scenario.getName() + "'");
            base.Driver.manage().deleteAllCookies();
            base.Driver.quit();
            System.out.print("\nBrowser is closed!!!\n---------------------------------------------------------------");
        } else {
            System.out.print("\nInitiating Teardown...");
            System.out.print("\nClosing Browser '"+browser+"'...");
            if (browser.equals("Internet Explorer"))
            {
                base.Driver.navigate().to(url);
            }
            System.out.print("\nPassed Scenario Name: " + "'" + scenario.getName() + "'");
            base.Driver.manage().deleteAllCookies();
            base.Driver.quit();
            System.out.print("\nBrowser is closed!!!\n---------------------------------------------------------------\n");
        }
    }
}
