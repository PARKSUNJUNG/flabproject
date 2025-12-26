package space.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserProductListPageResponse {

    private int pageNumber;
    private Long totalCount;
    private int totalPages;
    private List<UserProductListResponse> products;
}
