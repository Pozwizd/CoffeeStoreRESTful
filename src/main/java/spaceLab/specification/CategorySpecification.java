package spaceLab.specification;

import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.Category;

public interface CategorySpecification {

    public static Specification<Category> search(String searchValue) {
        return (root, query, criteriaBuilder) -> {
            if (searchValue == null || searchValue.isEmpty()) {
                return null;
            }
            String lowerSearchValue = "%" + searchValue.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerSearchValue)
            );
        };
    }

    public static Specification<Category> byNotDeleted() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("deleted"), false);
        };
    }

    public static Specification<Category> byStatus(Category.Status status) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

}
