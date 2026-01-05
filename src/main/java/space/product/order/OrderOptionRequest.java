package space.product.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOptionRequest {
    private Long optionStockId;
    private String optionName;
    private int quantity;
}
