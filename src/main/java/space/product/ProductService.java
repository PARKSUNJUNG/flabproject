package space.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.file.FileCategory;
import space.file.FileService;
import space.page.PageRequestDto;
import space.page.PageResponseDto;
import space.product.category.Category;
import space.product.category.CategoryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    /** 상품 등록 */
    public RegisterProductResponse registerProduct(RegisterProductRequest req){

        // 1. 카테고리 조회
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        // 2. 썸네일 업로드
        String thumbnailUrl = fileService.saveAndReturnUrl(req.getThumbnail(), FileCategory.PRODUCT_THUMBNAIL);

        // 3. Product 엔티티 생성
        Product product = req.toEntity();
        product.setCategory(category);
        product.setThumbnail(thumbnailUrl);

        if(req.isUseOption()){
            product.setStock(0);
        }

        // 4. 옵션 재고 처리
        if(req.isUseOption() && req.getOptionStocks() != null){
            for(ProductOptionStockRequest s : req.getOptionStocks()){
                ProductOptionStock stock = s.toEntity(product);
                product.getOptionStock().add(stock);
            }
        }

        // 5. 상품 컨텐츠
        if(req.getProductContents() != null){
            for(ProductContentsRequest c : req.getProductContents()){
                ProductContents contents = c.toEntity();

                if("image".equals(c.getType()) && c.getFile() != null && !c.getFile().isEmpty()){
                    String path = fileService.saveAndReturnUrl(c.getFile(), FileCategory.PRODUCT_CONTENT);
                    contents.setContents(path);
                } else {
                    contents.setContents(c.getContents());
                }

                product.addContent(contents);
            }
        }

        Product saved = productRepository.save(product);

        int totalStock = calculateTotalStock(saved);

        return new RegisterProductResponse(
                saved.getId(),
                saved.getProductName(),
                totalStock > 0 ? "IN_STOCK" : "OUT_OF_STOCK"
        );
    }

    /** 상품 목록 */
    @Transactional(readOnly = true)
    public PageResponseDto<ProductResponse> findAll(PageRequestDto req) {

        Pageable pageable = PageRequest.of(
                req.getPage() -1,
                req.getSize(),
                Sort.by(
                        Sort.Order.asc("category.name"),
                        Sort.Order.asc("productName")
                )
        );

        List<Product> products = productRepository.findAllWithOptionStock(pageable);

        long totalCount = productRepository.countAllWithOptionStock();

        List<ProductResponse> content = products.stream()
                .map(ProductResponse::from)
                .toList();

        return new PageResponseDto<>(content, req, totalCount);
    }

    /** 상품 조회 (수정 화면용) */
    @Transactional(readOnly = true)
    public Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    /** 상품 수정 */
    public ProductResponse update(Long id, UpdateProductRequest req){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("상품 없음"));

        // 기본 정보 update
        product.setProductName(req.getProductName());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());

        // 카테고리 update
        if(!product.getCategory().getId().equals(req.getCategoryId())){
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(()-> new IllegalArgumentException("카테고리 없음"));
            product.setCategory(category);
        }

        // 썸네일 update
        if(req.getThumbnail() != null && !req.getThumbnail().isEmpty()){
            String thumbnailUrl = fileService.saveAndReturnUrl(req.getThumbnail(), FileCategory.PRODUCT_THUMBNAIL);
            product.setThumbnail(thumbnailUrl);
        }

        // 판매기간 update
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
        product.setSaleStart(LocalDateTime.parse(req.getSaleStart() , formatter));
        product.setSaleEnd(LocalDateTime.parse(req.getSaleEnd(), formatter));

        // 옵션 재고 update
        if(req.isUseOption()){
            product.setStock(0);
            product.getOptionStock().clear();

            if(req.getOptionStock() != null && !req.getOptionStock().isEmpty()){
                for(ProductOptionStockRequest s : req.getOptionStock()){
                    ProductOptionStock stock = s.toEntity(product);
                    product.getOptionStock().add(stock);
                }
            }
        } else {
            product.getOptionStock().clear();
            product.setStock(req.getStock());
        }

        // 상품 상세 update
        if(req.getProductContents() != null){
            product.getContents().clear();

            for(ProductContentsRequest c : req.getProductContents()){
                ProductContents contents = c.toEntity();

                if("image".equals(c.getType()) && c.getFile() != null && !c.getFile().isEmpty()){
                    String path = fileService.saveAndReturnUrl(c.getFile(), FileCategory.PRODUCT_CONTENT);
                    contents.setContents(path);
                } else {
                    contents.setContents(c.getContents());
                }

                product.addContent(contents);
            }
        }

        return ProductResponse.from(product);
    }

    /** 상품 삭제 */
    public void delete(Long id){
        if(!productRepository.existsById(id)){
            throw new IllegalArgumentException("이미 삭제되었거나 없는 상품입니다.");
        }
        productRepository.deleteById(id);
    }

    /** 실제 재고 계산 */
    public int calculateTotalStock(Product product) {
        if (product.isUseOption()) {
            return product.getOptionStock()
                    .stream()
                    .mapToInt(ProductOptionStock::getStock)
                    .sum();
        }
        return product.getStock();
    }
}
