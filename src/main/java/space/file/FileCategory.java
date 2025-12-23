package space.file;

import lombok.Getter;

@Getter
public enum FileCategory {
    PRODUCT_THUMBNAIL("images/product/thumbnail"),
    PRODUCT_CONTENT("images/product/content");

    private final String dir;

    FileCategory(String dir) {
        this.dir = dir;
    }
}
