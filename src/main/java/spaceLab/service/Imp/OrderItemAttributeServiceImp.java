package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.model.order.request.OrderItemAttributeRequest;
import spaceLab.repository.OrderItemAttributeRepository;
import spaceLab.service.AttributeProductService;
import spaceLab.service.AttributeValueService;
import spaceLab.service.OrderItemAttributeService;

@Service
@AllArgsConstructor
@Slf4j
public class OrderItemAttributeServiceImp implements OrderItemAttributeService {

    private final OrderItemAttributeRepository orderItemAttributeRepository;
    private final AttributeProductService attributeProductService;
    private final AttributeValueService attributeValueService;


    @Override
    public void deleteOrderItemAttribute(Long id) {
        orderItemAttributeRepository.deleteById(id);
    }

    @Override
    public OrderItemAttribute getOrderItemAttribute(Long id) {
        return orderItemAttributeRepository.findById(id).get();
    }

    @Override
    public Iterable<OrderItemAttribute> getAllOrderItemAttributes() {
        return orderItemAttributeRepository.findAll();
    }

    @Override
    public Iterable<OrderItemAttribute> getAllOrderItemAttributesByOrderItemId(Long orderItemId) {
        return orderItemAttributeRepository.findOrderItemAttributeByOrderItem_Id(orderItemId);
    }

    @Override
    public void deleteAllOrderItemAttributesByOrderItemId(Long orderItemId) {
        orderItemAttributeRepository.deleteByOrderItem_Id(orderItemId);
    }

    @Override
    public OrderItemAttribute saveOrderItemAttribute(OrderItemAttribute orderItemAttribute) {
        orderItemAttribute.setPrice(orderItemAttribute.getAttributeValue().getPriceWithDiscount() != null && orderItemAttribute.getAttributeValue().getPriceWithDiscount() != 0 ?
                orderItemAttribute.getAttributeValue().getPriceWithDiscount() : orderItemAttribute.getAttributeValue().getPrice());
        log.info("OrderItemAttribute saved: {}", orderItemAttribute);
        return orderItemAttributeRepository.save(orderItemAttribute);
    }

    @Override
    public OrderItemAttribute updateOrderItemAttribute(OrderItemAttribute orderItemAttribute) {
        orderItemAttribute.setPrice(orderItemAttribute.getAttributeValue().getPriceWithDiscount() != null && orderItemAttribute.getAttributeValue().getPriceWithDiscount() != 0 ?
                orderItemAttribute.getAttributeValue().getPriceWithDiscount() : orderItemAttribute.getAttributeValue().getPrice());

        return orderItemAttributeRepository.save(orderItemAttribute);
    }

    @Override
    public OrderItemAttribute saveOrderItemAttribute(OrderItemAttributeRequest orderItemAttributeRequest, OrderItem orderItem) {
        if (orderItemAttributeRequest.getId() == null) {
            OrderItemAttribute orderItemAttribute = new OrderItemAttribute();
            setOrderItemAttributeValues(orderItemAttribute, orderItemAttributeRequest, orderItem);
            return saveOrderItemAttribute(orderItemAttribute);
        }
        return orderItemAttributeRepository.findById(orderItemAttributeRequest.getId()).map(orderItemAttribute -> {
            setOrderItemAttributeValues(orderItemAttribute, orderItemAttributeRequest, orderItem);

            return updateOrderItemAttribute(orderItemAttribute);
        }).get();
    }

    private void setOrderItemAttributeValues(OrderItemAttribute orderItemAttribute, OrderItemAttributeRequest orderItemAttributeDto, OrderItem orderItem) {
        orderItemAttribute.setAttributeProduct(attributeProductService.getAttributeProduct(orderItemAttributeDto.getProductAttributeId()).get());
        orderItemAttribute.setAttributeValue(attributeValueService.getAttributeValue(orderItemAttributeDto.getAttributeValueId()).get());
        orderItemAttribute.setOrderItem(orderItem);
        orderItemAttribute.setPrice(
                orderItemAttribute
                        .getAttributeValue()
                        .getPriceWithDiscount() != null &&
                        orderItemAttribute
                                .getAttributeValue()
                                .getPriceWithDiscount() != 0 ? orderItemAttribute
                        .getAttributeValue()
                        .getPriceWithDiscount() : orderItemAttribute
                        .getAttributeValue()
                        .getPrice());
    }
}
