package com.ronald.fleetops.shared.api;

import com.ronald.fleetops.vehicle.application.exception.DuplicateVehicleVinException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    }