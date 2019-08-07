package com.trendkite_automation.dataProvider;

import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class ConfigFileReader {
    private static Properties properties;
    private final String propertyFilePath= "configs//config.properties";

    public ConfigFileReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + propertyFilePath);
        }
    }

    public String getTestNGParameterValue(String paramName){
        String paramValue = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(paramName);
        if(paramValue!= null) return paramValue;
        else throw new RuntimeException("Parameter value not specified in the testng.xml file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitWaitTime");
        if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
        else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }

    public String getApplicationUrl(String env) {
        String url = "";
        if (env.equals("dev"))
        { url = properties.getProperty("devUrl");}
        else if (env.equals("staging"))
        {url = properties.getProperty("stagingUrl");}
        if(url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    public String getChromeDriverMaximizedOption() {
        String url = properties.getProperty("chromeDriverMaximizedOption");
        if(url != null) return url;
        else throw new RuntimeException("Driver Option not specified in the Configuration.properties file.");
    }


    public String getChromeDriverPath(){
        String driverPath = properties.getProperty("chromeDriverPath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public String getFirefoxDriverPath(){
        String driverPath = properties.getProperty("firefoxDriverPath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public String getInternetExplorerDriverPath(){
        String driverPath = properties.getProperty("internetExplorerDriverPath");
        if(driverPath!= null) return driverPath;
        else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public static URL getGridUrl()
    {
        URL gridUrl=null;
        final String propertyValue = properties.getProperty("gridUrl");
        if (propertyValue != null) {
            try {
                gridUrl= new URL(propertyValue);
            } catch (final MalformedURLException e) {
                throw new IllegalArgumentException(
                        "The value of the environment variable 'gridUrl' is not a valid URL.", e);
            }
        }
        return gridUrl;
    }


}
