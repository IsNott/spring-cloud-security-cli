package org.nott.cli.common.config;

import org.nott.cli.common.enums.ResponseEnum;
import org.nott.cli.common.exception.BusinessException;
import org.nott.cli.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Controller
public class GlobalExceptionHandlerConfig {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerConfig.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Result<List<String>>> methodArgumentNotValidExceptionHandler(Exception e) {
        log.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Result.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID));
        }

        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID, defaultMessages));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> unauthorizedExceptionHandler(BusinessException e) {
        log.error("businessExceptionHandler:{}", e.getMessage(), e);

        Result<?> serverResponseEntity = e.getResult();
        if (serverResponseEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(serverResponseEntity);
        }
        // 失败返回消息 状态码固定为直接显示消息的状态码
        return ResponseEntity.status(HttpStatus.OK).body(Result.fail(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<Object>> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeExceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(Result.fail(ResponseEnum.SHOW_FAIL));
    }

    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    public ResponseEntity<Result<Object>> exceptionHandler(MethodArgumentConversionNotSupportedException e) {
        log.error("MethodArgumentConversionNotSupportedExceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(Result.fail(ResponseEnum.NOT_SUPPORT));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Object>> exceptionHandler(Exception e) {
        log.error("exceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(Result.fail(ResponseEnum.EXCEPTION));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Result<Object>> badCredentialsExceptionHandler(Exception e) {
        log.error("badCredentialsExceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK).body(Result.fail(ResponseEnum.BAD_CREDNTIAL));
    }
}
