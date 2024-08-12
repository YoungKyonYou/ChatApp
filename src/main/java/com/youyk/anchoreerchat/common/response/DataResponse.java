package com.youyk.anchoreerchat.common.response;


import com.youyk.anchoreerchat.common.error.ErrorResponse;
import com.youyk.anchoreerchat.common.error.constant.ErrorConstants;
import lombok.Builder;

@Builder
public record DataResponse(
        Object data,
        ErrorResponse error

) {

    public static DataResponse of(final Object data, final ErrorResponse error) {
        return new DataResponse(data, error);
    }

    public static DataResponse from(final Object data) {
        return new DataResponse(data, ErrorConstants.NO_ERROR.getError());
    }
}
