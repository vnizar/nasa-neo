package io.github.vnizar.app.config;

import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.AppErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        AppErrorResponseDto response;
        BindingResult result = ex.getBindingResult();
        if (result.hasFieldErrors()) {
            FieldError error = result.getFieldError();
            response = new AppErrorResponseDto(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    error != null ? error.getDefaultMessage() : ""
            );
            return ResponseEntity.badRequest().body(response);
        }

        response = new AppErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ""
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<AppErrorResponseDto> handleBindException(BindException ex) {
        AppErrorResponseDto response;
        BindingResult result = ex.getBindingResult();
        if (result.hasFieldErrors()) {
            FieldError error = result.getFieldError();
            response = new AppErrorResponseDto(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    error != null ? error.getDefaultMessage() : ""
            );
            return ResponseEntity.badRequest().body(response);
        }

        response = new AppErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ""
        );

        return ResponseEntity.badRequest().body(response);
    }
}
