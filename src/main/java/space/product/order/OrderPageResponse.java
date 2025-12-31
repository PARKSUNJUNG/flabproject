package space.product.order;

import lombok.Builder;
import lombok.Getter;
import space.product.Product;
import space.user.User;

import java.util.List;

@Getter
@Builder
public class OrderPageResponse {

    // 상품 정보
    private Long productId;
    private String productName;
    private int price;
    private boolean useOption;

    // 주문 정보
    private List<OrderOptionResponse> selectedOptions;
    private Integer quantity;   // 옵션 없는 상품만 사용
    private int totalPrice;

    // 배송 정보
    private String ordererName;
    private String ordererPhone;
    private String receiverName;
    private String receiverPhone;
    private String address;
    private String addressDetail;

    public static OrderPageResponse fromWithoutOption(
            Product product,
            int quantity,
            User user
    ) {
        int basePrice = product.getPrice();
        int totalPrice = basePrice * quantity;

        return OrderPageResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(basePrice)
                .useOption(false)
                .selectedOptions(List.of())
                .quantity(quantity)
                .totalPrice(totalPrice)
                .ordererName(user.getName())
                .ordererPhone(user.getPhone())
                .receiverName(user.getName())
                .receiverPhone(user.getPhone())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .build();
    }

    public static OrderPageResponse fromWithOption(
            Product product,
            List<OrderOptionResponse> options,
            User user
    ) {
        int totalPrice =
                options.stream()
                        .mapToInt(OrderOptionResponse::getTotalPrice)
                        .sum();

        return OrderPageResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .useOption(true)
                .selectedOptions(options)
                .totalPrice(totalPrice)
                .ordererName(user.getName())
                .ordererPhone(user.getPhone())
                .receiverName(user.getName())
                .receiverPhone(user.getPhone())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .build();
    }
}

