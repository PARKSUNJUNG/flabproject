package space.product.order.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import space.product.order.OrderOption;

@Getter
@AllArgsConstructor
public class AdminOrderOptionDto {

    private Long optionStockId;
    private String optionName;
    private int quantity;

    public static AdminOrderOptionDto from(OrderOption option){
        return new AdminOrderOptionDto(
                option.getOptionId(),
                option.getOptionName(),
                option.getQuantity()
        );
    }
}
