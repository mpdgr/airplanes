package mpdgr.planealert.domain.ports.web;

import mpdgr.planealert.domain.config.SearchRange;
import mpdgr.planealert.domain.model.Flight;
import java.util.List;

public interface FlightAreaMonitor {
    void setRange(SearchRange range);
    List<Flight> getFlightsInRange();
}
