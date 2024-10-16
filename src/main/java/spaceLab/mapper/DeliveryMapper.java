package spaceLab.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import spaceLab.entity.City;
import spaceLab.entity.Delivery;
import spaceLab.model.delivery.DeliveryRequest;
import spaceLab.model.delivery.DeliveryResponse;
import spaceLab.service.CityService;

@Mapper(componentModel = "spring")
public abstract class DeliveryMapper {

    @Autowired
    private CityService cityService;

    @Mapping(source = "city.id", target = "cityId")
    public abstract DeliveryResponse deliveryToDeliveryResponse(Delivery delivery);

    @Mapping(target = "city", ignore = true)
    public abstract Delivery deliveryRequestToDelivery(DeliveryRequest deliveryRequest);

    @AfterMapping
    protected void mapCity(DeliveryRequest deliveryRequest, @MappingTarget Delivery delivery) {
        if (deliveryRequest.getCityId() != null) {
            City city = cityService.getCity(deliveryRequest.getCityId());
            delivery.setCity(city);
        }
    }
}
