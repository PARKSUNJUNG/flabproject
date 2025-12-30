package space.product.order;

import lombok.Builder;
import lombok.Getter;
import space.product.ProductOptionStock;

@Getter
@Builder
public class OrderOptionResponse {

    private Long optionStockId;
    private String optionCombination;
    private int stock;

    public static OrderOptionResponse from(ProductOptionStock option){
        return OrderOptionResponse.builder()
                .optionStockId(option.getId())
                .optionCombination(option.getOptCombination())
                .stock(option.getStock())
                .build();
    }
}
