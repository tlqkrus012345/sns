package com.sns.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Member Name Is Duplicated"),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Password Is Wrong"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Interval Server Error"),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid");
    private HttpStatus status;
    private String message;
}