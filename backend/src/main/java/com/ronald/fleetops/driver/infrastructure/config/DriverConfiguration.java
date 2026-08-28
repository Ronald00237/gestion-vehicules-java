
package com.ronald.fleetops.driver.infrastructure.config;

import com.ronald.fleetops.driver.application.port.DriverRepository;
import com.ronald.fleetops.driver.application.service.DriverService;
import com.ronald.fleetops.driver.infrastructure.persistence.jpa.PostgreSqlDriverRepository;
import com.ronald.fleetops.driver.infrastructure.persistence.jpa.SpringDataDriverJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfiguration {

    @Bean
    public DriverRepository driverRepository(
            SpringDataDriverJpaRepository jpaRepository
    ) {
        return new PostgreSqlDriverRepository(jpaRepository);
    }

    @Bean
    public DriverService driverService(
            DriverRepository driverRepository
    ) {
        return new DriverService(driverRepository);
    }
}