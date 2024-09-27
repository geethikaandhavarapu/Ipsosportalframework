package com.ipsos.cd.selenium.helpers;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import lombok.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class MarkupHtml implements Markup {
    private static final long serialVersionUID = 548723938072445261L;

    private String preText;
    private String url;
    private String name;
    private ExtentColor color;

    public String getMarkup() {
        if (url == null || url.isEmpty())
            return "";
        String textColor = color != ExtentColor.WHITE ? "white-text" : "black-text";
        String lhs = "<span>" + preText + "</span> <a href='" + url + "' target='_blank' class='badge " + textColor + " " + String.valueOf(color).toLowerCase() + "'>";
        String rhs = "</a>";
        return lhs + name + rhs;
    }

    public static class HtmlBuilder {
        private String url = "";
        private String preText = "";
        private String name = "";
        private ExtentColor color = ExtentColor.BLUE;
    }
}
