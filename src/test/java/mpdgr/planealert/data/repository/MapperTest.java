package mpdgr.planealert.data.repository;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import mpdgr.planealert.data.aircraft.AircraftEntity;
import mpdgr.planealert.data.aircraft.AircraftEntityMapper;
import mpdgr.planealert.data.flight.FlightEntity;
import mpdgr.planealert.data.flight.FlightEntityMapper;
import mpdgr.planealert.domain.model.Aircraft;
import mpdgr.planealert.domain.model.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MapperTest {
    @Mock
    AircraftEntityMapper aircraftEntityMapper;
    @Spy
    FlightEntityMapper flightEntityMapper;
    @Spy
    Aircraft aircraft;
    @Spy
    Flight flight;

    @Test
    public void mapAircraftToAircraftEntityShouldReturnValidEntity(){
        aircraft.setHexIcao("test");
        AircraftEntity aircraftEntityResult = aircraftEntityMapper.INSTANCE.aircraftToAircraftEntity(aircraft);
        assertNotNull("mapper is null", flightEntityMapper);
        assertEquals("incorrect aircraft", aircraft.getHexIcao(),"test");
        assertEquals("incorrect flight", aircraftEntityResult.getHexIcao(),"test");
    }

    @Test
    public void mapFlightToFlightEntityShouldReturnEntityWithCorrectAcData(){
        aircraft.setHexIcao("test");
        flight.setAircraft(aircraft);
        FlightEntity flightEntity = flightEntityMapper.INSTANCE.flightToFlightEntity(flight);
        assertEquals("incorrect flight", flight.getAircraft().getHexIcao(),"test");
        assertNotNull("entity is null", flightEntity);
        assertEquals("incorrect flightentity", flightEntity.getAircraft().getHexIcao(),"test");
    }
}
