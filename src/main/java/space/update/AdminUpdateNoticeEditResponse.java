package space.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminUpdateNoticeEditResponse {

    private Long id;
    private String title;
    private UpdateCategory category;
    private String content;

    private List<AdminUpdateNoticeFileResponse> files;

    public static AdminUpdateNoticeEditResponse from(UpdateNotice notice) {
        return new AdminUpdateNoticeEditResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getCategory(),
                notice.getContent(),
                notice.getFiles().stream()
                        .map(AdminUpdateNoticeFileResponse::from)
                        .toList()
        );
    }
}
