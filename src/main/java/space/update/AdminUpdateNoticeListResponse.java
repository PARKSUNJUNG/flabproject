package space.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminUpdateNoticeListResponse {

    private Long id;
    private UpdateCategory updateCategory;
    private String title;
    private LocalDateTime createdAt;

    public static AdminUpdateNoticeListResponse from(UpdateNotice notice){
        return new AdminUpdateNoticeListResponse(
                notice.getId(),
                notice.getCategory(),
                notice.getTitle(),
                notice.getCreatedAt()
        );
    }
}
