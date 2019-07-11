package steps;

import base.BaseUtil;
import cucumber.api.Scenario;
import cucumber.api.java.*;
import dataProvider.ConfigFileReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        System.out.println("\nPreparing Browser...");
        if (browser.equals("Chrome")) {
            System.setProperty(configFileReader.useChromeDriver(), configFileReader.getChromeDriverPath());
            base.Options = new ChromeOptions();
            base.Options.addArguments(configFileReader.getChromeDriverMaximizedOption());
            base.Driver = new ChromeDriver(base.Options);
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
        }

        else if (browser.equals("Firefox")) {
            System.setProperty(configFileReader.useFirefoxDriver(), configFileReader.getFirefoxDriverPath());
            base.Driver = new FirefoxDriver();
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
        }
        else if (browser.equals("Internet Explorer")){
            System.setProperty(configFileReader.useInternetExplorerDriver(), configFileReader.getInternetExplorerDriverPath());
            base.ieOptions = new InternetExplorerOptions();
            base.ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
            base.Driver = new InternetExplorerDriver(base.ieOptions);
            base.Wait = new WebDriverWait(base.Driver, configFileReader.getImplicitlyWait());
            base.Driver.manage().deleteAllCookies();
            base.Driver.manage().window().maximize();
        }
        System.out.println("\nBrowser '"+browser+"' is ready!");
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
