package com.aliens.membership.global.exception.exceptionhandler;

import com.aliens.membership.global.api.Api;
import com.aliens.membership.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.lang.Integer.MAX_VALUE;

@Slf4j
@RestControllerAdvice
@Order(value = MAX_VALUE)   // 가장 마지막에 실행 적용
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception (
            Exception exception
    ){
        log.error("",exception);

        return ResponseEntity
                .status(500)
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR)
                );
    }
}
