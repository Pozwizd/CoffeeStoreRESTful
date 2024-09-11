package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.Location;
import spaceLab.model.location.LocationResponse;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "city.name", target = "city")
    LocationResponse locationToLocationResponse(Location location);
}
