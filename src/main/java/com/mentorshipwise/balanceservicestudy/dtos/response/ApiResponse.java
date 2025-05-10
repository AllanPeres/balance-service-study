package com.mentorshipwise.balanceservicestudy.dtos.response;


public record ApiResponse<T>(String status, String message, T data) {
}
