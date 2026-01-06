package space.product.order.admin;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import space.product.order.Order;
import space.product.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AdminOrderDetailDto {

    private Long orderId;

    // 주문자 정보
    private String ordererName;
    private String ordererPhone;

    // 배송 정보
    private String receiverName;
    private String receiverPhone;
    private String address;
    private String addressDetail;

    // 주문 상태
    private OrderStatus status;
    private LocalDateTime orderedAt;

    // 주문 상품
    private List<AdminOrderItemDto> items;

    private int totalPrice;

    public static AdminOrderDetailDto from(Order order) {

        List<AdminOrderItemDto> itemDtos = order.getOrderItems()
                .stream()
                .map(AdminOrderItemDto::from)
                .toList();

        return new AdminOrderDetailDto(
                order.getId(),
                order.getOrdererName(),
                order.getOrdererPhone(),
                order.getReceiverName(),
                order.getReceiverPhone(),
                order.getAddress(),
                order.getAddressDetail(),
                order.getStatus(),
                order.getOrderedAt(),
                itemDtos,
                order.getTotalPrice()
        );
    }
}
