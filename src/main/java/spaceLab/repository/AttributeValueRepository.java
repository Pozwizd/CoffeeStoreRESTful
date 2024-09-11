package spaceLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spaceLab.entity.AttributeValue;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    List<AttributeValue> findByAttributeProduct_Id(Long id);

    List<AttributeValue> findAttributeValueByAttributeProduct_Id(Long id);
}
