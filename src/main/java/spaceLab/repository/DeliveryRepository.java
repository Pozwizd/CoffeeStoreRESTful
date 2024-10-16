package spaceLab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Page<Delivery> findAll(Specification<Delivery> search, Pageable pageable);
}