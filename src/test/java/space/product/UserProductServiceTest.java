package space.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserProductServiceTest {

    @Autowired
    UserProductService userProductService;

    @Autowired
    ProductRepository productRepository;

    private void saveProducts(int count){
        for(int i=0; i<count; i++){
            Product p = Product.builder()
                    .productName("상품"+i)
                    .price(1000)
                    .stock(10)
                    .useOption(false)
                    .build();
            productRepository.save(p);
        }
    }

    @Test
    void 상품이_0개면_페이지1_상품0개() {
        UserProductListPageResponse result =
                userProductService.getProductList(1, null);

        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getTotalCount()).isEqualTo(0);
        assertThat(result.getProducts().size()).isEqualTo(0);
    }


}