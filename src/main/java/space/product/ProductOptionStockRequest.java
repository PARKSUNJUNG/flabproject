package space.product;

import lombok.Data;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Data
public class ProductOptionStockRequest {
    private String optCombination;
    private Integer stock;

    public ProductOptionStock toEntity(Product product){
        return ProductOptionStock.builder()
                .optCombination(this.optCombination)
                .stock(this.stock != null ? this.stock : 0)
                .product(product)
                .build();
    }
}
