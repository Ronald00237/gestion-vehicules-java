
package com.ronald.fleetops.driver.application.exception;

public class DuplicateDriverLicenseException
        extends RuntimeException {

    public DuplicateDriverLicenseException(String message) {
        super(message);
    }
}