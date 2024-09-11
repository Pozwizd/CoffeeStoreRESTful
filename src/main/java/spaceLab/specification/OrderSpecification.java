package spaceLab.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.AttributeProduct;
import spaceLab.entity.Order;
import spaceLab.entity.Product;

public interface OrderSpecification {

    static Specification<Order> byCustomerPhoneNumber(String phoneNumber) {
        return (root, query, builder) -> {
            Join<AttributeProduct, Product> product = root.join("customer");
            return builder.or(
                    builder.like(product.get("phoneNumber"), "%" +phoneNumber+ "%")
            );
        };
    }

    static Specification<Order> byCustomerName(String name) {

        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            Join<AttributeProduct, Product> product = root.join("customer");
            String lowerSearchValue = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(product.get("name")), lowerSearchValue)
            );
        };
    }

    static Specification<Order> byNotDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }


}
