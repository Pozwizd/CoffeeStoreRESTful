package spaceLab.service.Imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.AttributeProduct;
import spaceLab.mapper.AttributeProductMapper;
import spaceLab.model.AttributeProduct.AttributeProductResponse;
import spaceLab.repository.AttributeProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AttributeProductServiceImpTest {

    @InjectMocks
    private AttributeProductServiceImp attributeProductService;

    @Mock
    private AttributeProductRepository attributeProductRepository;

    @Mock
    private AttributeProductMapper attributeProductMapper;

    private AttributeProduct attributeProduct;
    private AttributeProductResponse attributeProductResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        attributeProduct = new AttributeProduct();
        attributeProduct.setId(1L);
        attributeProductResponse = new AttributeProductResponse();
        attributeProductResponse.setId(1L);
    }

    @Test
    public void testSaveAttributeProduct() {
        attributeProductService.saveAttributeProduct(attributeProduct);
        verify(attributeProductRepository, times(1)).save(attributeProduct);
    }

    @Test
    public void testGetAttributeProduct() {
        when(attributeProductRepository.findById(anyLong())).thenReturn(Optional.of(attributeProduct));

        Optional<AttributeProduct> result = attributeProductService.getAttributeProduct(1L);
        assertTrue(result.isPresent());
        assertEquals(attributeProduct, result.get());
        verify(attributeProductRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAttributeProductDto() {
        when(attributeProductRepository.findById(anyLong())).thenReturn(Optional.of(attributeProduct));
        when(attributeProductMapper.toAttributeProductResponse(any(AttributeProduct.class))).thenReturn(attributeProductResponse);

        AttributeProductResponse result = attributeProductService.getAttributeProductDto(1L);
        assertNotNull(result);
        assertEquals(attributeProductResponse.getId(), result.getId());
        verify(attributeProductMapper, times(1)).toAttributeProductResponse(attributeProduct);
    }

    @Test
    public void testGetAllAttributeProducts() {
        when(attributeProductRepository.findAll()).thenReturn(Arrays.asList(attributeProduct));

        List<AttributeProduct> result = attributeProductService.getAllAttributeProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attributeProduct, result.get(0));
        verify(attributeProductRepository, times(1)).findAll();
    }

    @Test
    public void testFindByProduct() {
        when(attributeProductRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(attributeProduct));

        List<AttributeProduct> result = attributeProductService.findByProduct(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attributeProduct, result.get(0));
        verify(attributeProductRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    public void testGetAllAttributesDtoByProduct() {
        when(attributeProductRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(attributeProduct));
        when(attributeProductMapper.toAttributeProductResponse(any(List.class))).thenReturn(Arrays.asList(attributeProductResponse));

        List<AttributeProductResponse> result = attributeProductService.getAllAttributesDtoByProduct("1");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attributeProductResponse.getId(), result.get(0).getId());
        verify(attributeProductMapper, times(1)).toAttributeProductResponse(any(List.class));
    }

    @Test
    public void testGetAttributesByProduct() {
        when(attributeProductRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(attributeProduct));
        when(attributeProductMapper.toAttributeProductResponse(any(List.class))).thenReturn(Arrays.asList(attributeProductResponse));

        List<AttributeProductResponse> result = attributeProductService.getAttributesByProduct(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(attributeProductResponse.getId(), result.get(0).getId());
        verify(attributeProductMapper, times(1)).toAttributeProductResponse(any(List.class));
    }
}
