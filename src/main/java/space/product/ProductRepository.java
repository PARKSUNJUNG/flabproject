package space.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        select distinct p
        from Product p
        join fetch p.category c
        left join fetch p.optionStock
        order by c.name asc, p.productName asc
    """)
    List<Product> findAllWithOptionStock();

    @Query("""
         select p
         from Product p
         left join p.optionStock os
         where (:categoryId is null or p.category.id = :categoryId)
            and (p.saleStart is null or p.saleStart <= :now)
            and (p.saleEnd is null or p.saleEnd >= :now)
         group by p
         order by
            case
              when p.useOption = false and p.stock = 0 then 1
              when p.useOption = true and coalesce(sum(os.stock), 0) = 0 then 1
              else 0
            end,
            p.id desc
    """)
    Page<Product> findUserProducts(
            @Param("categoryId") Long categoryId,
            @Param("now")LocalDateTime now,
            Pageable pageable
    );
}
