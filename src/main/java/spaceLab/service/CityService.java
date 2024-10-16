package spaceLab.service;

import org.springframework.data.domain.Page;
import spaceLab.entity.City;

import java.util.List;

public interface CityService {

    void saveCity(City city);
    City getCity(Long id);
    void updateCity(City city);
    void deleteCity(City city);
    void deleteCity(Long id);
    Page<City> findAllCities(int page, int pageSize);
    List<City> findAllCities();
    City getCityByName(String name);
    Page<City> findCitiesByRequest(int page, int pageSize, String search);

    long countCities();
}
