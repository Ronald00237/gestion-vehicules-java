package com.ronald.fleetops.shared.api;

import com.ronald.fleetops.vehicle.application.exception.DuplicateVehicleVinException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.LinkedHashMap;
import java.util.Map;
    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(DuplicateVehicleVinException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateVehicleVin(
                DuplicateVehicleVinException exception,
                HttpServletRequest request
        ) {
            HttpStatus status = HttpStatus.CONFLICT;

            ApiErrorResponse response = new ApiErrorResponse(
                    status.value(),
                    status.getReasonPhrase(),
                    exception.getMessage(),
                    request.getRequestURI()
            );

            return ResponseEntity.status(status).body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
                MethodArgumentNotValidException exception,
                HttpServletRequest request
        ) {
            HttpStatus status = HttpStatus.BAD_REQUEST;

            Map<String, String> fieldErrors = new LinkedHashMap<>();

            for (FieldError fieldError :
                    exception.getBindingResult().getFieldErrors()) {

                fieldErrors.put(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                );
            }

            ValidationErrorResponse response =
                    new ValidationErrorResponse(
                            status.value(),
                            status.getReasonPhrase(),
                            "Request validation failed",
                            fieldErrors,
                            request.getRequestURI()
                    );

            return ResponseEntity.status(status).body(response);
        }
    }