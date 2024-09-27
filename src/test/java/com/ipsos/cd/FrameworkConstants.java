/*
 * Copyright (c) 2022 Santosh Kumar Talachutla
 * Automation Framework Selenium
 */
// Ipsosportal frameworkconstants
package com.ipsos.cd.selenium.constants;

import com.ipsos.cd.selenium.helpers.Helpers;
import com.ipsos.cd.selenium.helpers.PropertiesHelpers;
import com.ipsos.cd.selenium.utils.ReportUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    static {
        PropertiesHelpers.loadAllFiles();
    }

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String PROJECT_PATH = Helpers.getCurrentDir();
    public static final String EXCEL_DATA_FILE_PATH = PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
    public static final String JSON_DATA_FILE_PATH = PropertiesHelpers.getValue("JSON_DATA_FILE_PATH");
    // public static final String EXCEL_CMS_LOGIN = PropertiesHelpers.getValue("EXCEL_CMS_LOGIN");
    public static final String EXCEL_IPSOS_LOGIN = PropertiesHelpers.getValue("EXCEL_IPSOS_LOGIN");
    public static final String EXCEL_IPSOSPORTAL_LOGIN = PropertiesHelpers.getValue("EXCEL_IPSOSPORTAL_LOGIN");
    public static final String EXCEL_CMS_DATA = PropertiesHelpers.getValue("EXCEL_CMS_DATA");
    public static final String EXCEL_CMS_PRODUCTS_USER = PropertiesHelpers.getValue("EXCEL_CMS_PRODUCTS_USER");

    public static final String BROWSER = PropertiesHelpers.getValue("BROWSER");
    public static final String CD_MEMBER_URL = PropertiesHelpers.getValue("CD_MEMBER_URL");
    public static final String CD_PORTAL_URL = PropertiesHelpers.getValue("CD_PORTAL_URL");
    public static final String REMOTE_URL = PropertiesHelpers.getValue("REMOTE_URL");
    public static final String REMOTE_PORT = PropertiesHelpers.getValue("REMOTE_PORT");
    public static final String REMOTE_GRID_USER_NAME = PropertiesHelpers.getValue("REMOTE_GRID_USER_NAME");
    public static final String REMOTE_GRID_PASSCODE = PropertiesHelpers.getValue("REMOTE_GRID_PASSCODE");

    public static final String PROJECT_NAME = PropertiesHelpers.getValue("PROJECT_NAME");
    public static final String REPORT_TITLE = PropertiesHelpers.getValue("REPORT_TITLE");
    public static final String EXTENT_REPORT_NAME = PropertiesHelpers.getValue("EXTENT_REPORT_NAME");
    public static final String EXTENT_REPORT_FOLDER = PropertiesHelpers.getValue("EXTENT_REPORT_FOLDER");
    public static final String EXPORT_VIDEO_PATH = PropertiesHelpers.getValue("EXPORT_VIDEO_PATH");
    public static final String EXPORT_CAPTURE_PATH = PropertiesHelpers.getValue("EXPORT_CAPTURE_PATH");
    public static final String SEND_REPORT_TO_TELEGRAM = PropertiesHelpers.getValue("SEND_REPORT_TO_TELEGRAM");
    public static final String TELEGRAM_TOKEN = PropertiesHelpers.getValue("TELEGRAM_TOKEN");
    public static final String TELEGRAM_CHATID = PropertiesHelpers.getValue("TELEGRAM_CHATID");
    public static final String AUTHOR = PropertiesHelpers.getValue("AUTHOR");
    public static final String TARGET = PropertiesHelpers.getValue("TARGET");
    public static final String HEADLESS = PropertiesHelpers.getValue("HEADLESS");
    public static final String OVERRIDE_REPORTS = PropertiesHelpers.getValue("OVERRIDE_REPORTS");
    public static final String OPEN_REPORTS_AFTER_EXECUTION = PropertiesHelpers.getValue("OPEN_REPORTS_AFTER_EXECUTION");
    public static final String SEND_EMAIL_TO_USERS = PropertiesHelpers.getValue("SEND_EMAIL_TO_USERS");
    public static final String SCREENSHOT_PASSED_TCS = PropertiesHelpers.getValue("SCREENSHOT_PASSED_TCS");
    public static final String SCREENSHOT_FAILED_TCS = PropertiesHelpers.getValue("SCREENSHOT_FAILED_TCS");
    public static final String SCREENSHOT_SKIPPED_TCS = PropertiesHelpers.getValue("SCREENSHOT_SKIPPED_TCS");
    public static final String SCREENSHOT_ALL_STEPS = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEPS");
    public static final String ZIP_FOLDER = PropertiesHelpers.getValue("ZIP_FOLDER");
    public static final String ZIP_FOLDER_PATH = PropertiesHelpers.getValue("ZIP_FOLDER_PATH");
    public static final String ZIP_FOLDER_NAME = PropertiesHelpers.getValue("ZIP_FOLDER_NAME");
    public static final String VIDEO_RECORD = PropertiesHelpers.getValue("VIDEO_RECORD");
    public static final String LOCATE = PropertiesHelpers.getValue("LOCATE");
    public static final String RETRY_TEST_FAIL = PropertiesHelpers.getValue("RETRY_TEST_FAIL");

    public static final int WAIT_DEFAULT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_DEFAULT"));
    public static final int WAIT_IMPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_IMPLICIT"));
    public static final int WAIT_EXPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_EXPLICIT"));
    public static final int WAIT_PAGE_LOADED = Integer.parseInt(PropertiesHelpers.getValue("WAIT_PAGE_LOADED"));
    public static final int WAIT_SLEEP_STEP = Integer.parseInt(PropertiesHelpers.getValue("WAIT_SLEEP_STEP"));
    public static final String ACTIVE_PAGE_LOADED = PropertiesHelpers.getValue("ACTIVE_PAGE_LOADED");

    public static final String JIRA_BOARD = PropertiesHelpers.getValue("JIRA_BOARD");
    public static final String TEST_CASES_LINK = PropertiesHelpers.getValue("TEST_CASES_LINK");
    public static final String JIRA_URL = PropertiesHelpers.getValue("allure.jira.url");
    public static final String JIRA_USERNAME = PropertiesHelpers.getValue("allure.jira.username");
    public static final String JIRA_API_TOKEN = PropertiesHelpers.getValue("allure.jira.password");
    public static final String JIRA_PROJECT_KEY = PropertiesHelpers.getValue("allure.jira.project.key");
    public static final String BUILD_NO = PropertiesHelpers.getValue("BUILD_NO");
    public static final String TEST_REPORT_BOARD = PropertiesHelpers.getValue("TEST_REPORT_BOARD");

    public static final String JIRA_MANAGEMENT_ENABLE = StringUtils.isEmpty(BUILD_NO) ? NO : PropertiesHelpers.getValue("JIRA_MANAGEMENT_ENABLE");

    public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
    public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
    public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator + EXTENT_REPORT_FILE_NAME;

    //Zip file for Report folder
    public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";


    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";

    /* ICONS - START */

    public static final String ICON_SMILEY_PASS = "<i class='fa fa-smile-o' style='font-size:24px'></i>";
    public static final String ICON_SMILEY_SKIP = "<i class=\"fas fa-frown-open\"></i>";
    public static final String ICON_SMILEY_FAIL = "<i class='fa fa-frown-o' style='font-size:24px'></i>";

    public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";

    public static final String ICON_BROWSER_OPERA = "<i class=\"fa fa-opera\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";

    public static final String ICON_Navigate_Right = "<i class='fa fa-arrow-circle-right' ></i>";
    public static final String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
    public static final String ICON_BUG = "<i class='fa fa-bug' ></i>";
    /* style="text-align:center;" */

    public static final String ICON_CAMERA = "<i class=\"fa fa-camera\" aria-hidden=\"true\"></i>";

    public static final String ICON_BROWSER_PREFIX = "<i class=\"fa fa-";
    public static final String ICON_BROWSER_SUFFIX = "\" aria-hidden=\"true\"></i>";
    /* ICONS - END */

    public static String getExtentReportFilePath() {
        if (EXTENT_REPORT_FILE_PATH.isEmpty()) {
            EXTENT_REPORT_FILE_PATH = ReportUtils.createExtentReportPath();
        }
        return EXTENT_REPORT_FILE_PATH;
    }

}
