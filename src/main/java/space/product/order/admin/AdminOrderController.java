package space.product.order.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.page.PageRequestDto;
import space.page.PageResponseDto;
import space.product.order.Order;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping
    public String orderList(PageRequestDto pageRequest, Model model) {

        PageResponseDto<AdminOrderListDto> orders = adminOrderService.getOrderList(pageRequest);

        model.addAttribute("orders", orders);
        return "admin/product/order/list";
    }

    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model){
        model.addAttribute("order", adminOrderService.getOrderDetail(orderId));
        return "admin/product/order/detail";
    }
}
