CREATE TABLE vehicles (
                          id UUID PRIMARY KEY,

                          vin VARCHAR(100) NOT NULL UNIQUE,
                          license_plate VARCHAR(100) NOT NULL,
                          brand VARCHAR(100) NOT NULL,
                          model VARCHAR(100) NOT NULL,

                          manufacturing_year INTEGER NOT NULL
                              CHECK (manufacturing_year >= 1886),

                          mileage_in_kilometers BIGINT NOT NULL
                              CHECK (mileage_in_kilometers >= 0),

                          vehicle_type VARCHAR(50) NOT NULL
                              CHECK (vehicle_type IN (
                                                      'SUV', 'TRUCK', 'VAN',
                                                      'PICKUP', 'SEDAN', 'MOTORCYCLE'
                                  )),

                          fuel_type VARCHAR(50) NOT NULL
                              CHECK (fuel_type IN (
                                                   'GASOLINE', 'DIESEL', 'HYBRID',
                                                   'PLUG_IN_HYBRID', 'ELECTRIC'
                                  )),

                          status VARCHAR(50) NOT NULL
                              DEFAULT 'AVAILABLE'
                              CHECK (status IN (
                                                'AVAILABLE', 'ASSIGNED',
                                                'IN_MAINTENANCE',
                                                'OUT_OF_SERVICE', 'RETIRED'
                                  ))
);