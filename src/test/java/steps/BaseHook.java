package steps;

import com.trendkite_automation.Drivers.DriverManager;
import org.testng.annotations.AfterTest;

/**
 * @author ignflo
 */
public class BaseHook {

    private DriverManager driverManager;


    protected DriverManager getDriverManager() {
        return driverManager;
    }

    protected DriverManager setDriverManager(final DriverManager driverManager) {
        if(getDriverManager() == null)
            this.driverManager = driverManager;
        return this.driverManager;
    }

}
