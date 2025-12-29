package space.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProductContentResponse {

    private String type;
    private String contents;

    public static UserProductContentResponse from(ProductContents entity){
        return new UserProductContentResponse(
                entity.getType(),
                entity.getContents()
        );
    }
}
