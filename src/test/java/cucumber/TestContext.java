package cucumber;

import com.trendkite_automation.Base.Page;
import enums.Context;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class TestContext {

    public WebDriver driver;
    public Page page;
    private Map<String, Object> scenarioContext;

    public void setContext(Context key, Object value) {
        scenarioContext.put(key.toString(), value);
    }

    public Object getContext(Context key){
        return scenarioContext.get(key.toString());
    }

    public Boolean isContains(Context key){
        return scenarioContext.containsKey(key.toString());
    }
}
