package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class UserProductDetailController {

    private final UserProductDetailService userProductDetailService;

    @GetMapping("/{productId}")
    public String productDetail(@PathVariable Long productId, Model model){
        UserProductDetailResponse product = userProductDetailService.getProductDetail(productId);

        model.addAttribute("product", product);
        return "user/product/detail";
    }
}
