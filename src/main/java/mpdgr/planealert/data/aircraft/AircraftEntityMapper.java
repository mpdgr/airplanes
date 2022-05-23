package mpdgr.planealert.data.aircraft;

import mpdgr.planealert.domain.model.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AircraftEntityMapper {
    AircraftEntityMapper INSTANCE = Mappers.getMapper(AircraftEntityMapper.class);
    AircraftEntity aircraftToAircraftEntity(Aircraft aircraft);
    Aircraft aircraftEntityToAircraft(AircraftEntity aircraftEntity);
}
