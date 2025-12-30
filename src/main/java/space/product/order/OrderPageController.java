package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class OrderPageController {

    private final OrderPageService orderPageService;

    @GetMapping("/{productId}/order")
    public String orderPage(@PathVariable Long productId,
                            @RequestParam(required = false) List<Long> optionIds,
                            @RequestParam(defaultValue = "1") int quantity,
                            Model model){

        OrderPageResponse response =
                orderPageService.getOrderPage(productId, optionIds, quantity);

        model.addAttribute("order", response);
        return "user/product/order";
    }
}
