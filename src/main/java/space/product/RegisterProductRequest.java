package space.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class RegisterProductRequest {

    private Long productId;
    private String productName;
    private Long categoryId;
    private MultipartFile thumbnail;
    private String summary;
    private Integer price;
    private Integer stock;
    private boolean useOption;

    private List<ProductOptionStockRequest> optionStocks;
    private List<ProductContentsRequest> productContents;

    private String saleStart;
    private String saleEnd;
    private String productSpec;

    public Product toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
        return Product.builder()
                .productName(this.productName)
                .summary(this.summary)
                .price(this.price)
                .stock(this.stock)
                .useOption(this.useOption)
                .saleStart(LocalDateTime.parse(this.saleStart,formatter))
                .saleEnd(LocalDateTime.parse(this.saleEnd, formatter))
                .productSpec(this.productSpec)
                .build();
    }
}
