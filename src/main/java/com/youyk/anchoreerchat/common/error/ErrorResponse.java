package com.youyk.anchoreerchat.common.error;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String code,
        String message
) {
    public static ErrorResponse of(final String code, final String message) {
        return new ErrorResponse(code, message);
    }
}
