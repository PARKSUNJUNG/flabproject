package space.product.order;

import lombok.Builder;
import lombok.Getter;
import space.product.ProductOptionStock;

@Getter
@Builder
public class OrderOptionResponse {

    private Long optionStockId;
    private String optionCombination;

    private int quantity;   // 주문 수량
    private int price;      // 옵션 1개 가격
    private int totalPrice; // price * quantity
    private int stock;

    public static OrderOptionResponse from(
            ProductOptionStock option,
            int quantity,
            int basePrice
    ) {
        return OrderOptionResponse.builder()
                .optionStockId(option.getId())
                .optionCombination(option.getOptCombination())
                .quantity(quantity)
                .price(basePrice)
                .totalPrice(basePrice * quantity)
                .stock(option.getStock())
                .build();
    }
}

