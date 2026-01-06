package space.page;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    private boolean hasPrev;
    private boolean hasNext;

    public PageResponseDto(List<T> content, PageRequestDto req, long totalCount){
        this.content = content;
        this.page = req.getPage();
        this.size = req.getSize();
        this.totalCount = totalCount;
        this.totalPages = (int)Math.ceil((double) totalCount/size);
        this.hasPrev = page > 1;
        this.hasNext = page < totalPages;
    }
}
