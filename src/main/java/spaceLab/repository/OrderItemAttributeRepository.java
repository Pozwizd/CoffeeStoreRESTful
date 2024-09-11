package spaceLab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import spaceLab.entity.OrderItem;
import spaceLab.entity.OrderItemAttribute;

import java.util.Iterator;

public interface OrderItemAttributeRepository extends JpaRepository<OrderItemAttribute, Long> {
    Iterator<OrderItemAttribute> findByOrderItem_Id(Long id);

    long deleteByOrderItem(OrderItem orderItem);

    Iterable<OrderItemAttribute> findOrderItemAttributeByOrderItem_Id(Long id);

    @Modifying
    @Query("delete from OrderItemAttribute where orderItem.id = ?1")
    void deleteByOrderItem_Id(Long id);
}