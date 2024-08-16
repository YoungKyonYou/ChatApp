package com.youyk.anchoreerchat.common.error.exception;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;

public class DomainException extends BaseCustomException{
    private final String code;

    private final List<Object> args;

    public DomainException(final String code, final String message, final Object... args) {
        super(code, String.format(message, args));
        this.code = code;
        this.args = Arrays.asList(args);
    }
}
