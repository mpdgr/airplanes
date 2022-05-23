package mpdgr.planealert.web.apiclients.aerodatabox;

import mpdgr.planealert.web.apiclients.aerodatabox.dto.AircraftDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AeroDataBoxClient {
    @Value("${aerodatabox.key}")
    String apiKey;
    String headerParam = "X-RapidAPI-Key";

    public AircraftDto getRawAcData(String icao24code) {
        RestTemplate adbTemplate = new RestTemplate();
        String url = String.format("https://aerodatabox.p.rapidapi.com/aircrafts/icao24/%s", icao24code);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        headers.set(headerParam, apiKey);
        System.out.println("AeroDataBox call");
        return adbTemplate.exchange(url, HttpMethod.GET, entity, AircraftDto.class).getBody();
    }
}
