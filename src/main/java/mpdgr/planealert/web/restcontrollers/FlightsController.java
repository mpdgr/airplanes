package mpdgr.planealert.web.restcontrollers;

import lombok.RequiredArgsConstructor;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.ports.data.FlightRepository;
import mpdgr.planealert.domain.service.FlightsChecker;
import mpdgr.planealert.web.restcontrollers.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequiredArgsConstructor
public class FlightsController {
    private final FlightsChecker service;
    private final FlightRepository flightRepository;
    private final FlightDtoMapper flightDtoMapper;

    @GetMapping(path = "/currentflights")
    List<FlightDto> getFlights() {
        List<String> activeUuids = service.getCurrentFlightsContainer().stream()
                .map(f -> f.getUuid().toString())
                .collect(Collectors.toList());

        return flightRepository.getAll().stream()
                .filter(f -> f.getUuid() != null)
                .filter(f -> activeUuids.contains(f.getUuid().toString()))
                .sorted(latestFirst)
                .map(flightDtoMapper::flightToFlightDto)
                .filter(f -> f.getAircraft().getAirlineName().equalsIgnoreCase("Lufthansa"))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/recentflights/{hours}")
    ResponseEntity<List<FlightDto>> getRecentFlights(@PathVariable @Min(1) @Max(72) long hours) {
        return ResponseEntity.ok(flightRepository.getAll().stream()
                .filter(flight -> {
                    return (flight.getLastObservation() != null
                            && flight.getLastObservation()
                            .after(new Timestamp(System.currentTimeMillis() - hours * 3600_000)));
                })
                .sorted(latestFirst)
                .map(flightDtoMapper::flightToFlightDto)
                .filter(f -> f.getAircraft().getAirlineName().equalsIgnoreCase("Lufthansa"))
                .collect(Collectors.toList())
        );
    }

    Comparator<Flight> latestFirst = Comparator
            .comparing(Flight::getLastObservation)
            .reversed()
            .thenComparing(Flight::getCallsign);
}