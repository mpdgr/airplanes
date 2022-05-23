package mpdgr.planealert.web.apiclients.aeroapi;

import mpdgr.planealert.web.apiclients.aeroapi.dto.FlightDto;
import mpdgr.planealert.web.apiclients.aeroapi.dto.FlightsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Component
public class AeroApiClient {
    @Value("${aeroapi.key}")
    String apiKey;
    String headerParam = "x-apikey";

    public Optional<FlightDto> getFlightDetails(String callsign) {
        RestTemplate aaTemplate = new RestTemplate();
        String url = String.format("https://aeroapi.flightaware.com/aeroapi/flights/%s", callsign);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.set(headerParam, apiKey);
        System.out.println("AeroApi call");
        FlightsDto flightsDto = Optional.ofNullable(aaTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                entity,
                                FlightsDto.class)
                        .getBody())
                .orElse(new FlightsDto());
        return (flightsDto.getFlights().isEmpty() || flightsDto.getFlights() == null) ?
                Optional.empty() : Optional.of(flightsDto.getFlights().get(0));
    }
}
