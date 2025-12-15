package space.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/register")
    public String registForm(Model model) {
        model.addAttribute("category", new CreateCategoryRequest());
        return "admin/product/category/register";
    }

    @GetMapping("/list")
    public String listForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product/category/list";
    }
}
