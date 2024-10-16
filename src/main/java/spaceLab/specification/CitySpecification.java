package spaceLab.specification;

import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.City;

public interface CitySpecification {

    static Specification<City> search(String searchValue) {
        return (root, query, criteriaBuilder) -> {
            if (searchValue == null || searchValue.isEmpty()) {
                return null;
            }
            String lowerSearchValue = "%" + searchValue.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerSearchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("postalCode")), lowerSearchValue),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("region")), lowerSearchValue)
            );
        };
    }




}
