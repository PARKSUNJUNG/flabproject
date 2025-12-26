package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.product.category.CategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class UserProductController {

    private final UserProductService userProductService;
    private final CategoryService categoryService;

    @GetMapping
    public String productList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Long categoryId,
            Model model
    ){
        int pageSize = 12;

        UserProductListPageResponse result =
                userProductService.getProductList(page, categoryId);

        model.addAttribute("result", result);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categoryId", categoryId);

        return "user/product/list";
    }
}
