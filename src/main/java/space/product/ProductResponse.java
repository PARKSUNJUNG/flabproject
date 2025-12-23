package space.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private int price;
    private int totalStock;
    private String categoryName;

    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getTotalStock(),
                product.getCategory().getName()
        );
    }
}
