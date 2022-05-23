package mpdgr.planealert.web.restcontrollers.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FlightDto {
    private String callsign;
    private String originAirport;
    private String destinationAirport;
    private AircraftDto aircraft;
    private Timestamp lastObservation;
    @JsonProperty("observedSince")
    private Timestamp spottedDate;

    @JsonGetter
    public String getLastObservation() {
        return lastObservation.toInstant()
                .atZone(ZoneId.of("Europe/Paris"))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    @JsonGetter
    public String getSpottedDate() {
        return spottedDate.toInstant()
                .atZone(ZoneId.of("Europe/Paris"))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }
}
