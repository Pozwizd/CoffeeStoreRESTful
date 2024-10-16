package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spaceLab.entity.City;
import spaceLab.repository.CityRepository;
import spaceLab.service.CityService;
import spaceLab.specification.CitySpecification;

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
    public Page<City> findAllCities(int page, int pageSize) {
        return cityRepository.findAll(PageRequest.of(page, pageSize));
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
    public Page<City> findCitiesByRequest(int page, int pageSize, String search) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return cityRepository.findAll(CitySpecification.search(search), pageable);
    }

    @Override
    public long countCities() {
        return cityRepository.count();
    }
}
