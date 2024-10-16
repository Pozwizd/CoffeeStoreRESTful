package spaceLab.service.Imp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import spaceLab.entity.Customer;
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.mapper.OrderMapper;
import spaceLab.model.order.request.OrderItemRequest;
import spaceLab.model.order.request.OrderRequest;
import spaceLab.model.order.response.OrderResponse;
import spaceLab.repository.OrderRepository;
import spaceLab.service.DeliveryService;
import spaceLab.service.OrderItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImpTest {

    @InjectMocks
    private OrderServiceImp orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private OrderRepository orderRepository;



    @Test
    public void testGetOrder_WhenFound() {
        Order order = new Order();
        OrderRequest orderRequest = new OrderRequest();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrder_WhenNotFound() {
        Order order = new Order();
        OrderRequest orderRequest = new OrderRequest();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrder(1L);
        });

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetOrderResponse() {
        Order order = new Order();
        OrderRequest orderRequest = new OrderRequest();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderResponse(order)).thenReturn(new OrderResponse());

        OrderResponse result = orderService.getOrderResponse(1L);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderMapper, times(1)).orderToOrderResponse(order);
    }



    @Test
    public void testReorder() {
        Order order = new Order();
        OrderRequest orderRequest = new OrderRequest();

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.orderToOrderResponse(order)).thenReturn(new OrderResponse());

        OrderResponse result = orderService.reorder(1L);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testUpdateOrderFromOrderRequest() {
        // Setup
        Order order = new Order();
        order.setId(1L);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L); // Указываем существующий id

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        orderService.updateOrderFromOrderRequest(orderRequest);

        // Assert
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }


    @Test
    public void testSaveOrderFromOrderRequest_WhenOrderExists() {

        Order order = new Order();
        order.setId(1L);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        OrderItem orderItem2 = new OrderItem();
        orderItem.setId(2L);
        orderItem.setQuantity(1);
        order.getOrderItems().add(orderItem);
        order.getOrderItems().add(orderItem2);



        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(1L);
        orderRequest.setOrderItemRequests(new ArrayList<>());

        Customer customer = new Customer();
        customer.setId(1L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.orderToOrderResponse(any(Order.class))).thenReturn(new OrderResponse());

        OrderResponse result = orderService.saveOrderFromOrderRequest(orderRequest, customer);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(orderMapper, times(1)).orderToOrderResponse(any(Order.class));
    }


    @Test
    public void testSaveOrderFromOrderRequest_WhenOrderDoesNotExist() {

        Order order = new Order();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItemRequests(new ArrayList<>());

        Customer customer = new Customer();
        customer.setId(1L);

        Order newOrder = new Order();
        newOrder.setId(1L);
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);
        when(orderMapper.orderToOrderResponse(any(Order.class))).thenReturn(new OrderResponse());

        OrderResponse result = orderService.saveOrderFromOrderRequest(orderRequest, customer);

        assertNotNull(result);
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(orderMapper, times(1)).orderToOrderResponse(any(Order.class));

    }

    @Test
    public void testGetOrdersByCustomerIdPage() {
        // Setup
        Long customerId = 1L;
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        List<Order> orderList = Arrays.asList(new Order(), new Order());

        Page<Order> orderPage = new PageImpl<>(orderList, pageable, orderList.size());

        Page<OrderResponse> orderResponsePage = new PageImpl<>(Arrays.asList(new OrderResponse(), new OrderResponse()), pageable, orderList.size());

        when(orderRepository.findByCustomer_Id(customerId, pageable)).thenReturn(orderPage);
        when(orderMapper.ordersToOrderResponses(orderPage)).thenReturn(orderResponsePage);

        // Act
        Page<OrderResponse> result = orderService.getOrdersByCustomerIdPage(customerId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size()); // Проверяем количество маппированных заказов
        verify(orderRepository, times(1)).findByCustomer_Id(customerId, pageable);
        verify(orderMapper, times(1)).ordersToOrderResponses(orderPage);
    }





}
