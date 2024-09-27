package com.ipsos.cd.selenium.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JiraFields {
    SPRINT_ID("customfield_10007") , TASK("");
    private final String fieldName;
}
