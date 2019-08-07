package steps;

import base.Helper;
import com.trendkite_automation.Base.Page;
import cucumber.TestContext;
import cucumber.api.DataTable;
import cucumber.api.java.en.*;
import enums.Context;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;

import java.util.List;
import java.util.Map;

public class DashboardSteps {

    private DashboardPage dashboardPage;
    private final WebDriver _driver;
    private TestContext _context;

    public DashboardSteps(TestContext context) {
        _context = context;
        _driver = _context.driver;
        _context.page = new Page(_driver);
        dashboardPage = _context.page.loadPage(DashboardPage.class);
    }

    @When("^I create a new Dashboard$")
    public void iCreateANewDashboard(DataTable table) {
        List<Map<String, String>> list = table.asMaps(String.class, String.class);
        String time = " " + Helper.currentTime();
        String dashboardName = list.get(0).get("dashboardName") + time;
        String searchName = list.get(0).get("searchName") + time;
        String searchText = list.get(0).get("searchText");
        dashboardPage.createNewDashboard(dashboardName, searchName, searchText);
        _context.setContext(Context.DASHBOARD_NAME, dashboardName);
        _context.setContext(Context.SEARCH_NAME, searchName);
        _context.setContext(Context.SEARCH_TEXT, searchText);
    }

    @Then("^I validate created Dashboard exists under MY DASHBOARDS section$")
    public void iValidateCreatedDashboardExistsUnderMYDASHBOARDSSection() {

        String dashboardName = _context.getContext(Context.DASHBOARD_NAME).toString();//_context.scenarioContext.getContext(Context.DASHBOARD_NAME).toString();
        Assert.assertTrue("Dashboard with name: '" + dashboardName + "' was not created",
                dashboardPage.checkCurrentDashboardByName(dashboardName));
    }

}
