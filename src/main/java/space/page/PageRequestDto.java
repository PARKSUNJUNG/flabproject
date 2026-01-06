package space.page;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageRequestDto {

    private int page = 1;  // 현재 페이지
    private int size = 10; // 페이지당 개수

    public int getOffset(){
        return (page - 1) * size;
    }
}
