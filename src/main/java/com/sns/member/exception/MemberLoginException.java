package com.sns.member.exception;

import com.sns.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public MemberLoginException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }
    @Override
    public String getMessage() {
        if (message == null) return errorCode.getMessage();
        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
