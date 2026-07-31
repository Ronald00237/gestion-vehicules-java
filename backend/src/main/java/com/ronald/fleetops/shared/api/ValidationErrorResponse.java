package com.ronald.fleetops.shared.api;

import java.util.Map;

    public record ValidationErrorResponse(
            int status,
            String error,
            String message,
            Map<String, String> fieldErrors,
            String path) {

}