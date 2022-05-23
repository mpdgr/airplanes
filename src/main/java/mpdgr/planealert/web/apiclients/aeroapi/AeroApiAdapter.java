package mpdgr.planealert.web.apiclients.aeroapi;

import lombok.RequiredArgsConstructor;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.ports.web.FlightDataProvider;
import mpdgr.planealert.web.apiclients.aeroapi.dto.FlightDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AeroApiAdapter implements FlightDataProvider {
    private final AeroApiClient aeroApiClient;

    @Override
    public Flight updateRouteData(Flight flight){
        try {
            Thread.sleep(7_000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        Optional<FlightDto> flightDto = aeroApiClient.getFlightDetails(flight.getCallsign());
        if (flightDto.isEmpty()){
            return flight;
        }
        if (flightDto.get().getOrigin() != null) {
            flight.setOriginAirport(flightDto.get().getOrigin().getCode_icao());
        }
        if (flightDto.get().getDestination() != null) {
            flight.setDestinationAirport(flightDto.get().getDestination().getCode_icao());
        }
        return flight;
    }
}