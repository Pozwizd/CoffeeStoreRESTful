package spaceLab.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spaceLab.entity.Delivery;
import spaceLab.model.delivery.DeliveryRequest;

import java.util.List;

@Service
public interface DeliveryService {

    public Delivery saveDelivery(Delivery delivery);

    public Delivery saveDelivery(DeliveryRequest delivery);

    public Delivery getDelivery(Long id);

    public List<Delivery> getAllDeliveries();

    public void deleteDelivery(Delivery delivery);

    public void deleteDelivery(Long id);

    public Page findAllDeliveries(int page, int pageSize);

    public Page findDeliveriesByRequest(int page, int pageSize, String search);

    public long countDeliveries();

}
