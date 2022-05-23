package mpdgr.planealert.data.repository;

import mpdgr.planealert.data.aircraft.AircraftEntityRepository;
import mpdgr.planealert.domain.model.Aircraft;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Disabled
@SpringBootTest
public class AircraftEntityRepositoryTest {

    @Autowired
    AircraftEntityRepository repository;

    @Test
    @Disabled
    void shouldReturnAircraftEntityBasedOnId(){
        //given
        String icao24valid = "A98EDC";
        //when
        Optional<Aircraft> result = repository.findByIcao24(icao24valid);
        //then
        Assertions.assertTrue(result.isPresent());
    }
}
