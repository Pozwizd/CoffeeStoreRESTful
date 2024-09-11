package spaceLab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.Product;
import spaceLab.model.product.ProductResponse;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    Optional<Product> getProduct(Long id);

    ProductResponse getProductResponse(Long id);

    List<Product> getProductsByCategory(Long categoryId);


    Page<ProductResponse> findAllProductResponseByCategoryId(Long id, int page, int size);
}
