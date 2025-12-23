package space.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductContentsRequest {
    private String type;
    private String contents;
    private MultipartFile file;

    public ProductContents toEntity() {
        return ProductContents.builder()
                .type(this.type)
                .build();
    }
}
