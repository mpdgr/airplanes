package mpdgr.planealert.web.apiclients.opensky;


import lombok.Data;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.web.apiclients.opensky.dto.FlightMapper;
import mpdgr.planealert.web.apiclients.opensky.dto.FlightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class OpenSkyClient {
    @Autowired
    private final FlightMapper resolver;
    private String url;

    public List<Flight> getFlightsInRange() {
        RestTemplate osTemplate = new RestTemplate();
        FlightsDto flightsDto = osTemplate.getForEntity(url, FlightsDto.class).getBody();
        System.out.println("OpenSky call");

        return flightsDto != null ?
                Arrays.stream(flightsDto.getFlights())
                        .map(resolver::dtoToFlight)
                        .collect(Collectors.toList())
                : List.of();
    }
}