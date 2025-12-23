package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.product.category.CategoryService;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /** 상품 등록 화면 */
    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("request", new RegisterProductRequest());
        return "admin/product/register";
    }

    /** 상품 등록 */
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterProductRequest req){
        productService.registerProduct(req);
        return "redirect:/admin/product/list";
    }

    /** 상품 목록 화면 */
    @GetMapping("/list")
    public String listForm(Model model) {

        model.addAttribute("products", productService.findAll());
        return "admin/product/list";
    }

    /** 상품 수정 화면 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){

        Product product = productService.findEntityById(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());

        return "admin/product/edit";
    }

    /** 상품 수정 */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute UpdateProductRequest req){
        productService.update(id, req);
        return "redirect:/admin/product/list";
    }

    /** 상품 삭제 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/admin/product/list";
    }
}
