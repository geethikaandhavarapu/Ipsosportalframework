package com.ipsos.cd.selenium.enums;

/**
 * A Java Enum is a special Java type used to define collections of constants.
 */
public enum CategoryType {
    INTEGRATION, // To ensure that different modules or services work together as expected.
    FUNCTIONAL, // To validate the software system against functional requirements/specifications.
    SYSTEM, // To evaluate the complete system's compliance with its specified requirements.
    REGRESSION, // To ensure that new code changes have not adversely affected existing functionalities.
    SMOKE, // Basic functionality of complete application
    SANITY // To verify that a specific functionality or bug fix works correctly.
}