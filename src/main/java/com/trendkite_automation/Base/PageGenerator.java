package com.trendkite_automation.Base;


/**
 * @author ignflo
 */
public interface PageGenerator {

    <TPage extends BasePage> TPage loadPage (Class<TPage> pageClass);
    <TPage extends BasePage> TPage getPage (Class<TPage> pageClass);
    <TPage extends BasePage> TPage waitToLoad();
    boolean isLoaded();
}
