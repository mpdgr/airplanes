package mpdgr.planealert.web.apiclients.aeroapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class FlightsDto {
    @JsonProperty
    private List<FlightDto> flights;
}
