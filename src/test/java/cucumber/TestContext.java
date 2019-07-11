package cucumber;

import base.BaseUtil;
import org.openqa.selenium.WebDriver;

public class TestContext extends BaseUtil {
    private BaseUtil base;
    private WebDriver driver;
    public ScenarioContext scenarioContext;

    public TestContext(BaseUtil base) {
        this.base = base;
        driver = base.Driver;
        scenarioContext = new ScenarioContext();
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
