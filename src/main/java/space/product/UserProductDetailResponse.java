package space.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserProductDetailResponse {

    private Long productId;
    private String productName;
    private String thumbnail;
    private String summary;

    private int basePrice;
    private StockStatus stockStatus;
    private boolean useOption;

    private Integer stock; // 옵션 없는 경우

    private List<UserProductOptionResponse> options;
    private List<UserProductContentResponse> productContents;

    private String productSpec;

}
