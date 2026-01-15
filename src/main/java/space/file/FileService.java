package space.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-path}")
    private String uploadPath;

    public FileSaveResult saveFile(MultipartFile file, FileCategory category) {
        if(file == null || file.isEmpty()) return null;

        String dirPath = category.getDir();
        Path saveDir = Paths.get(uploadPath, dirPath);

        String original = file.getOriginalFilename();
        String ext = original.substring(original.lastIndexOf("."));
        String fileName = UUID.randomUUID() + ext;

        // 실제 저장 위치를 Path 객체로 만듦
        Path savePath = saveDir.resolve(fileName);

        try{
            Files.createDirectories(saveDir);
            Files.copy(file.getInputStream(), savePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        String filePath = "/uploads/" + dirPath + "/" +fileName; // DB에 저장할 경로

        return new FileSaveResult(original, fileName, filePath);
    }

    public String saveAndReturnUrl(MultipartFile file, FileCategory category){

        FileSaveResult result = saveFile(file, category);

        return result.getFilePath();
    }
}
