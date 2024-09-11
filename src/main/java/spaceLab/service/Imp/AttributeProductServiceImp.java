package spaceLab.service.Imp;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceLab.entity.AttributeProduct;
import spaceLab.mapper.AttributeProductMapper;
import spaceLab.model.AttributeProduct.AttributeProductResponse;
import spaceLab.repository.AttributeProductRepository;
import spaceLab.service.AttributeProductService;
import spaceLab.service.AttributeValueService;
import spaceLab.service.ProductService;
import spaceLab.specification.AttributeProductSpecification;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AttributeProductServiceImp implements AttributeProductService {

    private final AttributeProductRepository attributeProductRepository;
    private final AttributeProductMapper attributeProductMapper;


    @Override
    public void saveAttributeProduct(AttributeProduct attributeProduct) {
        attributeProductRepository.save(attributeProduct);
        log.info("AttributeProduct saved successfully");
    }

    @Override
    public Optional<AttributeProduct> getAttributeProduct(Long id) {
        log.info("Fetching attributeProduct with id: {}", id);
        return attributeProductRepository.findById(id);
    }

    @Override
    public AttributeProductResponse getAttributeProductDto(Long id) {
        return attributeProductMapper.toAttributeProductResponse(getAttributeProduct(id).get());
    }


    @Override
    public List<AttributeProduct> getAllAttributeProducts() {
        log.info("Fetching all attributeProducts");
        return attributeProductRepository.findAll();
    }

    @Override
    public List<AttributeProduct> findByProduct(Long productId) {
        return attributeProductRepository.findAll(AttributeProductSpecification.byProductId(productId));
    }

    @Override
    public List<AttributeProductResponse> getAllAttributesDtoByProduct(String productId) {
        return attributeProductMapper.toAttributeProductResponse(findByProduct(Long.valueOf(productId)));
    }




    @Override
    public List<AttributeProductResponse> getAttributesByProduct(Long Long) {
        return attributeProductMapper.toAttributeProductResponse(findByProduct(Long));
    }
}
