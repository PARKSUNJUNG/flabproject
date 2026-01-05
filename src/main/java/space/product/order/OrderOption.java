package space.product.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderOption {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private Long optionId;
    private String optionName;
    private int quantity;

    public void setOrderItem(OrderItem orderItem){
        this.orderItem = orderItem;
    }

    public static OrderOption from(OrderOptionRequest request){
        OrderOption option = new OrderOption();
        option.optionId = request.getOptionStockId();
        option.optionName = request.getOptionName();
        option.quantity = request.getQuantity();

        return option;

    }
}
