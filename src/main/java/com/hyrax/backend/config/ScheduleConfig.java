package com.hyrax.backend.config;

import com.hyrax.backend.service.VehicleStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleConfig {

    private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
    private VehicleStatusService vehicleStatusService;

    @Scheduled(fixedDelay = 1000)
    public void checkVehicleStatus() {
        log.debug("check vehicle status");
        vehicleStatusService.checkVehicleStatus();
    }

}
