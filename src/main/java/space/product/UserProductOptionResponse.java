package space.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProductOptionResponse {

    private Long optionId;
    private String optionCombination;
    private int stock;
    private StockStatus stockStatus;

    public static UserProductOptionResponse from(ProductOptionStock optionStock) {

        StockStatus stockStatus =
                optionStock.getStock() > 0
                        ? StockStatus.IN_STOCK
                        : StockStatus.OUT_OF_STOCK;

        return new UserProductOptionResponse(
                optionStock.getId(),
                optionStock.getOptCombination(),
                optionStock.getStock(),
                stockStatus
        );
    }

}
