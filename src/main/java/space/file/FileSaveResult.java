package space.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileSaveResult {

    private String originalName;
    private String storedName;
    private String filePath;
}
