package com.ipsos.cd.selenium.projects.cd.portal;


import com.ipsos.cd.selenium.driver.DriverManager;
import com.ipsos.cd.selenium.driver.TargetFactory;
import com.ipsos.cd.selenium.helpers.PropertiesHelpers;
import com.ipsos.cd.selenium.keywords.WebUI;
import com.ipsos.cd.selenium.projects.cd.portal.CommonPage;
import com.ipsos.cd.selenium.listeners.TestListener;
import com.ipsos.cd.selenium.projects.region.CommonPageRegion;
import com.ipsos.cd.selenium.projects.region.members.pages.activities.BasicDiscussionActivity;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.annotations.*;

@Listeners(com.ipsos.cd.selenium.listeners.TestListener.class)
public class portalbasetest extends CommonPage {

    @Parameters("BROWSER")
    @BeforeMethod(alwaysRun = true)
    public void createDriver(@Optional("chrome") String browser) {
        WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        WebUI.stopSoftAssertAll();
        DriverManager.quit();
    }

    public WebDriver createBrowser(@Optional("chrome") String browser) {
        PropertiesHelpers.loadAllFiles();
        WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
        return DriverManager.getDriver();
    }



}
