package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.product.Product;
import space.product.ProductOptionStock;
import space.product.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final ProductOptionStockRepository productOptionStockRepository;
    private final OrderRepository orderRepository;

    public void createOrder(Long userId, OrderCreateRequest request){

       Product product = productRepository.findById(request.getProductId())
               .orElseThrow(()->new IllegalArgumentException("상품 없음"));

       // 옵션 / 단일 상품 검증
       if(product.isUseOption()){
           if(request.getOptions() == null || request.getOptions().isEmpty()){
               throw new IllegalArgumentException("옵션이 필요합니다.");
           }
       } else {
           if(request.getQuantity() == null || request.getQuantity() <= 0){
                throw new IllegalArgumentException("수량 오류");
           }
       }

       int totalPrice = 0;
       int totalQuantity = 0;

       // 옵션 상품
       if(product.isUseOption() && request.getOptions() != null){
           for(OrderOptionRequest opt : request.getOptions()){

               ProductOptionStock stock = productOptionStockRepository.findById(opt.getOptionStockId())
                       .orElseThrow(()->new IllegalArgumentException("옵션 없음"));

               opt.setOptionName(stock.getOptCombination());
               totalQuantity += opt.getQuantity();

               // 재고 차감
               stock.decrease(opt.getQuantity());
           }
           totalPrice = product.getPrice() * totalQuantity;
       } else {
           totalPrice = product.getPrice() * request.getQuantity();
           product.decreaseStock(request.getQuantity());
       }

        // 주문 저장
        Order order = Order.from(product, userId, request, totalPrice);

        orderRepository.save(order);
    }
}
