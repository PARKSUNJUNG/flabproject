package space.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AdminUpdateRegisterRequest {

    private String title;
    private UpdateCategory category;
    private String content;
    private List<MultipartFile> files;

    // 엔티티 생성
    public UpdateNotice toEntity() {
        return new UpdateNotice(title, category, content);
    }
}
