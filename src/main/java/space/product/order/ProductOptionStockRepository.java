package space.product.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.product.ProductOptionStock;

@Repository
public interface ProductOptionStockRepository extends JpaRepository<ProductOptionStock, Long> {

}
