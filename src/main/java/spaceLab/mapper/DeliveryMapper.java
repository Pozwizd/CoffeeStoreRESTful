package spaceLab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spaceLab.entity.Delivery;
import spaceLab.model.delivery.DeliveryResponse;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "city.id", target = "cityId")
    DeliveryResponse deliveryToDeliveryResponse(Delivery delivery);
}

