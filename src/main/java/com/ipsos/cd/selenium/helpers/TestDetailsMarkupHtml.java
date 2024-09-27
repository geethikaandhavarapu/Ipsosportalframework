package com.ipsos.cd.selenium.helpers;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class TestDetailsMarkupHtml implements Markup {
    private static final long serialVersionUID = 548723938072445261L;

    private String preText;
    private String tcLink;
    private String tcId;
    private String jiraIssueLink;
    private String jiraIssueId;
    private ExtentColor color;

    public String getMarkup() {
        if (jiraIssueLink == null || jiraIssueLink.isEmpty())
            return "";
        String textColor = color != ExtentColor.WHITE ? "white-text" : "black-text";
        String lhs = "<span>" + preText + "</span> <a href='" + tcLink + "' target='_blank' class='badge " + textColor + " " + String.valueOf(ExtentColor.ORANGE).toLowerCase() + "'>" + tcId + "</a>";
        String rhs = "<a href='" + jiraIssueLink + "' target='_blank' class='badge " + textColor + " " + String.valueOf(color).toLowerCase() + "'>" + jiraIssueId + "</a>";

        return lhs + "; JiraId : " + rhs;
    }

    public static class TestDetailsMarkupHtmlBuilder {
        private String preText = "";
        private String tcLink = "";
        private String tcId = "";
        private String jiraIssueLink = "";
        private String jiraIssueId = "";
        private ExtentColor color = ExtentColor.BLUE;
    }
}
