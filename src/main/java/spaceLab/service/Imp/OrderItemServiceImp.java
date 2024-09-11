package spaceLab.service.Imp;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.model.order.request.OrderItemRequest;
import spaceLab.repository.OrderItemRepository;
import spaceLab.service.OrderItemAttributeService;
import spaceLab.service.OrderItemService;
import spaceLab.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderItemServiceImp implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderItemAttributeService orderItemAttributeService;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        log.info("Saving OrderItem: {}", orderItem);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        log.info("Deleting OrderItem: {}", orderItem);
        orderItemRepository.delete(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItem(Long id) {
        return orderItemRepository.findById(id).get();
    }

    @Override
    public Page<OrderItem> findAllOrderItems(int page, int pageSize) {
        return orderItemRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public long countOrderItems() {
        return orderItemRepository.count();
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        log.info("Updating OrderItem: {}", orderItem);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
        log.info("Deleted OrderItem with id: {}", id);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        log.info("Get order items by order id: {}", orderId);
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public OrderItem saveOrderItem(OrderItemRequest orderItemDto, Order savedOrder) {
        if (orderItemDto.getId() == null) {
            OrderItem orderItem = createNewOrderItem(orderItemDto, savedOrder);
            return saveOrderItem(orderItem);
        } else {
            return orderItemRepository.findById(orderItemDto.getId())
                    .map(existingOrderItem -> updateExistingOrderItem(existingOrderItem, orderItemDto, savedOrder))
                    .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + orderItemDto.getId()));
        }
    }

    @Override
    public boolean deleteAll(List<OrderItem> itemsToRemove) {
        try {
            orderItemRepository.deleteAll(itemsToRemove);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private OrderItem createNewOrderItem(OrderItemRequest orderItemDto, Order savedOrder) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(productService.getProduct(orderItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found")));
        orderItem.setOrder(savedOrder);
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem = orderItemRepository.save(orderItem);
        OrderItem finalOrderItem = orderItem;
        List<OrderItemAttribute> attributes = orderItemDto.getOrderItemAttributeRequests().stream()
                .map(orderItemAttributeDto -> orderItemAttributeService.saveOrderItemAttribute(orderItemAttributeDto, finalOrderItem))
                .peek(orderItemAttribute -> finalOrderItem.getOrderItemAttributes().add(orderItemAttribute))
                .collect(Collectors.toList());

        finalOrderItem.setTotalAmount(calculateTotalAmount(orderItem.getQuantity(), attributes));
        return finalOrderItem;
    }

    private OrderItem updateExistingOrderItem(OrderItem existingOrderItem, OrderItemRequest orderItemDto, Order savedOrder) {
        existingOrderItem.setProduct(productService.getProduct(orderItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found")));
        existingOrderItem.setOrder(savedOrder);
        existingOrderItem.setQuantity(orderItemDto.getQuantity());
        existingOrderItem.setOrderItemAttributes(new ArrayList<>());
        List<OrderItemAttribute> updatedAttributes = orderItemDto.getOrderItemAttributeRequests().stream()
                .map(orderItemAttributeDto -> orderItemAttributeService.saveOrderItemAttribute(orderItemAttributeDto, existingOrderItem))
                .peek(orderItemAttribute -> existingOrderItem.getOrderItemAttributes().add(orderItemAttribute))
                .collect(Collectors.toList());

        existingOrderItem.setTotalAmount(calculateTotalAmount(existingOrderItem.getQuantity(), updatedAttributes));
        return updateOrderItem(existingOrderItem);
    }

    private double calculateTotalAmount(int quantity, List<OrderItemAttribute> attributes) {
        double totalAmount = attributes.stream()
                .map(OrderItemAttribute::getPrice)
                .reduce(0.0, Double::sum);
        return totalAmount * quantity;
    }
}
