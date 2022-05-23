package mpdgr.planealert.web.restcontrollers.dto;

import mpdgr.planealert.domain.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
@Component
public interface FlightDtoMapper {
    FlightDtoMapper INSTANCE = Mappers.getMapper(FlightDtoMapper.class);
    FlightDto flightToFlightDto(Flight flight);
    //Flight flightDtoToFlight(FlightDto flightDto);
}
