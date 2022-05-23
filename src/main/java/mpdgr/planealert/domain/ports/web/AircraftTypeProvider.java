package mpdgr.planealert.domain.ports.web;

import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.util.AircraftType;

public interface AircraftTypeProvider {
    boolean verifyAircraftType(Flight flight, AircraftType type);
}
