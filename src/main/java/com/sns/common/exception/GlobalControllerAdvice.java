package com.sns.common.exception;

import com.sns.member.dto.response.Response;
import com.sns.member.exception.MemberJoinException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MemberJoinException.class)
    public ResponseEntity<?> applicationHandler(MemberJoinException e) {
        log.error("Error {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e) {
        log.error("Error {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ErrorCode.INTERNAL_SERVER_ERROR.name()));
    }
}
