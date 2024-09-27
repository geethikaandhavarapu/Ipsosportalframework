package com.ipsos.cd.selenium.projects.cd.portal;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Homepage {

    private WebDriver driver;

    // Locators
    private By siteNavigationButton = By.xpath("//mat-toolbar[@class='mat-toolbar home__container__toolbar mat-toolbar-single-row']//button[@aria-label='site navigation']");
    private By activeProjectsButton = By.xpath("//a[@aria-label='active']//span[@class='home__col__button--text']");
    private By activitiesIcon = By.xpath("//a[@fxlayout='column']//mat-icon[@role='img']");

    // Constructor
    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void clickSiteNavigationButton() {
        WebElement siteNavigationElement = driver.findElement(siteNavigationButton);
        siteNavigationElement.click();
    }

    public void clickActiveProjectsButton() {
        WebElement activeProjectsElement = driver.findElement(activeProjectsButton);
        activeProjectsElement.click();
    }

    public void clickActivitiesIcon() {
        WebElement activitiesIconElement = driver.findElement(activitiesIcon);
        activitiesIconElement.click();
    }
}

