package com.mentorshipwise.balanceservicestudy.utils;

import com.mentorshipwise.balanceservicestudy.dtos.ApiResponse;
import com.mentorshipwise.balanceservicestudy.enums.ApiResponseStatus;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ApiResponseStatus.SUCCESS.name(), message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ApiResponseStatus.FAIL.name(), message, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(ApiResponseStatus.FAIL.name(), message, data);
    }
}
