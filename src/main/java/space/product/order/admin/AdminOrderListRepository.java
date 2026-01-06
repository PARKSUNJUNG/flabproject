package space.product.order.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.product.order.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminOrderListRepository extends JpaRepository<Order, Long> {

    @Query("""
            select o
            from Order o
            order by o.orderedAt desc
            """)
    List<Order> findAdminList(Pageable pageable);

    @Query("""
        select count(o)
        from Order o
    """)
    long countAdminList();

    @Query("""
            select distinct o
            from Order o
            join fetch o.orderItems oi
            where o.id = :orderId
            """)
    Optional<Order> findDetailById(@Param("orderId") Long orderId);
}
