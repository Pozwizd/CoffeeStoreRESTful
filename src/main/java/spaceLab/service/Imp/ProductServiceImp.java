package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spaceLab.entity.Product;
import spaceLab.mapper.ProductMapper;
import spaceLab.model.product.ProductResponse;
import spaceLab.repository.ProductRepository;
import spaceLab.service.ProductService;

import java.util.List;
import java.util.Optional;

import static spaceLab.specification.ProductSpecification.byCategoryId;


/**
ProductServiceImp
- productRepository: ProductRepository
- productMapper: ProductMapper
--
+ getProduct(Long id): Optional<Product>
+ getProductDto(Long id): ProductDto
+ getProductsByCategory(Long categoryId): List<Product>
+ findByCategoryId(Long id): List<ProductDto>
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Optional<Product> getProduct(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    @Override
    public ProductResponse getProductResponse(Long id) {
        return productMapper.productToProductResponse(getProduct(id).get());
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findAll(byCategoryId(categoryId));
    }

    @Override
    public Page<ProductResponse> findAllProductResponseByCategoryId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(byCategoryId(id), pageable).map(productMapper::productToProductResponse);
    }

}
