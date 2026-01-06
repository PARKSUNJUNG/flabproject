package space.product.order.admin;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import space.product.order.Order;
import space.product.order.OrderItem;
import space.product.order.OrderStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminOrderListDto {

    private Long orderId;
    private String ordererName;
    private String ordererPhone;

    private OrderStatus status;
    private LocalDateTime orderedAt;

    private int totalPrice;
    private int totalQuantity; // 주문 총 수량 (옵션 포함 합계)

    public static AdminOrderListDto from(Order order) {

        int totalQuantity = order.getOrderItems()
                .stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        return new AdminOrderListDto(
                order.getId(),
                order.getOrdererName(),
                order.getOrdererPhone(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getTotalPrice(),
                totalQuantity
        );
    }
}
