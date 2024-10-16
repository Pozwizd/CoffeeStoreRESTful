package spaceLab.service.Imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.Product;
import spaceLab.mapper.ProductMapper;
import spaceLab.model.product.ProductResponse;
import spaceLab.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImp productService;

    @Test
    void testGetProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProduct(productId);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(productId, result.get().getId());
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
    }

    @Test
    void testGetProductResponse() {
        Long productId = 1L;
        Product product = new Product();
        ProductResponse productResponse = new ProductResponse();
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.getProductResponse(productId);

        Assertions.assertEquals(productResponse, result);
        Mockito.verify(productRepository, Mockito.times(1)).findById(productId);
        Mockito.verify(productMapper, Mockito.times(1)).productToProductResponse(product);
    }

    @Test
    void testGetProductsByCategory() {
        Long categoryId = 1L;
        List<Product> products = Arrays.asList(new Product(), new Product());
        Mockito.when(productRepository.findAll(any(Specification.class))).thenReturn(products);

        List<Product> result = productService.getProductsByCategory(categoryId);

        Assertions.assertEquals(products.size(), result.size());
        Mockito.verify(productRepository, Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void testFindAllProductResponseByCategoryId() {
        Long categoryId = 1L;
        int page = 0;
        int size = 5;
        List<Product> products = Arrays.asList(new Product(), new Product());
        Page<Product> productPage = new PageImpl<>(products);
        ProductResponse productResponse = new ProductResponse();

        Mockito.when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);
        Mockito.when(productMapper.productToProductResponse(any(Product.class)))
                .thenReturn(productResponse);

        Page<ProductResponse> result = productService.findAllProductResponseByCategoryId(categoryId, page, size);

        Assertions.assertEquals(products.size(), result.getTotalElements());
        Mockito.verify(productRepository, Mockito.times(1)).findAll(any(Specification.class), any(Pageable.class));
        Mockito.verify(productMapper, Mockito.times(products.size())).productToProductResponse(any(Product.class));
    }
}
