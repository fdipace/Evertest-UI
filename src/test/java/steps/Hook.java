package steps;

import com.trendkite_automation.Base.Page;
import com.trendkite_automation.Drivers.DriverManager;
import com.trendkite_automation.Drivers.DriverManagerFactory;
import com.trendkite_automation.dataProvider.ConfigFileReader;
import cucumber.TestContext;
import cucumber.api.Scenario;
import cucumber.api.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Hook extends BaseHook {

    public ConfigFileReader configFileReader;

    private WebDriver driver;
    private DriverManager driverManager;
    private TestContext _context;


    public Hook(TestContext context) {
        configFileReader = new ConfigFileReader();
        _context = context;
        DriverManagerFactory.setGridUrl(configFileReader.getGridUrl());
        driverManager=setDriverManager(DriverManagerFactory.getManager(DriverManagerFactory.getDriverManagerType()));
        _context.page = new Page(driver);
    }

    @Before
    public void InitializeTest(Scenario scenario) {
        String browser = configFileReader.getTestNGParameterValue("browserName");
        Boolean headless = Boolean.valueOf(configFileReader.getTestNGParameterValue("headless"));
        System.out.println("\nPreparing Browser...");

        driver = driverManager.get();
        _context.driver = driver;
        driver.navigate().to("https://dev.trendkite.com");
        System.out.println("\nBrowser '"+browser+"' is ready!");
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
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (browser.equals("Internet Explorer"))
            {
                driver.navigate().to(url);
            }
            System.out.print("\nFailed Scenario Name: " + "'" + scenario.getName() + "'");
            driver.manage().deleteAllCookies();
            driver.quit();
            System.out.print("\nBrowser is closed!!!\n---------------------------------------------------------------");
        } else {
            System.out.print("\nInitiating Teardown...");
            System.out.print("\nClosing Browser '"+browser+"'...");
            if (browser.equals("Internet Explorer"))
            {
                driver.navigate().to(url);
            }
            System.out.print("\nPassed Scenario Name: " + "'" + scenario.getName() + "'");
            System.out.println("Quitting WebDriver");
            driverManager.quitDriver();
            driverManager.stopService();
            System.out.print("\nBrowser is closed!!!\n---------------------------------------------------------------\n");
        }
    }
}
