package spaceLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByName(String name);
}