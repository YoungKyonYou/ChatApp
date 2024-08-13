package com.youyk.anchoreerchat.common.response;


import com.youyk.anchoreerchat.common.error.ErrorResponse;
import com.youyk.anchoreerchat.common.error.constant.ErrorConstants;
import lombok.Builder;

@Builder
public record DataResponse<T>(
        T data,
        ErrorResponse error

) {

    public static <T> DataResponse<T> of(final T data, final ErrorResponse error) {
        return new DataResponse<T>(data, error);
    }

    public static <T> DataResponse<T> from(final T data) {
        return new DataResponse<T>(data, ErrorConstants.NO_ERROR.getError());
    }
}
