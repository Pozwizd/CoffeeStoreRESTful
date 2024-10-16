package spaceLab.specification;

import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.Delivery;

public interface DeliverySpecification {

    static Specification<Delivery> search(String searchValue) {
        return (root, query, criteriaBuilder) -> {
            if (searchValue == null || searchValue.isEmpty()) {
                return null;
            }
            String lowerSearchValue = "%" + searchValue.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerSearchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), lowerSearchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), lowerSearchValue)
            );
        };
    }


}
