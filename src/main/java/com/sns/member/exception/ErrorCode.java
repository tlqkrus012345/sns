package com.sns.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Member Name Is Duplicated"),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Password Is Wrong"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member Not Found")
    ;
    private HttpStatus status;
    private String message;
}
