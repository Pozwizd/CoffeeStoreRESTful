package spaceLab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spaceLab.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COUNT(o) FROM Order o")
    Integer countAllOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.dateTimeOfCreate >= CURRENT_DATE")
    Integer countTodayOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE'")
    Long findAllDoneOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'DONE' AND o.dateTimeOfCreate >= CURRENT_DATE")
    Long findAllDoneOrdersToday();

    Page<Order> findAll(Specification<Order> and, Pageable pageable);


    @Query("SELECT o FROM Order o ORDER BY o.dateTimeOfCreate DESC LIMIT 5")
    List<Order> getLastOrdersForStatistics();

    List<Order> findAllByCustomerId(Long id);

    Page<Order> findByCustomer_Id(Long customerId, Pageable pageable);
}