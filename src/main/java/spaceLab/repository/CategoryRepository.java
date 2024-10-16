package spaceLab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAll(Specification<Category> specification, Pageable pageable);
    List<Category> findAll(Specification<Category> specification);
}