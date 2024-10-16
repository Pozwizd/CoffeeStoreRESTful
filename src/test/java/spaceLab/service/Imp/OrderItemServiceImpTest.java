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
import spaceLab.entity.Order;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;
import spaceLab.entity.Product;
import spaceLab.model.order.request.OrderItemRequest;
import spaceLab.repository.OrderItemRepository;
import spaceLab.service.OrderItemAttributeService;
import spaceLab.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImpTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderItemAttributeService orderItemAttributeService;

    @InjectMocks
    private OrderItemServiceImp orderItemService;

    @Test
    public void testSaveOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        OrderItem result = orderItemService.saveOrderItem(orderItem);
        assertNotNull(result);
        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemRepository, times(1)).save(orderItem);
    }



    @Test
    public void testDeleteOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        doNothing().when(orderItemRepository).delete(any(OrderItem.class));
        orderItemService.deleteOrderItem(orderItem);
        verify(orderItemRepository, times(1)).delete(orderItem);
    }

    @Test
    public void testGetAllOrderItems() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.findAll()).thenReturn(Collections.singletonList(orderItem));
        List<OrderItem> result = orderItemService.getAllOrderItems();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderItem.getId(), result.get(0).getId());
        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    public void testGetOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));
        OrderItem result = orderItemService.getOrderItem(1L);
        assertNotNull(result);
        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllOrderItems() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        Page<OrderItem> page = new PageImpl<>(Collections.singletonList(orderItem));
        when(orderItemRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<OrderItem> result = orderItemService.findAllOrderItems(0, 10);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(orderItem.getId(), result.getContent().get(0).getId());
        verify(orderItemRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    public void testCountOrderItems() {
        when(orderItemRepository.count()).thenReturn(5L);
        long count = orderItemService.countOrderItems();
        assertEquals(5L, count);
        verify(orderItemRepository, times(1)).count();
    }

    @Test
    public void testUpdateOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        OrderItem result = orderItemService.updateOrderItem(orderItem);
        assertNotNull(result);
        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemRepository, times(1)).save(orderItem);
    }

    @Test
    public void testDeleteOrderItemById() {
        doNothing().when(orderItemRepository).deleteById(anyLong());
        orderItemService.deleteOrderItem(1L);
        verify(orderItemRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetOrderItemsByOrderId() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.findByOrderId(anyLong())).thenReturn(Collections.singletonList(orderItem));
        List<OrderItem> result = orderItemService.getOrderItemsByOrderId(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderItem.getId(), result.get(0).getId());
        verify(orderItemRepository, times(1)).findByOrderId(1L);
    }

    @Test
    public void testSaveOrderItemWithNewItem() {
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1L);
        orderItemRequest.setQuantity(2);
        orderItemRequest.setOrderItemAttributeRequests(new ArrayList<>());
        Order savedOrder = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);


        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        lenient().when(productService.getProduct(anyLong())).thenReturn(Optional.of(new Product())); // Сделано lenient
        lenient().when(orderItemAttributeService.saveOrderItemAttribute(any(), any())).thenReturn(new OrderItemAttribute()); // Сделано lenient

        OrderItem result = orderItemService.saveOrderItem(orderItemRequest, savedOrder);

        assertNotNull(result);
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));
    }

    @Test
    public void testSaveOrderItemWithExistingItem() {
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setId(1L);
        orderItemRequest.setProductId(1L);
        orderItemRequest.setQuantity(2);
        orderItemRequest.setOrderItemAttributeRequests(new ArrayList<>());
        Order savedOrder = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        lenient().when(productService.getProduct(anyLong())).thenReturn(Optional.of(new Product()));
        lenient().when(orderItemAttributeService.saveOrderItemAttribute(any(), any())).thenReturn(new OrderItemAttribute()); // Сделано lenient

        OrderItem result = orderItemService.saveOrderItem(orderItemRequest, savedOrder);

        assertNotNull(result);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void testDeleteAll() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);

        doNothing().when(orderItemRepository).deleteAll(anyList());
        boolean result = orderItemService.deleteAll(Collections.singletonList(orderItem));
        assertTrue(result);
        verify(orderItemRepository, times(1)).deleteAll(anyList());
    }

    @Test
    public void testDeleteAll_WithEmptyList() {

        boolean result = orderItemService.deleteAll(null);
        assertFalse(result);
    }



}

