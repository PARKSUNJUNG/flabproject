package space.product.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import space.product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    private Long id;

    private Long userId;

    private String ordererName;
    private String ordererPhone;

    private String receiverName;
    private String receiverPhone;
    private String address;
    private String addressDetail;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem item){
        orderItems.add(item);
        item.setOrder(this);
    }

    public static Order from(Product product, Long userId, OrderCreateRequest request, int totalPrice){

        Order order = new Order();
        order.userId = userId;

        order.ordererName = request.getOrdererName();
        order.ordererPhone = request.getOrdererPhone();
        order.receiverName = request.getReceiverName();
        order.receiverPhone = request.getReceiverPhone();
        order.address = request.getAddress();
        order.addressDetail = request.getAddressDetail();

        order.totalPrice = totalPrice;
        order.status = OrderStatus.CREATED;
        order.orderedAt = LocalDateTime.now();

        OrderItem item = OrderItem.from(product, request);
        order.addOrderItem(item);

        return order;
    }
}
