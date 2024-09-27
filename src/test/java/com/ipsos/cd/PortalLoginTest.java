package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.annotations.FrameworkAnnotation;
import com.ipsos.cd.selenium.projects.cd.portal.portalbasetest;
import com.ipsos.cd.selenium.constants.FrameworkConstants;
import com.ipsos.cd.selenium.enums.AuthorType;
import com.ipsos.cd.selenium.enums.CategoryType;
import com.ipsos.cd.selenium.helpers.ExcelHelpers;
import org.testng.annotations.Test;


public class PortalLoginTest extends portalbasetest {

    @FrameworkAnnotation( category = {CategoryType.SANITY, CategoryType.REGRESSION}, author = {AuthorType.Santosh})
    @Test(testName = "Login check ", description = "Details of case")
    public void TC_LoginWithValidCredentials() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().LoginWithValidCredentials(excel.getCellData(5, "email"), excel.getCellData(5, "password"));
    }

    @Test(priority = 2)
    public void TC_LoginWithInvalidEmail() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().LoginWithInvalidEmail(excel.getCellData(6, "email"), excel.getCellData(6, "password"));
    }

    @Test(priority = 3)
    public void TC_loginFailWithIncorrectPassword() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().loginFailWithIncorrectPassword(excel.getCellData(7, "email"), excel.getCellData(7, "password"));
    }
    @Test(priority = 4)
    public void TC_ForgotPassword(){
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().ForgotPassword(excel.getCellData(8, "email"));

    }
    @Test(priority =5)
    public void TC_loginFailWithEmailNull() {
        getPortalLoginpage().loginFailWithEmailNull();
    }
    @Test(priority = 6)
    public void TC_loginFailWithNullPassword() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOS_LOGIN, "Login");
        getPortalLoginpage().loginFailWithNullPassword(excel.getCellData(7, "email"));
    }
    @Test(priority = 7)
    public void TC_ClickOnContactUsText() {
        getPortalLoginpage().Contactus();
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOS_LOGIN, "Login");

    }


}


