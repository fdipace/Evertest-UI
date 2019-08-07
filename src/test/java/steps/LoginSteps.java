package steps;

import com.trendkite_automation.Base.Page;
import com.trendkite_automation.dataProvider.ConfigFileReader;
import cucumber.TestContext;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LoginPage;

import java.util.List;
import java.util.Map;

public class LoginSteps{
    private LoginPage loginPage;
    private ConfigFileReader configFileReader;
    private final WebDriver _driver;
    private TestContext _context;
    private DashboardPage dashboardPage;

    public LoginSteps(TestContext context) {
        _context = context;
        _driver = _context.driver;
    public LoginSteps(BaseUtil base) {
        this.base = base;
        loginPage = new LoginPage(base.Driver, base);
        dashboardPage = new DashboardPage(base.Driver, base);
        helper = new Helper(base);
        driver = base.Driver;
        wait = base.Wait;
        configFileReader = new ConfigFileReader();
        _context.page = new Page(_driver);
        loginPage = _context.page.loadPage(LoginPage.class);
        dashboardPage = _context.page.getPage(DashboardPage.class);
    }

    @Given("^I navigate to Trendkite login page$")
    public void iNavigateToTrendkiteLoginPage() {
        String env = configFileReader.getTestNGParameterValue("env");
        _driver.navigate().to(configFileReader.getApplicationUrl(env));
    }

    @And("^I click the Sign in button$")
    public void iClickTheSignInButton() {

        loginPage.signInButton.click();
    }

    @Then("^I validate Dashboard page is displayed$")
    public void iValidateDashboardPageIsDisplayed() {
         dashboardPage.waitToLoad();
         Assert.assertTrue("Dashboard page was not reached.", _driver.getCurrentUrl().contains(dashboardPage.dashboardPageURL));
         Assert.assertTrue("Dashboard page was not reached.", dashboardPage.newDashboardButton.isDisplayed());
    }

    @When("^I enter username ([^\"]*) and password ([^\"]*)$")
    public void iEnterUsernameAndPassword(String username, String password) {
        loginPage.typeUsername(username);
        loginPage.typePassword(password);
    }

    @And("^I login with specific user and password$")
    public void iLoginUsingAdminUser(DataTable table) {
        List<Map<String, String>> list = table.asMaps(String.class, String.class);
        for(int i=0; i<list.size(); i++) {
            loginPage.Login(list.get(i).get("username"), list.get(i).get("password"));
        }
    }

    @Then("^I validate Incorrect User or Password message is displayed$")
    public void iValidateIncorrectUserOrPasswordMessageIsDisplayed() {

        dashboardPage.waitToLoad();
        Assert.assertTrue("Dashboard page was not reached.", _driver.getCurrentUrl().contains(dashboardPage.dashboardPageURL));
        Assert.assertTrue("Dashboard page was not reached.", dashboardPage.newDashboardButton.isDisplayed());
        //Assert.assertFalse("Login error message was not displayed.", loginPage.incorrectUsrOrPwdMessage.isDisplayed());
        //Assert.assertFalse("Expected message is: 'The username or password you entered is invalid' but actually was: '" + loginPage.getIncorrectUsrOrPwdMessage() + "'", loginPage.getIncorrectUsrOrPwdMessage().contains("The username or password you entered is invalid"));
        //Assert.assertFalse("Expected message is: 'You should all get a Screenshot out of this!!!' but actually was: '" + loginPage.getIncorrectUsrOrPwdMessage() + "'", loginPage.getIncorrectUsrOrPwdMessage().contains("You should all get a Screenshot out of this!!!"));

        Assert.assertTrue("Login error message was not displayed.", wait.until(ExpectedConditions.visibilityOf(loginPage.incorrectUsrOrPwdMessage)).isDisplayed());
        Assert.assertTrue("Expected message is: 'The username or password you entered is invalid' but actually was: '" + loginPage.getIncorrectUsrOrPwdMessage() + "'", loginPage.getIncorrectUsrOrPwdMessage().contains("The username or password you entered is invalid"));

    }
}
