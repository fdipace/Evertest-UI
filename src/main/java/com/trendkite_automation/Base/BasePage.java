package com.trendkite_automation.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * @author ignflo
 */
public abstract class BasePage<T extends BasePage<T>> extends Page {

    protected WebDriver driver;
    private long LOAD_TIMEOUT = 30;


    public BasePage(WebDriver _driver)
    {
        super(_driver);
        this.driver=_driver;
    }

    protected boolean loaded(WebElement element)
    {
        WebDriverWait wait = new WebDriverWait(driver,LOAD_TIMEOUT);
        return wait.until((ExpectedCondition<Boolean>) wd ->
                element.isDisplayed());
    }


}
