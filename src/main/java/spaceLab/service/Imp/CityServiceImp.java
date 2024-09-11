package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.City;
import spaceLab.repository.CityRepository;
import spaceLab.service.CityService;

import java.util.List;


@Service
@AllArgsConstructor
public class CityServiceImp implements CityService  {

    private final CityRepository cityRepository;
    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public City getCity(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Page findAllCities(int page, int pageSize) {
        return null;
    }

    @Override
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityByName(String name) {
        return cityRepository.findCityByName(name);
    }

    @Override
    public Page findCitiesByRequest(int page, int pageSize, String search) {
        return null;
    }

    @Override
    public long countCities() {
        return cityRepository.count();
    }
}
