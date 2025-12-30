package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.product.Product;
import space.product.ProductOptionStock;
import space.product.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderPageService {

    private final ProductRepository productRepository;
    private final ProductOptionStockRepository productOptionStockRepository;

    public OrderPageResponse getOrderPage(
            Long productId,
            List<Long> optionIds,
            int quantity
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        List<OrderOptionResponse> optionResponses = List.of();

        if (product.isUseOption()) {
            if (optionIds == null || optionIds.isEmpty()) {
                throw new IllegalArgumentException("옵션 상품인데 옵션이 선택되지 않음");
            }

            List<ProductOptionStock> selectedOptions =
                    productOptionStockRepository.findAllById(optionIds);

            if (selectedOptions.size() != optionIds.size()) {
                throw new IllegalArgumentException("존재하지 않는 옵션 포함");
            }

            boolean invalidOption =
                    selectedOptions.stream()
                            .anyMatch(o -> !o.getProduct().getId().equals(productId));

            if (invalidOption) {
                throw new IllegalArgumentException("해당 상품의 옵션이 아님");
            }

            optionResponses = selectedOptions.stream()
                    .map(OrderOptionResponse::from)
                    .toList();
        } else {
            if (quantity < 1) {
                throw new IllegalArgumentException("수량은 1 이상이어야 함");
            }
        }

        int finalQuantity = product.isUseOption() ? 1 : quantity;

        return OrderPageResponse.from(product, optionResponses, finalQuantity);
    }
}

