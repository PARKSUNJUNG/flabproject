package space.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_contents")
@Data
@NoArgsConstructor
public class ProductContents {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public ProductContents(String type, String contents, Product product) {
        this.type = type;
        this.contents = contents;
        this.product = product;
    }
}
