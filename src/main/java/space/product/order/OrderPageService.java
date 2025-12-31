package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.product.Product;
import space.product.ProductOptionStock;
import space.product.ProductRepository;
import space.user.User;
import space.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderPageService {

    private final ProductRepository productRepository;
    private final ProductOptionStockRepository productOptionStockRepository;
    private final UserRepository userRepository;

    // 옵션 없는 상품
    public OrderPageResponse getOrderPageWithoutOption(
            Long userId,
            Long productId,
            Integer quantity
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        if (product.isUseOption()) {
            throw new IllegalStateException("옵션 상품은 해당 메소드 사용 불가");
        }

        int qty = (quantity != null ? quantity : 1);

        if (qty < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 함");
        }

        if (product.getStock() < qty) {
            throw new IllegalArgumentException("재고 부족");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("로그인 필요"));

        return OrderPageResponse.fromWithoutOption(product, qty, user);
    }

    // 옵션 있는 상품
    public OrderPageResponse getOrderPageWithOptions(
            Long userId,
            Long productId,
            List<Long> optionIds,
            List<Integer> quantities
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        if (!product.isUseOption()) {
            throw new IllegalStateException("옵션 없는 상품은 해당 메소드 사용 불가");
        }

        if (optionIds == null || optionIds.isEmpty()) {
            throw new IllegalArgumentException("옵션이 선택되지 않음");
        }

        if (quantities == null || optionIds.size() != quantities.size()) {
            throw new IllegalArgumentException("옵션 수량 정보 오류");
        }

        List<ProductOptionStock> optionStocks =
                productOptionStockRepository.findAllById(optionIds);

        if (optionStocks.size() != optionIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 옵션 포함");
        }

        List<OrderOptionResponse> optionResponses = new ArrayList<>();

        for (int i = 0; i < optionStocks.size(); i++) {
            ProductOptionStock opt = optionStocks.get(i);
            int qty = quantities.get(i);

            if (!opt.getProduct().getId().equals(productId)) {
                throw new IllegalArgumentException("해당 상품의 옵션이 아님");
            }
            if (qty < 1) {
                throw new IllegalArgumentException("옵션 수량은 1 이상");
            }
            if (opt.getStock() < qty) {
                throw new IllegalArgumentException("옵션 재고 부족");
            }

            optionResponses.add(
                    OrderOptionResponse.from(
                            opt,
                            qty,
                            product.getPrice()
                    )
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("로그인 필요"));

        return OrderPageResponse.fromWithOption(product, optionResponses, user);
    }
}
