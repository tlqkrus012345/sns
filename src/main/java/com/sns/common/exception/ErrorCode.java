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
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User has invalid permission"),
    ALREADY_LIKE(HttpStatus.CONFLICT, "Member already like post"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Alarm Connect fail");
    private HttpStatus status;
    private String message;
}
