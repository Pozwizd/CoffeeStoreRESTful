package spaceLab.service.Imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import spaceLab.entity.City;
import spaceLab.entity.Delivery;
import spaceLab.entity.Order;
import spaceLab.model.delivery.DeliveryRequest;
import spaceLab.repository.DeliveryRepository;
import spaceLab.service.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImpTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private CityService cityService;

    @InjectMocks
    private DeliveryServiceImp deliveryService;

    @Test
    void testSaveDelivery_WithExistingDelivery() {

        Order order = new Order();
        order.setId(1L);

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setCityId(1L);
        deliveryRequest.setId(1L);

        Delivery existingDelivery = new Delivery();
        existingDelivery.setId(1L);

        Mockito.when(cityService.getCity(1L)).thenReturn(new City());
        Mockito.when(deliveryRepository.save(any(Delivery.class))).thenReturn(existingDelivery);
        Mockito.when(deliveryRepository.findById(1L)).thenReturn(Optional.of(existingDelivery));


        Delivery result = deliveryService.saveDelivery(order, deliveryRequest);


        Assertions.assertNotNull(result);
        Mockito.verify(deliveryRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(deliveryRepository, Mockito.times(1)).save(existingDelivery);
    }

    @Test
    void testSaveDelivery_WithNewDelivery() {
        Order order = new Order();
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setCityId(1L);
        Delivery expectedDelivery = new Delivery();
        City city = new City();
        expectedDelivery.setCity(city);
        expectedDelivery.setOrder(order);
        Mockito.when(cityService.getCity(1L)).thenReturn(city);

        Mockito.when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> {
            return invocation.<Delivery>getArgument(0);
        });

        Delivery result = deliveryService.saveDelivery(order, deliveryRequest);

        Mockito.verify(deliveryRepository, Mockito.times(1)).save(any(Delivery.class));

        Assertions.assertNotNull(result);

        Assertions.assertEquals(expectedDelivery.getOrder(), result.getOrder());
        Assertions.assertEquals(expectedDelivery.getCity(), result.getCity());

    }

    @Test
    void testGetDelivery() {
        Delivery delivery = new Delivery();
        Mockito.when(deliveryRepository.getReferenceById(1L)).thenReturn(delivery);

        Delivery result = deliveryService.getDelivery(1L);

        Assertions.assertNotNull(result);
        Mockito.verify(deliveryRepository, Mockito.times(1)).getReferenceById(1L);
    }

    @Test
    void testGetAllDeliveries() {
        List<Delivery> deliveries = Arrays.asList(new Delivery(), new Delivery());
        Mockito.when(deliveryRepository.findAll()).thenReturn(deliveries);

        List<Delivery> result = deliveryService.getAllDeliveries();

        Assertions.assertEquals(2, result.size());
        Mockito.verify(deliveryRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testDeleteDelivery() {
        Delivery delivery = new Delivery();

        deliveryService.deleteDelivery(delivery);

        Mockito.verify(deliveryRepository, Mockito.times(1)).delete(delivery);
    }

    @Test
    void testDeleteDeliveryById() {
        deliveryService.deleteDelivery(1L);

        Mockito.verify(deliveryRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testFindAllDeliveriesWithPaging() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Delivery> deliveryPage = new PageImpl<>(Arrays.asList(new Delivery(), new Delivery()));
        Mockito.when(deliveryRepository.findAll(pageRequest)).thenReturn(deliveryPage);

        Page<Delivery> result = deliveryService.findAllDeliveries(0, 10);
        Assertions.assertEquals(2, result.getContent().size());
        Mockito.verify(deliveryRepository, Mockito.times(1)).findAll(pageRequest);
    }

    @Test
    void testFindDeliveriesByRequest() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Delivery> deliveryPage = new PageImpl<>(Arrays.asList(new Delivery(), new Delivery()));
        Mockito.when(deliveryRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(deliveryPage);

        Page<Delivery> result = deliveryService.findDeliveriesByRequest(0, 10, "searchQuery");

        Assertions.assertEquals(2, result.getContent().size());
        Mockito.verify(deliveryRepository, Mockito.times(1)).findAll(any(Specification.class), eq(pageRequest));
    }

    @Test
    void testCountDeliveries() {

        Mockito.when(deliveryRepository.count()).thenReturn(5L);

        long result = deliveryService.countDeliveries();

        Assertions.assertEquals(5, result);
        Mockito.verify(deliveryRepository, Mockito.times(1)).count();
    }
}
