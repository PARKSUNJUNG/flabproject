package space.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
