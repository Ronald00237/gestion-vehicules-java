CREATE TABLE drivers
(
    id             UUID         PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL,
    active         BOOLEAN      NOT NULL,

    CONSTRAINT uk_drivers_license_number
        UNIQUE (license_number)
);