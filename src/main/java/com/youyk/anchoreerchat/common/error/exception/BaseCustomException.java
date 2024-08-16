package com.youyk.anchoreerchat.common.error.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public abstract class BaseCustomException extends RuntimeException{

    private final String code;
    private final String message;

    private final List<Object> args;

    public BaseCustomException(final String code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.args = new ArrayList<>();
    }


}
