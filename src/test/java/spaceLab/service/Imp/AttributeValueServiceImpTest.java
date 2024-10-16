package spaceLab.service.Imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spaceLab.entity.AttributeValue;
import spaceLab.mapper.AttributeValueMapper;
import spaceLab.model.attributeValue.AttributeValueResponse;
import spaceLab.repository.AttributeValueRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AttributeValueServiceImpTest {

    @Mock
    private AttributeValueRepository attributeValueRepository;

    @Mock
    private AttributeValueMapper attributeValueMapper;

    @InjectMocks
    private AttributeValueServiceImp attributeValueService;

    private AttributeValue attributeValue;
    private AttributeValueResponse attributeValueResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attributeValue = new AttributeValue();
        attributeValue.setId(1L);
        attributeValueResponse = new AttributeValueResponse();
        attributeValueResponse.setId(1L);
    }

    @Test
    void testGetAttributeValue() {
        when(attributeValueRepository.findById(1L)).thenReturn(Optional.of(attributeValue));

        Optional<AttributeValue> result = attributeValueService.getAttributeValue(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(attributeValue, result.get());
        verify(attributeValueRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAttributeValueDto() {
        when(attributeValueRepository.findById(1L)).thenReturn(Optional.of(attributeValue));
        when(attributeValueMapper.attributeValueToAttributeValueResponse(attributeValue)).thenReturn(attributeValueResponse);

        AttributeValueResponse result = attributeValueService.getAttributeValueDto(1L);

        Assertions.assertEquals(attributeValueResponse, result);
        verify(attributeValueMapper, times(1)).attributeValueToAttributeValueResponse(attributeValue);
    }

    @Test
    void testGetAllAttributeValues() {
        when(attributeValueRepository.findAll()).thenReturn(Collections.singletonList(attributeValue));
        List<AttributeValue> result = attributeValueService.getAllAttributeValues();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(attributeValue, result.get(0));
        verify(attributeValueRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAttributeValuesDto() {

        when(attributeValueRepository.findAll()).thenReturn(Collections.singletonList(attributeValue));
        when(attributeValueMapper.toAttributeValueResponseList(Collections.singletonList(attributeValue))).thenReturn(Collections.singletonList(attributeValueResponse));

        List<AttributeValueResponse> result = attributeValueService.getAllAttributeValuesDto();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(attributeValueResponse, result.get(0));
        verify(attributeValueMapper, times(1)).toAttributeValueResponseList(Collections.singletonList(attributeValue));
    }

    @Test
    void testGetAllAttributeValuesDtoByAttributeId() {
        Long attributeId = 1L;
        when(attributeValueRepository.findByAttributeProduct_Id(attributeId)).thenReturn(Collections.singletonList(attributeValue));
        when(attributeValueMapper.toAttributeValueResponseList(Collections.singletonList(attributeValue))).thenReturn(Collections.singletonList(attributeValueResponse));

        List<AttributeValueResponse> result = attributeValueService.getAllAttributeValuesDtoByAttributeId(attributeId);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(attributeValueResponse, result.get(0));
        verify(attributeValueMapper, times(1)).toAttributeValueResponseList(Collections.singletonList(attributeValue));
    }

    @Test
    void testFindByAttributeProduct() {
        Long productId = 1L;
        when(attributeValueRepository.findAttributeValueByAttributeProduct_Id(productId)).thenReturn(Collections.singletonList(attributeValue));

        List<AttributeValue> result = attributeValueService.findByAttributeProduct(productId);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(attributeValue, result.get(0));
        verify(attributeValueRepository, times(1)).findAttributeValueByAttributeProduct_Id(productId);
    }
}
