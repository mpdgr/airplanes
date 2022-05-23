package mpdgr.planealert.data.flight;

import lombok.RequiredArgsConstructor;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.ports.data.FlightRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlightEntityRepository implements FlightRepository {
    private final FlightJpaRepository flightJpaRepository;
    private final FlightEntityMapper mapper;

    @Override
    public void saveFlight(Flight flight){
        FlightEntity flightEntity = mapper.flightToFlightEntity(flight);
        List<FlightEntity> listed = flightJpaRepository.findAll().stream()
                .filter(flightEntity::isSame)
                .collect(Collectors.toList());
        if (listed.size() > 1){
            System.out.println("verify data for UUID " + flight.getUuid().toString());
        }
        if (listed.isEmpty()){
            flightJpaRepository.save(flightEntity);
        } else {
            listed.forEach(f -> flightJpaRepository.updateUuidCol(f.getId(), flight.getUuid().toString()));
        }
    }

    @Override
    public void updateLastObservation(UUID uuid, Timestamp timestamp) {
        flightJpaRepository.updateLastObservationCol(uuid.toString(), timestamp);
    }

    @Override
    public Optional<Flight> findById(int id) {
        Optional<FlightEntity> flightEntity = flightJpaRepository.findById(id);
        return flightEntity.isPresent() ?
                Optional.of(mapper.flightEntityToFlight(flightEntity.get())) : Optional.empty();
    }

    @Override
    public List<Flight> getAll() {
        return flightJpaRepository.findAll().stream()
                .map(mapper::flightEntityToFlight)
                .collect(Collectors.toList());
    }
}
