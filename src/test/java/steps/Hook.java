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
        System.out.println("\nPreparing Browser...");
        driver = driverManager.get();
        _context.driver = driver;
        driver.navigate().to("https://dev.trendkite.com");
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
