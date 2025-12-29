package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProductDetailService {

    private final ProductRepository productRepository;

    public UserProductDetailResponse getProductDetail(Long productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("상품 없음"));

        boolean useOption = product.isUseOption();

        StockStatus stockStatus;
        Integer stock = null;
        List<UserProductOptionResponse> optionResponses = new ArrayList<>();

        if(useOption){
            // 옵션 상품
            optionResponses = product.getOptionStock().stream()
                    .map(UserProductOptionResponse::from)
                    .toList();

            boolean hasStock = optionResponses.stream().anyMatch(o -> o.getStock() > 0);

            stockStatus = hasStock ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK;

        } else {
            // 단일 상품
            stock = product.getStock();
            stockStatus = stock > 0 ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK;
        }

        List<UserProductContentResponse> contentResponses =
                product.getContents().stream()
                        .map(UserProductContentResponse::from)
                        .toList();

        return new UserProductDetailResponse(
                product.getId(),
                product.getProductName(),
                product.getThumbnail(),
                product.getSummary(),
                product.getPrice(),
                stockStatus,
                useOption,
                stock,
                optionResponses,  //options
                contentResponses, //productContents
                product.getProductSpec()
        );
    }
}
