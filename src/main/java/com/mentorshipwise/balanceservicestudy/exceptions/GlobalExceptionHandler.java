package com.mentorshipwise.balanceservicestudy.exceptions;

import com.mentorshipwise.balanceservicestudy.dtos.response.ApiResponse;
import com.mentorshipwise.balanceservicestudy.exceptions.balance.BalanceExceptions;
import com.mentorshipwise.balanceservicestudy.exceptions.user.UserExceptions;
import com.mentorshipwise.balanceservicestudy.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handle global exception to avoid status 500 when not found
    @ExceptionHandler(UserExceptions.UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserExceptions.UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseUtil.error(ex.getMessage()));
    }

    @ExceptionHandler(UserExceptions.InvalidCredentials.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(UserExceptions.InvalidCredentials ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResponseUtil.error(ex.getMessage()));
    }

    // Handle display error responser after invalid input
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        return ResponseEntity
                .badRequest()
                .body(ResponseUtil.error(errorMessage));
    }

    // Handle balance conflicts when trying to create a dup
    @ExceptionHandler(BalanceExceptions.BalanceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBalanceAlreadyExists(BalanceExceptions.BalanceAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResponseUtil.error(ex.getMessage()));
    }

    // Handle balance not found based on ccy
    @ExceptionHandler(BalanceExceptions.BalanceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBalanceNotFound(BalanceExceptions.BalanceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseUtil.error(ex.getMessage()));
    }

    // Handle insufficient funds within user balance
    @ExceptionHandler(BalanceExceptions.BalanceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBalanceInsufficientFunds(BalanceExceptions.BalanceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponseUtil.error(ex.getMessage()));
    }

    // Handle minium spending limit based on ccy
    @ExceptionHandler(BalanceExceptions.MinimumAmountException.class)
    public ResponseEntity<ApiResponse<Void>> handleMinimumAmount(BalanceExceptions.MinimumAmountException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponseUtil.error(ex.getMessage()));
    }

}
