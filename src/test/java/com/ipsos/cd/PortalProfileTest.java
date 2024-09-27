package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.annotations.FrameworkAnnotation;

import com.ipsos.cd.selenium.projects.cd.portal.portalbasetest;
import com.ipsos.cd.selenium.constants.FrameworkConstants;
import com.ipsos.cd.selenium.enums.AuthorType;
import com.ipsos.cd.selenium.enums.CategoryType;
import com.ipsos.cd.selenium.helpers.ExcelHelpers;
import org.testng.annotations.Test;
public class PortalProfileTest extends portalbasetest{
    @FrameworkAnnotation( category = {CategoryType.SANITY, CategoryType.REGRESSION}, author = {AuthorType.Santosh})
    @Test(testName = "Login check ", description = "Details of case")
    public void TC_NavigateToDashboard() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().NavigateToDashboard(excel.getCellData(5, "email"), excel.getCellData(5, "password"));
    }

    @Test(priority = 2)
    public void TC_ProfilePage() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().ProfilePage(excel.getCellData(5, "email"), excel.getCellData(5, "password"));
    }
    @Test(priority = 3)
    public void TC_Activeproject() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_IPSOSPORTAL_LOGIN, "Login");
        getPortalLoginpage().Activeproject(excel.getCellData(5, "email"), excel.getCellData(5, "password"));
    }

}
