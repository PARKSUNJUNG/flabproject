package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProductService {

    private final ProductRepository productRepository;

    public UserProductListPageResponse getProductList(int pageNumber, Long categoryId) {
        int pageSize = 12;

        PageRequest pageRequest = PageRequest.of(
                pageNumber - 1,
                pageSize
        );

        Page<Product> page = productRepository.findUserProducts(
                categoryId,
                LocalDateTime.now(),
                pageRequest
        );

        int totalPages = page.getTotalPages();
        if(totalPages == 0) totalPages = 1;

        List<UserProductListResponse> products = page.getContent()
                .stream()
                .map(UserProductListResponse::from)
                .toList();

        return new UserProductListPageResponse(
                pageNumber,
                page.getTotalElements(),
                totalPages,
                products
        );
    }
}
