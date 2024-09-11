package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceLab.entity.Location;
import spaceLab.mapper.LocationMapper;
import spaceLab.model.location.LocationResponse;
import spaceLab.repository.LocationRepository;
import spaceLab.service.LocationService;

import java.util.List;
import java.util.stream.Collectors;



/**
 LocationService
 - locationRepository: LocationRepository
 - locationMapper: LocationMapper
 --
 + getLocation(@PathVariable("id") Long id): ResponseEntity<LocationDto>
 + getLocationResponse(@PathVariable("id") Long id): ResponseEntity<LocationResponse>
 + findAllLocationResponses(): ResponseEntity<List<LocationDto>>
 */
@Service
@AllArgsConstructor
@Slf4j
public class LocationServiceImp implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    @Override
    public Location getLocation(Long id) {
        return null;
    }

    @Override
    public LocationResponse getLocationResponse(Long id) {
        return null;
    }

    @Override
    public List<LocationResponse> findAllLocationResponses() {
        return locationRepository.findAll().stream().map(locationMapper::locationToLocationResponse).collect(Collectors.toList());
    }
}
