package space.product.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    // 주문자
    private String ordererName;
    private String ordererPhone;

    // 배송 정보
    private String receiverName;
    private String receiverPhone;
    private String address;
    private String addressDetail;

    // 주문 상품 정보
    private Long productId;
    private List<OrderOptionRequest> options; // 옵션 상품일 경우
    private Integer quantity; // 옵션 없는 상품일 경우

}
