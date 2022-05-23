package mpdgr.planealert.web.apiclients.opensky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class FlightsDto {
    @JsonProperty("states")
    private Object[][] flights;
}