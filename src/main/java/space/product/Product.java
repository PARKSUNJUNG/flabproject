package space.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.product.category.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String thumbnail;
    private String summary;
    private Integer price;
    private Integer stock;
    private boolean useOption;

    private LocalDateTime saleStart;
    private LocalDateTime saleEnd;

    @Lob
    private String productSpec;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionStock> optionStock = new ArrayList<>();

    public int getTotalStock() {
        if(this.useOption){
            return this.optionStock.stream()
                    .mapToInt(ProductOptionStock::getStock)
                    .sum();
        }
        return this.stock;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductContents> contents = new ArrayList<>();

    public void addContent(ProductContents content){
        contents.add(content);    // 객체 상태
        content.setProduct(this); // DB FK 상태
    }

    @Builder
    public Product(String productName, String summary, Integer price, Integer stock, boolean useOption, LocalDateTime saleStart, LocalDateTime saleEnd, String productSpec) {
        this.productName = productName;
        this.summary = summary;
        this.price = price;
        this.stock = stock;
        this.useOption = useOption;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
        this.productSpec = productSpec;
    }
}
