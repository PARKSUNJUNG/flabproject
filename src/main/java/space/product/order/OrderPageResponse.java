package space.product.order;

import lombok.Builder;
import lombok.Getter;
import space.product.Product;

import java.util.List;

@Getter
@Builder
public class OrderPageResponse {

    private Long productId;
    private String productName;
    private int price;
    private boolean useOption;

    private List<OrderOptionResponse> selectedOptions;
    private int quantity;
    private int totalPrice;

    public static OrderPageResponse from(
            Product product,
            List<OrderOptionResponse> options,
            int quantity
    ) {
        int basePrice = product.getPrice();

        int totalPrice;
        if (product.isUseOption()) {
            // 옵션 개수 * 가격
            totalPrice = basePrice * options.size();
        } else {
            totalPrice = basePrice * quantity;
        }

        return OrderPageResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(basePrice)
                .useOption(product.isUseOption())
                .selectedOptions(options)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}

