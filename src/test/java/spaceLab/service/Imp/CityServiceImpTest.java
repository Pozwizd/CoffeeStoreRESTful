package spaceLab.service.Imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.City;
import spaceLab.repository.CityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class CityServiceImpTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImp cityService;

    @Test
    void testSaveCity() {
        City city = new City();
        cityService.saveCity(city);

        Mockito.verify(cityRepository, Mockito.times(1)).save(city);
    }

    @Test
    void testGetCity() {
        City city = new City();
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCity(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(city, result);
        Mockito.verify(cityRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetCityNotFound() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());
        City result = cityService.getCity(1L);
        Assertions.assertNull(result);
        Mockito.verify(cityRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testUpdateCity() {
        City city = new City();
        cityService.updateCity(city);
        Mockito.verify(cityRepository, Mockito.times(1)).save(city);
    }

    @Test
    void testDeleteCity() {

        City city = new City();
        cityService.deleteCity(city);
        Mockito.verify(cityRepository, Mockito.times(1)).delete(city);
    }

    @Test
    void testDeleteCityById() {
        cityService.deleteCity(1L);
        Mockito.verify(cityRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testFindAllCitiesWithPaging() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<City> cityPage = new PageImpl<>(Arrays.asList(new City(), new City()));
        Mockito.when(cityRepository.findAll(pageRequest)).thenReturn(cityPage);
        Page<City> result = cityService.findAllCities(0, 10);
        Assertions.assertEquals(2, result.getContent().size());
        Mockito.verify(cityRepository, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void testFindAllCities() {
        List<City> cities = Arrays.asList(new City(), new City());
        Mockito.when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.findAllCities();

        Assertions.assertEquals(2, result.size());
        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetCityByName() {
        City city = new City();
        Mockito.when(cityRepository.findCityByName("TestCity")).thenReturn(city);
        City result = cityService.getCityByName("TestCity");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(city, result);
        Mockito.verify(cityRepository, Mockito.times(1)).findCityByName("TestCity");
    }

    @Test
    void testFindCitiesByRequest() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<City> cityPage = new PageImpl<>(Arrays.asList(new City(), new City()));
        Mockito.when(cityRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(cityPage);

        Page<City> result = cityService.findCitiesByRequest(0, 10, "searchQuery");

        Assertions.assertEquals(2, result.getContent().size());
        Mockito.verify(cityRepository, Mockito.times(1)).findAll(any(Specification.class), eq(pageRequest));
    }

    @Test
    void testCountCities() {
        Mockito.when(cityRepository.count()).thenReturn(5L);

        long result = cityService.countCities();

        Assertions.assertEquals(5, result);
        Mockito.verify(cityRepository, Mockito.times(1)).count();
    }
}
