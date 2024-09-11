package spaceLab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.AttributeProduct;
import spaceLab.model.AttributeProduct.AttributeProductResponse;

import java.util.List;
import java.util.Optional;

@Service
public interface AttributeProductService {

    void saveAttributeProduct(AttributeProduct attributeProduct);
    Optional<AttributeProduct> getAttributeProduct(Long id);
    AttributeProductResponse getAttributeProductDto(Long id);

    List<AttributeProduct> getAllAttributeProducts();

    List<AttributeProduct> findByProduct(Long productId);
    List<AttributeProductResponse> getAllAttributesDtoByProduct(String productId);

    List<AttributeProductResponse> getAttributesByProduct(Long Long);
}
