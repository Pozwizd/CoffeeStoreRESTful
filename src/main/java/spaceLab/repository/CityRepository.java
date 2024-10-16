package spaceLab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByName(String name);

    Page<City> findAll(Specification<City> search, Pageable pageable);
}