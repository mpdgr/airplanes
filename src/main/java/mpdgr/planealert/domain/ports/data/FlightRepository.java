package mpdgr.planealert.domain.ports.data;

import mpdgr.planealert.domain.model.Flight;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface FlightRepository {

    Optional<Flight> findById(int id);

    List<Flight> getAll();

    void saveFlight(Flight flight);

    void updateLastObservation(UUID uuid, Timestamp timestamp);
}
