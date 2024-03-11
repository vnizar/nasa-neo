package io.github.vnizar.app.config;

import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.AppErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    // Add exception handler for custom Exception class, so we don't need to manually return error code
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
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

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        AppErrorResponseDto response;
        response = new AppErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(response);

    }
}
