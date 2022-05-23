package mpdgr.planealert.domain.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mpdgr.planealert.domain.config.SearchRange;
import mpdgr.planealert.domain.model.Aircraft;
import mpdgr.planealert.domain.model.Flight;
import mpdgr.planealert.domain.model.HexCode;
import mpdgr.planealert.domain.ports.data.AircraftRepository;
import mpdgr.planealert.domain.ports.data.FlightRepository;
import mpdgr.planealert.domain.ports.data.HexCodeRepository;
import mpdgr.planealert.domain.ports.web.FlightDataProvider;
import mpdgr.planealert.domain.ports.web.AircraftDataProvider;
import mpdgr.planealert.domain.ports.web.AircraftTypeProvider;
import mpdgr.planealert.domain.ports.web.FlightAreaMonitor;
import mpdgr.planealert.domain.util.B747Resolver;
import mpdgr.planealert.domain.util.AircraftType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Data
public class FlightsChecker {
    private final FlightAreaMonitor flightAreaMonitor;
    private final AircraftTypeProvider aircraftTypeProvider;
    private final AircraftDataProvider aircraftDataProvider;
    private final FlightDataProvider flightDataProvider;
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;
    private final HexCodeRepository hexCodeRepository;
    private final B747Resolver b747Resolver;

    @Value("${current.range:ALL}")
    private SearchRange range;
    @Value("${current.type:BOEING_747}")
    private AircraftType type;

    private final List<Flight> currentFlightsContainer = new ArrayList<>();

    @Scheduled(fixedDelay = 300_000)
    public void updateFlightsContainer() {
        List<Flight> currentB747Flights = checkForB747();
        currentFlightsContainer.clear();
        currentFlightsContainer.addAll(currentB747Flights);
        updateLastObservation(currentFlightsContainer);
        System.out.println(currentFlightsContainer);
    }

    public List<Flight> checkForB747(){
        flightAreaMonitor.setRange(range);
        List<Flight> tempFlightsContainer = new ArrayList<>();
        List<Flight> currentFlightsRaw = flightAreaMonitor.getFlightsInRange();

        //copy flights recorded during previous check to temp container if still visible
        Predicate<Flight> recentlyRecordedFlight = flight -> {
            return currentFlightsContainer.stream()
                    .map(Flight::getIcao24)
                    .anyMatch(icao24 -> icao24.equals(flight.getIcao24()));
        };
        currentFlightsRaw.stream()
                .filter(recentlyRecordedFlight)
                .forEach(f -> {
                    currentFlightsContainer.stream()
                            .filter(cf -> cf.getIcao24().equals(f.getIcao24()))
                            .filter(cf -> cf.getCallsign().equals(f.getCallsign()))
                            .forEach(tempFlightsContainer::add);
                });

        //remove flights recorded during previous check from processing list
        currentFlightsRaw.removeIf(recentlyRecordedFlight);

        //remove flights of airlines not operating B747 from processing list
        List<Flight> flightsOfB747Operators = b747Resolver.findB747Operators(currentFlightsRaw);

        //verify aircraft data
        List<Flight> flightsOfB747Raw = verifyAircraftInfo(flightsOfB747Operators);

        //update flight info for new 747 flights
        List<Flight> flightsOfB747 = updateFlightInfo(flightsOfB747Raw);

        //update repositories
        updateRepositories(flightsOfB747);

        //copy new records to temp container
        tempFlightsContainer.addAll(flightsOfB747);
        tempFlightsContainer.forEach(f -> f.setLastObservation(new Timestamp(System.currentTimeMillis())));

        //sort temp container
        Comparator<Flight> latestEntriesFirst = (f1, f2) -> {
            return f2.getSpottedDate().compareTo(f1.getSpottedDate());
        };
        tempFlightsContainer.sort(latestEntriesFirst);

        return tempFlightsContainer;
    }

    public List<Flight> verifyAircraftInfo(List<Flight> flightList){
        //check if aircrafts listed in HexCode database
        List<Flight> flightsOfListedAircrafts = flightList.stream()
                .filter(flight -> hexCodeRepository.isListed(flight.getIcao24()))
                .collect(Collectors.toList());

        List<Flight> flightsOfNotListedAircrafts = flightList.stream()
                .filter(flight -> !hexCodeRepository.isListed(flight.getIcao24()))
                .collect(Collectors.toList());

        //list new 747 flights
        List<Flight> flightsB747 = new ArrayList<>();

        flightsOfListedAircrafts.stream()
                .filter(flight -> hexCodeRepository.findHexCode(flight.getIcao24()).get().isB747())
                .forEach(flightsB747::add);

        flightsOfNotListedAircrafts.forEach(flight -> {
            HexCode hexCode = new HexCode(flight.getIcao24(), false);
            if (aircraftTypeProvider.verifyAircraftType(flight, type)) {
                flightsB747.add(flight);
                hexCode.setB747(true);
            }
            hexCodeRepository.saveHexCode(hexCode);
        });
        return flightsB747;
    }

    public List<Flight> updateFlightInfo(List<Flight> flightList){
        flightList.forEach(flight ->
        {
            Optional<Aircraft> aircraftEntry = aircraftRepository
                    .findByIcao24(flight
                            .getIcao24()
                            .toUpperCase());
            Aircraft aircraft = aircraftEntry.orElseGet(
                    () -> aircraftDataProvider.getAircraftData(flight.getIcao24()));
            flight.setSpottedPl(range.equals(SearchRange.POLAND));
            Flight flightUpdt = flightDataProvider.updateRouteData(flight);
            flightUpdt.setAircraft(aircraft);
        });
        return flightList;
    }

    @Transactional
    public void updateRepositories(List<Flight> flightList){
        flightList.forEach(f -> aircraftRepository.saveAircraft(f.getAircraft()));
        flightList.forEach(flightRepository::saveFlight);
    }

    public void updateLastObservation(List<Flight> flightList){
        flightList.forEach(f -> flightRepository.updateLastObservation(f.getUuid(), f.getLastObservation()));
    }

}

//TODO: Update for AC type
//TODO: Logger
//TODO: 777 error -> remove
//TODO: Uppercase test