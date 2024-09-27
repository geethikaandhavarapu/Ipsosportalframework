package com.ipsos.cd.selenium.exceptions;

public class TargetNotValidException extends IllegalStateException {

    public TargetNotValidException(String target) {
        super(String.format("Target %s not supported. Use either local or gird", target));
    }

}
