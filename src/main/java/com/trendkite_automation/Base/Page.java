package com.trendkite_automation.Base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author ignflo
 */
public class Page implements PageGenerator {
    private WebDriver driver;

    public Page(WebDriver driver){
        this.driver = driver;
    }

    /***
     * Get the page and wait for load
     * @param pageClass
     * @param <TPage>
     * @return
     */
    @Override
    public  <TPage extends BasePage> TPage loadPage (Class<TPage> pageClass) {
        try {
            TPage page;
            page = getPage(pageClass);
            page.waitToLoad();
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /***
     * Get the page
     * @param pageClass
     * @param <TPage>
     * @return
     */
    @Override
    public  <TPage extends BasePage> TPage getPage (Class<TPage> pageClass) {
        try {
            TPage page;
            page = PageFactory.initElements(driver,  pageClass);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /***
     * Wait until DOM is loaded
     */
    protected void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }


    /***
     * Basically same implementation than ccc-ui
     * @param <T>
     * @return
     */
    public <T extends BasePage> T waitToLoad()
    {
        try{
            if(isLoaded())
                return (T) this;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        try{
            waitForPageToLoad();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return (T) this;
    }

    /***
     * Override it with necessary condition
     * @return
     * @throws Error
     */
    public boolean isLoaded() throws Error
    {
        return true;
    }

}
