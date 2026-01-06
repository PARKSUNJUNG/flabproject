package space.product.order.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import space.product.order.OrderItem;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminOrderItemDto {

    private Long OrderItemId;

    private String productName;
    private int price;

    private int totalQuantity; // 옵션 포함 합계 수량

    private List<AdminOrderOptionDto> options;

    public static AdminOrderItemDto from(OrderItem item){

        List<AdminOrderOptionDto> optionDtos;

        if(!item.getOrderOptions().isEmpty()) {
            optionDtos = item.getOrderOptions()
                    .stream()
                    .map(AdminOrderOptionDto::from)
                    .toList();
        } else {
            optionDtos = List.of(
                    new AdminOrderOptionDto(
                            null,
                            "기본",
                            item.getQuantity()
                    )
            );
        }

        int totalQuantity = optionDtos.stream()
                .mapToInt(AdminOrderOptionDto::getQuantity)
                .sum();

        return new AdminOrderItemDto(
                item.getId(),
                item.getProductName(),
                item.getPrice(),
                totalQuantity,
                optionDtos
        );
    }
}
