package steps;

import base.BaseUtil;
import base.Helper;
import cucumber.TestContext;
import cucumber.api.DataTable;
import cucumber.api.java.en.*;
import enums.Context;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import pages.LoginPage;

import java.util.List;
import java.util.Map;

public class DashboardSteps extends BaseUtil {
    private BaseUtil base;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private Helper helper;
    private WebDriver driver;
    private WebDriverWait wait;
    private TestContext testContext;

    public DashboardSteps(BaseUtil base) {
        this.base = base;
        loginPage = new LoginPage(base.Driver);
        dashboardPage = new DashboardPage(base.Driver, base);
        helper = new Helper(base);
        driver = base.Driver;
        wait = base.Wait;
        testContext = new TestContext(base);
    }

    @When("^I create a new Dashboard$")
    public void iCreateANewDashboard(DataTable table) {
        List<Map<String, String>> list = table.asMaps(String.class, String.class);
        String time = " " + helper.currentTime();
        String dashboardName = list.get(0).get("dashboardName") + time;
        String searchName = list.get(0).get("searchName") + time;
        String searchText = list.get(0).get("searchText");
        dashboardPage.createNewDashboard(dashboardName, searchName, searchText);
        testContext.scenarioContext.setContext(Context.DASHBOARD_NAME, dashboardName);
        testContext.scenarioContext.setContext(Context.SEARCH_NAME, searchName);
        testContext.scenarioContext.setContext(Context.SEARCH_TEXT, searchText);
    }

    @Then("^I validate created Dashboard exists under MY DASHBOARDS section$")
    public void iValidateCreatedDashboardExistsUnderMYDASHBOARDSSection() {

        String dashboardName = testContext.scenarioContext.getContext(Context.DASHBOARD_NAME).toString();
        Assert.assertTrue("Dashboard with name: '" + dashboardName + "' was not created",
                dashboardPage.checkCurrentDashboardByName(dashboardName));
    }

}
