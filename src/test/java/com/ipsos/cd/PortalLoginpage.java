package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.constants.FrameworkConstants;
import com.ipsos.cd.selenium.projects.cd.portal.CommonPage;
import com.ipsos.cd.selenium.projects.region.CommonPageRegion;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;

import static com.ipsos.cd.selenium.keywords.WebUI.*;
import static com.ipsos.cd.selenium.keywords.WebUI.verifyElementVisible;
import static org.bouncycastle.cms.RecipientId.password;

public class PortalLoginpage extends CommonPage{

    private By emailField = By.xpath("//input[@aria-label='Email']");
    private By nextButton = By.xpath("//button[@aria-label='next']");
    private By passWordField = By.xpath("//input[@aria-label='Password']");
    private By loginButton = By.xpath("//span[normalize-space()='next']");
    private By errorMessage = By.xpath("//div[@class='ng-star-inserted']");
    private By incorrectPasswordError = By.xpath("//div[text()=\"That password doesn't look right. You can try again 4 more times.\"]");
    private By forgotPasswordLink = By.xpath("//span[@class='smx-col-primary smx-ptr']");
    private By forgotPasswordEmailField = By.xpath("//input[@aria-label='Email address']");
    private By forgotPasswordNextButton = By.xpath("//button[@aria-label='next']");
    private By forgotPasswordSuccessMessage = By.xpath("//div[@class='ng-star-inserted']");
    private By dashboardLink = By.xpath("//a[@aria-label='dashboard']");
    private By profileIcon = By.xpath("//advanced-image[@class='ng-star-inserted']//img");
    private By profilepage =By.xpath("//a[@aria-label='goto profile']");
    private By logoutButton = By.xpath("//button[@aria-label='log off']");
    private By loginPage = By.xpath("//h1[text()='Login']");
    private By activeProject = By.xpath("//a[@aria-label='active']//span[@class='home__col__button--text']");
    private By activitiesIcon = By.xpath("//a[@fxlayout='column']//mat-icon[@role='img']");
    private By contactUs = By.xpath("//a[@aria-label='contact support']//span[@class='mat-button-wrapper']");

    public void LoginPage(){
        openWebsite(FrameworkConstants.CD_PORTAL_URL);

    }
    public void LoginWithValidCredentials(String email, String password) {
        LoginPage();
        // openWebsite(FrameworkConstants.URL_IPSOS_MEMBER);                                 //URL_CMS_USER
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
    }
    public void LoginWithInvalidEmail(String email, String password) {
        LoginPage();                              //URL_CMS_USER
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
        verifyElementVisible(errorMessage, "Either the email or password you entered is incorrect.");
    }
    public void loginFailWithIncorrectPassword(String email, String password) {
        LoginPage();                              //URL_CMS_USER
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
        verifyElementVisible(errorMessage, "That password doesn't look right. You can try again 4 more times.");

    }
    public void ForgotPassword(String email){
        LoginPage();
        sleep(2);
        clickElement(forgotPasswordLink);
        clickElement(forgotPasswordEmailField);
        setText(forgotPasswordEmailField, email);
        clickElement(forgotPasswordNextButton);
        verifyElementVisible(forgotPasswordSuccessMessage, "A password reset link has been sent to geethu1913@gmail.com.\n" +
                "This link will expire after 24 hours.");

    }
    public void NavigateToDashboard(String email,String password){
        LoginPage();
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
        clickElement(dashboardLink);
    }
    public void ProfilePage(String email,String password){
        LoginPage();
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
        clickElement(profileIcon);
        clickElement(profilepage);
        clickElement(profileIcon);
        clickElement(logoutButton);
    }
    public void Activeproject(String email,String password){
        LoginPage();
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        setText(passWordField, password);
        waitForPageLoaded();
        clickElement(loginButton);
        clickElement(activeProject);
        clickElement(activitiesIcon);
    }
    public void loginFailWithEmailNull() {
        LoginPage();
        clickElement(emailField);

    }
    public void loginFailWithNullPassword(String email){
        LoginPage();
        clickElement(emailField);
        setText(emailField, email);
        clickElement(nextButton);
        clickElement(passWordField);
        waitForPageLoaded();
    }
    public void Contactus(){
        LoginPage();
        sleep(3);
        clickElement(contactUs);
        sleep(5);
    }



}
