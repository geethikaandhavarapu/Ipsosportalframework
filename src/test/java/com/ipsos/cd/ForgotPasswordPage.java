package com.ipsos.cd.selenium.projects.cd.portal;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {

    WebDriver driver;
    WebDriverWait wait;

    private By forgotPasswordLink = By.xpath("//span[@class='smx-col-primary smx-ptr']");
    private By forgotPasswordEmailField = By.xpath("//input[@aria-label='Email address']");
    private By forgotPasswordNextButton = By.xpath("//button[@aria-label='next']");
    private By forgotPasswordSuccessMessage = By.xpath("//div[@class='ng-star-inserted']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordLink)).click();
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

    public String getSuccessMessageText() {
        return driver.findElement(forgotPasswordSuccessMessage).getText();
    }
}



