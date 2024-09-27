package com.ipsos.cd.selenium.projects.cd.portal;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private DriverManager() {
        super();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public static void quit() {
        if (com.ipsos.cd.selenium.driver.DriverManager.getDriver() != null){
            com.ipsos.cd.selenium.driver.DriverManager.getDriver().quit();
        }
    }

//    public static String getInfo() {
//        Capabilities cap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
//        String browserName = cap.getBrowserName();
//        String platform = cap.getPlatformName().toString();
//        String version = cap.getBrowserVersion();
//        return String.format("browser: %s v: %s platform: %s", browserName, version, platform);
//    }
}
