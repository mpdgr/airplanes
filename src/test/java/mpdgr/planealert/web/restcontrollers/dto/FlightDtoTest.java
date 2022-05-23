package mpdgr.planealert.web.restcontrollers.dto;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class FlightDtoTest {

    @Test
    @Disabled
    void getLastObservationShouldReturnLocalTime() {
        FlightDto flightDto = new FlightDto();
        flightDto.setLastObservation(new Timestamp(System.currentTimeMillis()));
        System.out.println(flightDto.getLastObservation());
    }
}