package pages;

import base.BaseUtil;
import base.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DashboardPage {
    private WebDriver driver;
    private Actions actions;
    private BaseUtil base;
    private Helper helper;
    private WebDriverWait wait;
    public DashboardPage(WebDriver driver, BaseUtil base) {
        PageFactory.initElements(driver, this);
        this.base = base;
        helper = new Helper(base);
        wait = base.Wait;
        driver = base.Driver;
        actions = new Actions(base.Driver);
    }

    //----------------------------------------------------------------------------
    //Homepage
    //----------------------------------------------------------------------------
    @FindBy(how = How.ID, using = "dashboard-page")
    public WebElement dashboardHomepage;
    @FindBy(how = How.ID, using = "create-new-dashboard-button")
    public WebElement newDashboardButton;
    @FindBy(how = How.ID, using = "dashboard-edit-name")
    public WebElement dashboardNameField;
    @FindBy(how = How.ID, using = "custom-dashboard-li")
    public WebElement customNewDashboardButton;
    @FindBy(how = How.ID, using = "my-dashboard-list")
    public WebElement myDashboardList;
    @FindBy(how = How.ID, using = "shared-with-me-folder")
    public WebElement sharedDashboardFolder;
    @FindBy(how = How.ID, using = "create-report")
    public WebElement createReportButton;
    @FindBy(how = How.CSS, using = ".tk-icon-users.user-icon")
    public WebElement userIcon;
    @FindBy(how = How.CSS, using = ".logout")
    public WebElement logoutButton;

    //----------------------------------------------------------------------------
    //New Dashboard Modal
    //----------------------------------------------------------------------------

    @FindBy(how = How.CSS, using = "[id=add-dash-modal]")
    public WebElement addDashModal;
    @FindBy(how = How.ID, using = "add-dash-title")
    public WebElement addDashTitle;
    @FindBy(how = How.ID, using = "add-dash-next-button")
    public WebElement addDashNextButton;
    @FindBy(how = How.ID, using = "search-selector-create-new-search-button")
    public WebElement searchSelectorCreateNewSearchButton;
    @FindBy(how = How.ID, using = "boolean-editor-title-field")
    public WebElement searchTitleField;
    @FindAll({@FindBy(css = ".dashboard-list-item-container > li > span > i:last-child")})
    public List<WebElement> myDashboardItems;
    @FindBy(how = How.CSS, using = ".modal-footer>button[id=delete-Dashboard-button]")
    public WebElement confirmDeleteButton;
    @FindBy(how = How.ID, using = "share-dashboard-confirmation-button")
    public WebElement confirmShareButton;
    @FindBy(how = How.CSS, using = ".modal-body-inner-column-description.modal-body-inner-column-description--prompt")
    public WebElement confirmShareWarningText;
    @FindBy(how = How.ID, using = "shared-dashboard-list")
    public WebElement sharedDashboardList;

    //----------------------------------------------------------------------------
    //Delete Actions Modal
    //----------------------------------------------------------------------------
    @FindBy(how = How.CSS, using = ".modal.fade.ng-isolate-scope")
    public WebElement deleteDashboardModal;
    @FindBy(how = How.CSS, using = ".modal-body")
    public WebElement modalBody;
    @FindBy(how = How.CSS, using = ".modal-footer")
    public WebElement modalFooter;

    //Strings
    public String dashboardPageURL = "/dashboard";
    public String newSearchTextEditor = "div.ace_content";
    public String newSearchTextEditorInput = "textarea.ace_text-input";

    public void createNewDashboard(String dashboardName, String searchName, String searchText)
    {
        while (myDashboardItems.size() > 0)
        {
            deleteDashboardFirstItem();
            myDashboardItems.size();
        }
        helper.waitForClickability(newDashboardButton);
        newDashboardButton.click();
        helper.waitForVisibility(customNewDashboardButton);
        customNewDashboardButton.click();
        helper.waitForVisibility(addDashModal);
        addDashTitle.sendKeys(dashboardName);
        helper.waitForVisibility(addDashNextButton);
        addDashNextButton.click();
        helper.waitForVisibility(searchSelectorCreateNewSearchButton);
        searchSelectorCreateNewSearchButton.click();

        WebElement editor = addDashModal.findElement(By.cssSelector(newSearchTextEditor));
        WebElement editorInput = addDashModal.findElement(By.cssSelector(newSearchTextEditorInput));
        actions.doubleClick(editor).perform();
        editorInput.sendKeys(searchText);

        searchTitleField.sendKeys(searchName);
        addDashNextButton.click();
        addDashNextButton.click();
        addDashNextButton.click();
        addDashNextButton.click();
    }

    public void deleteDashboardFirstItem()
    {
        helper.waitForClickability(myDashboardItems.get(0));
        myDashboardItems.get(0).click();
        helper.waitForVisibility(deleteDashboardModal);
        helper.waitForVisibility(modalFooter);
        helper.waitForClickability(confirmDeleteButton);
        confirmDeleteButton.click();
        helper.waitForInvisibility(modalFooter);
    }

    public boolean checkCurrentDashboardByName(String dashboardName)
    {
        helper.waitForVisibility(dashboardNameField);
        boolean bool = dashboardNameField.getAttribute("value").contains(dashboardName);
        return bool;
    }
}
