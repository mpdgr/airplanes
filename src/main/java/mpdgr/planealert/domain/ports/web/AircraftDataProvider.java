package mpdgr.planealert.domain.ports.web;

import mpdgr.planealert.domain.model.Aircraft;

public interface AircraftDataProvider {
    Aircraft getAircraftData(String icao24code);
}
