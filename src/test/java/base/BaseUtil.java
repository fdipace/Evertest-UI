package base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseUtil {

    public WebDriver Driver;
    public WebDriverWait Wait;
    public ChromeOptions chromeOptions;
    public FirefoxOptions firefoxOptions;
    public InternetExplorerOptions ieOptions;
}
