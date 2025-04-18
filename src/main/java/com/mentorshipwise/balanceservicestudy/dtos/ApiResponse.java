package com.mentorshipwise.balanceservicestudy.dtos;


public record ApiResponse<T>(String status, String message, T data) {
}
