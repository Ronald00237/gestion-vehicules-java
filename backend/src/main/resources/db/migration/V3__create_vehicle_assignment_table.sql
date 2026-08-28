CREATE TABLE vehicle_assignments
(
    id         UUID                     PRIMARY KEY,
    vehicle_id UUID                     NOT NULL,
    driver_id  UUID                     NOT NULL,
    started_at TIMESTAMP WITH TIME ZONE NOT NULL,
    ended_at   TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_vehicle_assignments_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES vehicles (id),

    CONSTRAINT fk_vehicle_assignments_driver
        FOREIGN KEY (driver_id)
            REFERENCES drivers (id),

    CONSTRAINT chk_vehicle_assignments_dates
        CHECK (
            ended_at IS NULL
                OR ended_at > started_at
            )
);

CREATE UNIQUE INDEX uk_active_assignment_vehicle
    ON vehicle_assignments (vehicle_id)
    WHERE ended_at IS NULL;

CREATE UNIQUE INDEX uk_active_assignment_driver
    ON vehicle_assignments (driver_id)
    WHERE ended_at IS NULL;