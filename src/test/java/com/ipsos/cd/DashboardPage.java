package com.ipsos.cd.selenium.projects.cd.portal;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    private By dashboardLink = By.xpath("//a[text()='Dashboard']");
    private By dashboardTitle = By.xpath("//h2[text()='Dashboard Overview']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateToDashboard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardLink)).click();
    }

    public boolean isDashboardTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardTitle)).isDisplayed();
    }
}

