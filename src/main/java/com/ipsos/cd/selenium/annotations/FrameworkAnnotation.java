package com.ipsos.cd.selenium.annotations;

import com.ipsos.cd.selenium.enums.AuthorType;
import com.ipsos.cd.selenium.enums.CategoryType;
import com.ipsos.cd.selenium.enums.FeatureType;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//This is an Custom Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrameworkAnnotation {

    // This is not a method
    public AuthorType[] author();

    // public String[] category();
    public CategoryType[] category();

    public FeatureType[] feature() default {FeatureType.GENERAL};

    public String testCaseId() default "bad practiced test id";

    public String jiraId() default StringUtils.EMPTY;

}
