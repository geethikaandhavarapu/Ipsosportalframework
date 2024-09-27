package com.ipsos.cd.selenium.projects.cd.portal;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;


    private By emailField = By.xpath("//input[@aria-label='Email']");
    private By nextButton = By.xpath("//button[@aria-label='next']");
    private By passwordField = By.xpath("//input[@aria-label='Password']");
    private By loginButton = By.xpath("//span[normalize-space()='next']");
    private By incorrectPasswordError = By.xpath("//div[text()=\"That password doesn't look right. You can try again 4 more times.\"]");
    private By forgotPasswordLink = By.xpath("//span[@class='smx-col-primary smx-ptr']");
    private By forgotPasswordEmailField = By.xpath("//input[@aria-label='Email address']");
    private By forgotPasswordNextButton = By.xpath("//button[@aria-label='next']");
    private By forgotPasswordSuccessMessage = By.xpath("//div[@class='ng-star-inserted']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isIncorrectPasswordErrorDisplayed() {
        return driver.findElement(incorrectPasswordError).isDisplayed();
    }

    public String getIncorrectPasswordErrorText() {
        return driver.findElement(incorrectPasswordError).getText();
    }

    public void clickForgotPassword() {
        driver.findElement(forgotPasswordLink).click();
    }

    public void enterForgotPasswordEmail(String email) {
        driver.findElement(forgotPasswordEmailField).sendKeys(email);
    }

    public void clickForgotPasswordNextButton() {
        driver.findElement(forgotPasswordNextButton).click();
    }

    public boolean isForgotPasswordSuccessMessageDisplayed() {
        return driver.findElement(forgotPasswordSuccessMessage).isDisplayed();
    }

    public String getForgotPasswordSuccessMessageText() {
        return driver.findElement(forgotPasswordSuccessMessage).getText();
    }
}
