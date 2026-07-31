package com.ronald.fleetops.shared.api;

public record ApiErrorResponse(int status,String error,  String message, String path ){


}