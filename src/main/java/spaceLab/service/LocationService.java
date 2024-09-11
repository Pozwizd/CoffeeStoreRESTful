package spaceLab.service;

import org.springframework.stereotype.Service;
import spaceLab.entity.Location;
import spaceLab.model.location.LocationResponse;

import java.util.List;


/* Пример
 LocationController
 - locationService: LocationService
 --
 + getLocation(@PathVariable("id") Long id): ResponseEntity<LocationDto>
 + getPageableLocationAddresses(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size): ResponseEntity<Page<LocationDto>>
 + getListLocationDto(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size): ResponseEntity<Page<LocationDto>>
 */


/**
 LocationService
 --
 + getLocation(@PathVariable("id") Long id): ResponseEntity<LocationDto>
 + getLocationResponse(@PathVariable("id") Long id): ResponseEntity<LocationResponse>
 + findAllLocationResponses(): ResponseEntity<List<LocationDto>>
 */
@Service
public interface LocationService {

    Location getLocation(Long id);
    LocationResponse getLocationResponse(Long id);
    List<LocationResponse> findAllLocationResponses();
}
