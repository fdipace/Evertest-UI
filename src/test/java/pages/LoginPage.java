package pages;

<<<<<<< HEAD
import com.trendkite_automation.Base.BasePage;
=======
import base.BaseUtil;
import base.Helper;
import cucumber.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BasePage<LoginPage> {

    public LoginPage(WebDriver driver){
        super(driver);
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseUtil {

    private BaseUtil base;
    private Helper helper;
    private WebDriver driver;
    private WebDriverWait wait;
    private TestContext testContext;

    public LoginPage(WebDriver driver, BaseUtil base) {
        PageFactory.initElements(driver, this);
        this.base = base;
        helper = new Helper(base);
    }

    @FindBy(how = How.CSS, using = ".visible-lg * input[name=username]")
    public WebElement usernameInput;

    @FindBy(how = How.CSS, using = ".visible-lg * input[name=password]")
    public WebElement passwordInput;

    @FindBy(how = How.CSS, using = ".visible-lg * input[name=signInSubmitButton]")
    public WebElement signInButton;

    @FindBy(how = How.CSS, using = "div.visible-lg * form > p[id=loginErrorMessage]")
    public WebElement incorrectUsrOrPwdMessage;

    public void Login(String username, String password)
    {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public void typeUsername(String username)
    {
        usernameInput.sendKeys(username);
    }

    public void typePassword(String password)
    {
        passwordInput.sendKeys(password);
    }

    public String getIncorrectUsrOrPwdMessage()
    {
        return incorrectUsrOrPwdMessage.getText();
    }


    @Override
    public boolean isLoaded() throws Error {
        return loaded(usernameInput);
    }
}