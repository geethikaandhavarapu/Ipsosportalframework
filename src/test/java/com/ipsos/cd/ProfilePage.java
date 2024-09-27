package com.ipsos.cd.selenium.projects.cd.portal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    WebDriver driver;
    WebDriverWait wait;

    private By profileIcon = By.xpath("//img[@alt='Profile']");
    private By logoutButton = By.xpath("//button[text()='Logout']");
    private By loginPage = By.xpath("//h1[text()='Login']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickProfileIcon() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileIcon)).click();
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).click();
    }

    public boolean isLoginPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginPage)).isDisplayed();
    }
}
