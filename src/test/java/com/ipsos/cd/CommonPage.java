package com.ipsos.cd.selenium.projects.cd.portal;

import com.ipsos.cd.selenium.projects.cd.portal.PortalLoginpage;

import com.ipsos.cd.selenium.projects.region.members.pages.activities.BasicDiscussionActivity;

public class CommonPage {

    public PortalLoginpage loginPage;


    public PortalLoginpage getPortalLoginpage() {
        if (loginPage == null) {
            loginPage = new PortalLoginpage();

        }
        return loginPage;


    }
}
