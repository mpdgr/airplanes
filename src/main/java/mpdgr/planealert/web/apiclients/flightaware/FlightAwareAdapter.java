package mpdgr.planealert.web.apiclients.flightaware;

import lombok.RequiredArgsConstructor;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.ports.web.AircraftTypeProvider;
import mpdgr.planealert.domain.util.AircraftType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightAwareAdapter implements AircraftTypeProvider {
    private final FlightAwareClient flightAwareClient;

    @Override
    public boolean verifyAircraftType(Flight flight, AircraftType type){
        String flightDataString = flightAwareClient.getWebContent(flight.getCallsign());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return containsTypeId(flightDataString, type.getType());
    }

    public boolean containsTypeId(String webContent, String typeId) {
        return webContent.contains(String.format("\"model\":\"%s", typeId));
    }
}
