package com.trendkite_automation.Drivers;

import com.google.inject.Provider;
import com.trendkite_automation.dataProvider.ConfigFileReader;

import java.net.URL;

/**
 * Driver Manager Factory
 * @author ignflo
 */
public final class DriverManagerFactory implements Provider<DriverManager> {

    private DriverManager webDriverManager;
    private static URL gridUrl;

    public static void setGridUrl(final URL gridUrl) {
        DriverManagerFactory.gridUrl = gridUrl;
    }

    /***
     * Create Driver Manager
     * @param type
     * @return
     */
    public static AbstractDriverManager getManager(final DriverType type) {

        final AbstractDriverManager driverManager;

        if (type == null) {
            throw new IllegalArgumentException("Requested DriverType is not recognised");
        }

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager(true, false, gridUrl);
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager(true, false, gridUrl);
            default:
                throw new UnsupportedOperationException(
                        String.format("Requested Browser '%s' has not yet been implemented.",
                                type));
        }
        return driverManager;

    }

    /***
     * Get brower type to create manager
     * @return
     */
    public static DriverType getDriverManagerType()
    {
        ConfigFileReader configFileReader = new ConfigFileReader();
        String driverManager = configFileReader.getTestNGParameterValue("browserName");
        switch (driverManager){
            case "Chrome":
                return DriverType.CHROME;
            case "Firefox":
                return DriverType.FIREFOX;
            default:
                throw new UnsupportedOperationException(
                        String.format("Requested Browser '%s' has not yet been implemented.",
                                driverManager));
        }
    }

    /***
     * Get Driver Manager
     * @return
     */
    @Override
    public DriverManager get() {
        if (webDriverManager == null) {
            webDriverManager = getManager(getDriverManagerType());
        }
        return webDriverManager;
    }
}
