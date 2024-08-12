package com.youyk.anchoreerchat.common.response.constant;



import com.youyk.anchoreerchat.common.response.DataResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataConstants {
    NO_DATA(null);

    private final DataResponse data;
}
