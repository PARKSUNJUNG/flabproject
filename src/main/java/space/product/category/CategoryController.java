package space.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /** 카테고리 등록 */
    @PostMapping
    public CategoryResponse create(@RequestBody CreateCategoryRequest req){
        return categoryService.create(req);
    }

    /** 카테고리 목록 조회 */
    @GetMapping
    public List<CategoryResponse> list() {
        return categoryService.findAll();
    }

    /** 카테고리 수정 */
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody UpdateCategoryRequest req) {
        return categoryService.update(id, req);
    }

    /** 카테고리 삭제 */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}