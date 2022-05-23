package mpdgr.planealert.domain.ports.web;

import mpdgr.planealert.domain.model.Flight;

public interface FlightDataProvider {
    Flight updateRouteData(Flight flight);
}
