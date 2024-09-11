package spaceLab.service;

import org.hibernate.query.Page;
import spaceLab.entity.City;

import java.util.List;

public interface CityService {

    void saveCity(City city);
    City getCity(Long id);
    void updateCity(City city);
    void deleteCity(City city);
    void deleteCity(Long id);
    Page findAllCities(int page, int pageSize);
    List<City> findAllCities();
    City getCityByName(String name);
    Page findCitiesByRequest(int page, int pageSize, String search);

    long countCities();
}
