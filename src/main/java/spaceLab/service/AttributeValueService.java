package spaceLab.service;

import spaceLab.entity.AttributeValue;
import org.springframework.stereotype.Service;
import spaceLab.model.attributeValue.AttributeValueResponse;

import java.util.List;
import java.util.Optional;

@Service
public interface AttributeValueService {

    Optional<AttributeValue> getAttributeValue(Long id);
    AttributeValueResponse getAttributeValueDto(Long id);
    List<AttributeValue> getAllAttributeValues();
    List<AttributeValueResponse> getAllAttributeValuesDto();
    List<AttributeValueResponse> getAllAttributeValuesDtoByAttributeId(Long attributeId);
    List<AttributeValue> findByAttributeProduct(Long id);
}
