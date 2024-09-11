package spaceLab.service.Imp;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceLab.entity.AttributeValue;
import spaceLab.mapper.AttributeValueMapper;
import spaceLab.model.attributeValue.AttributeValueResponse;
import spaceLab.repository.AttributeValueRepository;
import spaceLab.service.AttributeValueService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AttributeValueServiceImp implements AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeValueMapper attributeValueMapper;


    @Override
    public Optional<AttributeValue> getAttributeValue(Long id) {
        log.info("Fetching attributeValue with id: {}", id);
        return attributeValueRepository.findById(id);
    }

    @Override
    public AttributeValueResponse getAttributeValueDto(Long id) {
        return attributeValueMapper.attributeValueToAttributeValueResponse(getAttributeValue(id).get());
    }

    @Override
    public List<AttributeValue> getAllAttributeValues() {
        log.info("Fetching all attributeValues");
        return attributeValueRepository.findAll();
    }

    @Override
    public List<AttributeValueResponse> getAllAttributeValuesDto() {
        return attributeValueMapper.toAttributeValueResponseList(getAllAttributeValues());
    }

    @Override
    public List<AttributeValueResponse> getAllAttributeValuesDtoByAttributeId(Long attributeId) {
        return attributeValueMapper.toAttributeValueResponseList(attributeValueRepository.findByAttributeProduct_Id(attributeId));
    }

    @Override
    public List<AttributeValue> findByAttributeProduct(Long id) {
        return attributeValueRepository.findAttributeValueByAttributeProduct_Id(id);
    }
}
