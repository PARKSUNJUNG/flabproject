package space.product.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import space.user.UserPrincipal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrder(
            @AuthenticationPrincipal UserPrincipal user,
            @ModelAttribute OrderCreateRequest request,
            RedirectAttributes redirectAttributes
    ){
        if(user == null) return "redirect:/login";

        orderService.createOrder(user.getId(), request);
        redirectAttributes.addFlashAttribute("orderComplete", true);

        return "redirect:/products";
    }
}
