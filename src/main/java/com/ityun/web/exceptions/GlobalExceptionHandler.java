package com.ityun.web.exceptions;

import com.ityun.base.lang.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        return Result.failure( message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handelHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handelMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handelHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public Result handleHttpMediaTypeException(HttpMediaTypeException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(ServletException.class)
    public Result handleServletException(ServletException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(MultipartException.class)
    public Result handleMultipartException(MultipartException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public Result handleUnexpectedTypeException(UnexpectedTypeException e) {
        String message = e.getMessage();
        return Result.failure(message);
    }
}
