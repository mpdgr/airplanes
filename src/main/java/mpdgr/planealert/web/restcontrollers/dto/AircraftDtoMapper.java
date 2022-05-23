package mpdgr.planealert.web.restcontrollers.dto;

import mpdgr.planealert.domain.model.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
@Component
public interface AircraftDtoMapper {
    AircraftDtoMapper INSTANCE = Mappers.getMapper(AircraftDtoMapper.class);
    AircraftDto aircraftToAircraftDto(Aircraft aircraft);
    Aircraft aircraftDtoToAircraft(AircraftDto aircraftDto);
}
