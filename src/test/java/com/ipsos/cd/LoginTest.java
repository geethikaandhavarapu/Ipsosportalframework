package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
//import org.testng.DriverManager;
import com.ipsos.cd.selenium.projects.cd.portal.Excelutils;
//import com.ipsos.cd.selenium.projects.cd.portal.basetest;

import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseTest {

    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;
    ForgotPasswordPage forgotPasswordPage;
    DashboardPage dashboardPage;
    ProfilePage profilePage;

    String excelFilePath = "D:\\work\\Logindata.xlsx";
    String sheetName = "LoginCredentials";

    @BeforeClass
    public void setUp() throws IOException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        wait = new WebDriverWait(driver, Duration.ofSeconds(150));

        loginPage = new LoginPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        dashboardPage = new DashboardPage(driver);
        profilePage = new ProfilePage(driver);
        Excelutils.setExcelFile("D:\\work\\Logindata.xlsx", "Sheet1");
        driver.get("https://portal-int.ipsos-cd-dev.com/");

    }

    @BeforeMethod
    public void reset() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @Test(priority = 1)
    public void TC_LoginWithValidCredentials() {
        // Get valid email and password from Excel
        String email = Excelutils.getCellData(1 ,1);
        String password = Excelutils.getCellData(1, 2);

        loginPage.enterEmail(email);
        loginPage.clickNextButton();
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        boolean isLoginSuccessful = driver.findElement(By.xpath("//advanced-image[@class='ng-star-inserted']//img")).isDisplayed();
        Assert.assertTrue(isLoginSuccessful, "Login was not successful!");
    }

    @Test(priority = 2)
    public void TC_LoginWithInvalidEmail() {
        try {
            String email = Excelutils.getCellData(2,1 ); // row 2, column 0 for invalid email
            String password = Excelutils.getCellData(2, 2); // row 1, column 1 for valid password

            loginPage.enterEmail(email);
            loginPage.clickNextButton();
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Either the email or password you entered is incorrect.')]")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");
            Assert.assertEquals(errorMessage.getText(), "Either the email or password you entered is incorrect.", "Error message text is incorrect.");
        } catch (TimeoutException e) {
            System.out.println("TimeoutException: Element not found within the specified time: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3)
    public void TC_LoginWithIncorrectPassword() {
        try {
            String email = Excelutils.getCellData(3, 1);
            String password = Excelutils.getCellData(3, 2);

            loginPage.enterEmail(email);
            loginPage.clickNextButton();
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()=\"That password doesn't look right. You can try again 4 more times.\"]")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");
            Assert.assertEquals(errorMessage.getText(), "That password doesn't look right. You can try again 4 more times.", "Error message text is incorrect.");
        } catch (TimeoutException e) {
            System.out.println("TimeoutException: Element not found within the time limit.");
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    public void TC_ForgotPassword() {
        try {
            String email = Excelutils.getCellData(4, 1);

            loginPage.clickForgotPassword();
            forgotPasswordPage.enterForgotPasswordEmail(email);
            forgotPasswordPage.clickForgotPasswordNextButton();

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'A password reset link has been sent')]")));
            System.out.println("Success message: " + successMessage.getText());
            Assert.assertTrue(successMessage.isDisplayed(), "Success message is not displayed.");
            Assert.assertEquals(successMessage.getText(), "A password reset link has been sent to '" + email + "'. This link will expire after 24 hours.", "Success message text is incorrect.");
        } catch (TimeoutException e) {
            System.out.println("TimeoutException: Element not found within the time limit.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 5)
    public void TC_NavigateToDashboard() {
        try {
            // Get valid email and password from Excel
            String email = Excelutils.getCellData(1, 0);
            String password = Excelutils.getCellData(1, 1);

            loginPage.enterEmail(email);
            loginPage.clickNextButton();
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            dashboardPage.navigateToDashboard();
            Assert.assertTrue(dashboardPage.isDashboardTitleDisplayed(), "Dashboard title is not displayed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 6)
    public void TC_Logout() {
        try {
            // Get valid email and password from Excel
            String email = Excelutils.getCellData(1, 0);
            String password = Excelutils.getCellData(1, 1);

            loginPage.enterEmail(email);
            loginPage.clickNextButton();
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            profilePage.clickProfileIcon();
            profilePage.clickLogoutButton();

            Assert.assertTrue(profilePage.isLoginPageDisplayed(), "Login page is not displayed after logout.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

