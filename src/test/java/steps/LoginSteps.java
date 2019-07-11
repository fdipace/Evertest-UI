package steps;

import base.BaseUtil;
import base.Helper;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataProvider.ConfigFileReader;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import pages.LoginPage;

import java.util.List;
import java.util.Map;

public class LoginSteps extends BaseUtil {
    private BaseUtil base;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private Helper helper;
    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigFileReader configFileReader;

    public LoginSteps(BaseUtil base) {
        this.base = base;
        loginPage = new LoginPage(base.Driver);
        dashboardPage = new DashboardPage(base.Driver, base);
        helper = new Helper(base);
        driver = base.Driver;
        wait = base.Wait;
        configFileReader = new ConfigFileReader();
    }

    @Given("^I navigate to Trendkite login page$")
    public void iNavigateToTrendkiteLoginPage() {
        String env = configFileReader.getTestNGParameterValue("env");
        helper.goToPage(configFileReader.getApplicationUrl(env));
    }

    @And("^I click the Sign in button$")
    public void iClickTheSignInButton() {
        loginPage.signInButton.click();
    }

    @Then("^I validate Dashboard page is displayed$")
    public void iValidateDashboardPageIsDisplayed() {
        Assert.assertTrue("Dashboard page was not reached.", helper.urlContains(dashboardPage.dashboardPageURL));
        Assert.assertTrue("Dashboard page was not reached.", wait.until(ExpectedConditions.visibilityOf(dashboardPage.newDashboardButton)).isDisplayed());
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
        Assert.assertTrue("Login error message was not displayed.", wait.until(ExpectedConditions.visibilityOf(loginPage.incorrectUsrOrPwdMessage)).isDisplayed());
        Assert.assertTrue("Expected message is: 'The username or password you entered is invalid' but actually was: '" + loginPage.getIncorrectUsrOrPwdMessage() + "'", loginPage.getIncorrectUsrOrPwdMessage().contains("The username or password you entered is invalid"));
        //Assert.assertTrue("Expected message is: 'You should all get a Screenshot out of this!!!' but actually was: '" + loginPage.getIncorrectUsrOrPwdMessage() + "'", loginPage.getIncorrectUsrOrPwdMessage().contains("You should all get a Screenshot out of this!!!"));
    }
}
