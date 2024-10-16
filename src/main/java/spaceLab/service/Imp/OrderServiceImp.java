package spaceLab.service.Imp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spaceLab.entity.Customer;
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.mapper.DeliveryMapper;
import spaceLab.mapper.OrderMapper;
import spaceLab.model.order.request.OrderItemRequest;
import spaceLab.model.order.request.OrderRequest;
import spaceLab.model.order.response.OrderResponse;
import spaceLab.repository.OrderRepository;
import spaceLab.service.DeliveryService;
import spaceLab.service.OrderItemService;
import spaceLab.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final OrderItemService orderItemService;
    private final DeliveryMapper deliveryMapper;

    @Override
    public Order getOrder(Long id) {
        log.info("Fetching order by id: {}", id);
        return orderRepository.findById(id).orElseThrow(() -> {
            log.error("Order with id {} not found", id);
            return new RuntimeException("Order not found");
        });
    }

    @Override
    public OrderResponse getOrderResponse(Long id) {
        log.info("Fetching Order Response by id: {}", id);
        Order order = getOrder(id);
        OrderResponse orderResponse = orderMapper.orderToOrderResponse(order);
        log.info("Successfully mapped order with id {} to OrderResponse", id);
        return orderResponse;
    }



    @Override
    public OrderResponse reorder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        Order newOrder = Order.builder()
                .customer(order.getCustomer())
                .orderItems(order.getOrderItems())
                .dateTimeOfReady(LocalDateTime.now())
                .dateTimeOfCreate(LocalDateTime.now())
                .payment(order.getPayment())
                .totalAmount(order.getTotalAmount())
                .status(Order.OrderStatus.IN_PROGRESS)
                .delivery(order.getDelivery())
                .orderItems(order.getOrderItems())
                .build();
        return orderMapper.orderToOrderResponse(orderRepository.save(newOrder));
    }


    @Override
    public void updateOrderFromOrderRequest(OrderRequest orderRequest) {
        Order order = getOrder(orderRequest.getId());
        order.setDateTimeOfReady(orderRequest.getDateTimeOfReady());
        order.setPayment(orderRequest.getPayment());
        order.setStatus(orderRequest.getStatus());
        orderRepository.save(order);
        log.info("Order with id {} updated successfully", order.getId());
    }

    @Override
    public OrderResponse saveOrderFromOrderRequest(OrderRequest orderRequest, Customer customer) {
        if (orderRequest.getId() != null) {
            Order existingOrder = getOrder(orderRequest.getId());
            double totalAmount = existingOrder.getOrderItems().stream()
                    .mapToDouble(OrderItem::getTotalAmount)
                    .sum();
            existingOrder.setTotalAmount(totalAmount);
            return orderMapper.orderToOrderResponse(updateOrder(existingOrder, orderRequest));
        } else {
            Order newOrder = createOrder(orderRequest, customer);
            double totalAmount = newOrder.getOrderItems().stream()
                    .mapToDouble(OrderItem::getTotalAmount)
                    .sum();
            newOrder.setTotalAmount(totalAmount);
            return orderMapper.orderToOrderResponse(orderRepository.save(newOrder));
        }
    }

    @Override
    public Page<OrderResponse> getOrdersByCustomerIdPage(Long customerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findByCustomer_Id(customerId, pageable);
        return orderMapper.ordersToOrderResponses(orders);
    }

    private Order updateOrder(Order existingOrder, OrderRequest orderRequest) {
        List<OrderItemRequest> orderItemRequests = orderRequest.getOrderItemRequests();
        if (orderItemRequests == null) {
            orderItemRequests = new ArrayList<>();
        }
        applyOrderRequestToOrder(existingOrder, orderRequest);

        Order savedOrder = orderRepository.save(existingOrder);

        Set<Long> updatedOrderItemIds = orderRequest.getOrderItemRequests().stream()
                .map(OrderItemRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<OrderItem> currentOrderItems = savedOrder.getOrderItems();

        List<OrderItem> itemsToRemove = currentOrderItems.stream()
                .filter(item -> !updatedOrderItemIds.contains(item.getId())).collect(Collectors.toList());

        if (!itemsToRemove.isEmpty()) {
            savedOrder.getOrderItems().removeAll(itemsToRemove);
            boolean deletedNonexistent = orderItemService.deleteAll(itemsToRemove);
        }


        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(orderItemRequest -> orderItemService.saveOrderItem(orderItemRequest, savedOrder))
                .collect(Collectors.toList());

        savedOrder.setOrderItems(orderItems);
        return orderRepository.save(savedOrder);
    }


    private Order createOrder(OrderRequest orderRequest, Customer customer) {

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder = orderRepository.save(newOrder);

        applyOrderRequestToOrder(newOrder, orderRequest);
        Order finalNewOrder = newOrder;
        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
                .map(orderItemRequest -> orderItemService.saveOrderItem(orderItemRequest, finalNewOrder))
                .collect(Collectors.toList());
        newOrder.setOrderItems(orderItems);
        return newOrder;
    }

    private void applyOrderRequestToOrder(Order order, OrderRequest orderRequest) {
        order.setDateTimeOfCreate(orderRequest.getDateTimeOfCreate());
        order.setDateTimeOfReady(orderRequest.getDateTimeOfReady());

        if (orderRequest.getDeliveryRequest() != null) {
            order.setDelivery(deliveryService.saveDelivery(order, orderRequest.getDeliveryRequest()));
        } else {
            order.setDelivery(null);
        }
        order.setPayment(orderRequest.getPayment());
        order.setStatus(orderRequest.getStatus());
    }

}
