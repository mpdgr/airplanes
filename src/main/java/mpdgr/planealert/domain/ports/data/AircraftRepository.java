package mpdgr.planealert.domain.ports.data;

import mpdgr.planealert.domain.model.Aircraft;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public interface AircraftRepository {

    Optional<Aircraft> findByIcao24(String icao24);

    List<Aircraft> getAll();

    void saveAircraft(Aircraft aircraft);
}
