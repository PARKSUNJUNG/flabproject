package space.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterProductResponse {

    private Long id;
    private String productName;
    private String stockStatus;
}
