package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.projects.cd.portal.Homepage;
import com.ipsos.cd.selenium.projects.cd.portal.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Hometest {

    private WebDriver driver;
    private LoginPage loginPage;
    private Homepage homePage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D://chromedriver-win64//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://portal-int.ipsos-cd-dev.com/");

        // Initialize Page Objects
        loginPage = new LoginPage(driver);
        homePage = new Homepage(driver);
    }

    @Test
    public void testLoginAndNavigation() {
        // Perform login
        loginPage.enterEmail("Sharmila@prodifyllp.com");
        loginPage.clickNextButton();
        loginPage.enterPassword("Ipsos@2019");
        loginPage.clickLoginButton();

        // Navigate to active projects page
        homePage.clickSiteNavigationButton();
        homePage.clickActiveProjectsButton();
        homePage.clickActivitiesIcon();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
