package space.file;

import lombok.Getter;

@Getter
public enum FileCategory {
    PRODUCT_THUMBNAIL("images/product/thumbnail"),
    PRODUCT_CONTENT("images/product/content"),
    UPDATE_NOTICE("files/update/notice"),
    UPDATE_NOTICE_EDITOR("files/update/notice/editor");

    private final String dir;

    FileCategory(String dir) {
        this.dir = dir;
    }
}
