package space.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_stock")
@Data
@NoArgsConstructor
public class ProductOptionStock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String optCombination;

    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void decrease(int quantity) {
        if(this.stock < quantity) {
            throw new IllegalStateException("재고 부족");
        }
        this.stock -= quantity;
    }

    @Builder
    public ProductOptionStock(String optCombination, Integer stock, Product product) {
        this.optCombination = optCombination;
        this.stock = stock;
        this.product = product;
    }
}
