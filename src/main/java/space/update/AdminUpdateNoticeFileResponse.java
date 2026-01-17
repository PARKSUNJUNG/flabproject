package space.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUpdateNoticeFileResponse {

    private Long id;
    private String originalName;
    private String filePath;

    public static AdminUpdateNoticeFileResponse from(UpdateFile file){
        return new AdminUpdateNoticeFileResponse(
                file.getId(),
                file.getOriginalName(),
                file.getFilePath()
        );
    }
}
