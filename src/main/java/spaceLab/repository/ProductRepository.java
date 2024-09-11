package spaceLab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import spaceLab.entity.Product;

import java.util.List;

/**
 * Repository for {@link Product}
 */

/**
ProductRepository
--
findAll(Specification<Product> specification, Pageable pageable) : Page<Product>
findAll(Specification<Product> productSpecification) : List<Product>
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    List<Product> findAll(Specification<Product> productSpecification);
}