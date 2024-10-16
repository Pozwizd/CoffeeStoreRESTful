package spaceLab.service.Imp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spaceLab.entity.Location;
import spaceLab.mapper.LocationMapper;
import spaceLab.model.location.LocationResponse;
import spaceLab.repository.LocationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LocationServiceImpTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationServiceImp locationService;

    private Location location;
    private LocationResponse locationResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        location = new Location();
        location.setId(1L);
        location.setStreet("Location1");

        locationResponse = new LocationResponse();
        locationResponse.setId(1L);
        locationResponse.setStreet("Location1 Response");
    }

    @Test
    public void testGetLocation() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        Location result = locationService.getLocation(1L);
        assertNotNull(result);
        assertEquals(location.getId(), result.getId());
        assertEquals(location.getStreet(), result.getStreet());
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetLocation_WhenNotFound() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());
        Location result = locationService.getLocation(1L);
        assertNull(result);
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetLocationResponse() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(locationMapper.locationToLocationResponse(location)).thenReturn(locationResponse);
        LocationResponse result = locationService.getLocationResponse(1L);
        assertNotNull(result);
        assertEquals(locationResponse.getId(), result.getId());
        assertEquals(locationResponse.getStreet(), result.getStreet());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationMapper, times(1)).locationToLocationResponse(location);
    }


    @Test
    public void testFindAllLocationResponses() {
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));
        when(locationMapper.locationToLocationResponse(location)).thenReturn(locationResponse);
        List<LocationResponse> result = locationService.findAllLocationResponses();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(locationResponse.getId(), result.get(0).getId());
        assertEquals(locationResponse.getStreet(), result.get(0).getStreet());
        verify(locationRepository, times(1)).findAll();
        verify(locationMapper, times(1)).locationToLocationResponse(location);
    }
}
