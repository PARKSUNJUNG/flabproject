package space.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateProductRequest {

    private String productName;
    private int price;
    private int stock;
    private Long categoryId;

    private MultipartFile thumbnail;
    private boolean useOption;

    private List<ProductOptionStockRequest> optionStock;
    private List<ProductContentsRequest> productContents;
}
