package space.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AdminUpdateNoticeEditRequest {

    private String title;
    private UpdateCategory category;
    private String content;

    public void apply(UpdateNotice notice){
        notice.update(title, category, content);
    }

}
