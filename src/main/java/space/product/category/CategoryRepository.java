package space.product.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findAllByOrderBySortAsc();

    @Query("select coalesce(max(c.sort), 0) from Category c")
    Integer findMaxSort();
}
