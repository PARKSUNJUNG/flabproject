package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.user.UserPrincipal;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class OrderPageController {

    private final OrderPageService orderPageService;

    @GetMapping("/{productId}/order")
    public String orderPage(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long productId,
            @RequestParam(required = false) Integer quantity, // 옵션 없는 상품
            @RequestParam(required = false) List<Long> optionIds, // 옵션 있는 상품
            @RequestParam(required = false) List<Integer> quantities, // 옵션 있는 상품
            Model model
    ){

        OrderPageResponse response;

        if (optionIds != null && !optionIds.isEmpty()) {
            // 옵션 있는 상품
            response = orderPageService.getOrderPageWithOptions(
                    user.getId(), productId, optionIds, quantities
            );
        } else {
            // 옵션 없는 상품
            response = orderPageService.getOrderPageWithoutOption(
                    user.getId(), productId, quantity
            );
        }

        model.addAttribute("order", response);
        return "user/product/order";
    }
}
