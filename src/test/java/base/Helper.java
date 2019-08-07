package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper extends BaseUtil {
    private BaseUtil base;
    private WebDriver driver;
    private WebDriverWait wait;

    public Helper(BaseUtil base) {
        this.base = base;
        driver = base.Driver;
        wait = base.Wait;
    }

        String mainAppId = "tk-app";

        public void goToPage(String url)
        {
            driver.navigate().to(url);
        }


    public void waitForElementToBeClickable(WebElement element)
    {
       // wait.until(ExpectedConditions.elementToBeClickable(element));
    }

        public boolean urlContains(String url)
        {
            boolean contains;
            if(driver.getCurrentUrl().contains(url))
            {
                contains = true;
            }
            else
            {
                contains = false;
            }

            return contains;
        }

        public void waitForPageToLoad()
        {
            WebElement ele = driver.findElement(By.id(mainAppId));
            //wait.until(ExpectedConditions.visibilityOf(ele));
        }

        public void sleep(int milliseconds)
        {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Wait until Invisibility of element is completed
        public void waitForInvisibility(WebElement element){
        try{
            //wait.until(ExpectedConditions.invisibilityOf(element));
        }catch(Exception e){}
        }

    //Wait until Visibility of element is completed
    public void waitForVisibility(WebElement element){
        try{
            //wait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception e){}
    }

    //Wait until Visibility of element is completed
    public void waitForClickability(WebElement element){
        try{
            //wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch(Exception e){}
    }

    public static String currentTime(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
