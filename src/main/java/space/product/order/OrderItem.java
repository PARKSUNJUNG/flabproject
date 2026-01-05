package space.product.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.product.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
    private List<OrderOption> orderOptions = new ArrayList<>();

    private Long productId;
    private String productName;
    private int price;
    private int quantity;

    public void setOrder(Order order){
        this.order = order;
    }

    public void addOption(OrderOption option){
        orderOptions.add(option);
        option.setOrderItem(this);
    }

    public static OrderItem from(Product product, OrderCreateRequest request){
        OrderItem item = new OrderItem();
        item.productId = request.getProductId();;
        item.productName = product.getProductName();
        item.price = product.getPrice();
        if(product.isUseOption()){
            // 옵션 상품
            int totalQuantity = 0;

            for(OrderOptionRequest opt : request.getOptions()){
                OrderOption option = OrderOption.from(opt);
                item.addOption(option);
                totalQuantity += opt.getQuantity();
            }

            item.quantity = totalQuantity;
        } else {
            // 단일 상품
            item.quantity = request.getQuantity();
        }

        return item;
    }
}
