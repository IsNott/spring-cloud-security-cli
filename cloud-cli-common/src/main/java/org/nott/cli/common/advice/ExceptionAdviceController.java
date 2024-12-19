package org.nott.cli.common.advice;

/**
 * @author Nott
 * @date 2024-12-20
 */

import lombok.extern.slf4j.Slf4j;
import org.nott.cli.common.exception.BusinessException;
import org.nott.cli.common.model.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author Nott
 * @date 2024-5-24
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceController extends ResponseEntityExceptionHandler {

    @Override
    protected org.springframework.http.ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request){
        String message = ex.getMessage();
        log.error(message, ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusCode.value()) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = exception.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            message = allErrors.get(0).getDefaultMessage();
        }
        return new org.springframework.http.ResponseEntity<>(Result.fail(statusCode.value(), message, null), headers, statusCode);
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBizException(BusinessException businessException) {
        log.error("Catch BusinessException:{}", businessException.getMessage(), businessException);
        return Result.showFailMsg("Business Exception");
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("Catch Exception：{}", e.getMessage(), e);
        return Result.fail(500, "系统错误", null);
    }


    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {
        log.error("Catch RuntimeException：{}", e.getMessage(), e);
        return Result.fail(500, "System error", null);
    }


}
