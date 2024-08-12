package com.youyk.anchoreerchat.common.error.constant;


import com.youyk.anchoreerchat.common.error.ErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorConstants {
    NO_ERROR(null);

    private final ErrorResponse error;
}
