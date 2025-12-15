package space.product.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /** 카테고리 등록 */
    public CategoryResponse create(CreateCategoryRequest req) {
        if(categoryRepository.existsByName(req.getName())){
            throw new IllegalArgumentException("이미 존재하는 카테고리 입니다.");
        }

        int maxOrder = categoryRepository.findMaxSort();

        Category category = Category.builder()
                .name(req.getName())
                .sort(maxOrder + 1)
                .build();

        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName());
    }

    /** 카테고리 목록 조회 */
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllByOrderBySortAsc()
                .stream()
                .map(c->new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    /** 카테고리 수정 */
    @Transactional
    public CategoryResponse update(Long id, UpdateCategoryRequest req) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        category.setName(req.getName());

        return new CategoryResponse(category.getId(), category.getName());
    }

    /** 카테고리 삭제 */
    public void delete(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 카테고리입니다.");
        }

        categoryRepository.deleteById(id);
    }
}
