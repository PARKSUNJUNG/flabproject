package space.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProductListResponse {

    private Long productId;
    private String productName;
    private Long categoryId;
    private String thumbnail;
    private String summary;
    private Integer price;
    private StockStatus stockStatus;

    public static UserProductListResponse from(Product product){
        return new UserProductListResponse(
                product.getId(),
                product.getProductName(),
                product.getCategory().getId(),
                product.getThumbnail(),
                product.getSummary(),
                product.getPrice(),
                product.getTotalStock() > 0
                        ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK
        );
    }
}
