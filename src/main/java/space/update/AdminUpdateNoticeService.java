package space.update;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.file.FileCategory;
import space.file.FileSaveResult;
import space.file.FileService;
import space.page.PageRequestDto;
import space.page.PageResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminUpdateNoticeService {

    private final UpdateNoticeRepository updateNoticeRepository;
    private final FileService fileService;

    public AdminUpdateRegisterResponse register (AdminUpdateRegisterRequest req){

        // 1. 게시글 엔티티 생성
        UpdateNotice notice = req.toEntity();

        // 2. 첨부파일 처리
        if(req.getFiles() != null && !req.getFiles().isEmpty()){
            for(MultipartFile file : req.getFiles()){
                if (file.isEmpty()) continue;

                FileSaveResult result = fileService.saveFile(file, FileCategory.UPDATE_NOTICE);

                UpdateFile updateFile = new UpdateFile(
                        result.getOriginalName(),
                        result.getStoredName(),
                        result.getFilePath()
                );

                notice.addFile(updateFile);
            }
        }

        // 3. 저장
        UpdateNotice saved = updateNoticeRepository.save(notice);

        return AdminUpdateRegisterResponse.from(saved);
    }

    public PageResponseDto<AdminUpdateNoticeListResponse> getNoticeList(PageRequestDto req){

        Pageable pageable = PageRequest.of(
                req.getPage() - 1,
                req.getSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        List<UpdateNotice> notices = updateNoticeRepository.findAdminList(pageable);

        long totalCount = updateNoticeRepository.countAdminList();

        List<AdminUpdateNoticeListResponse> content =
                notices.stream()
                        .map(AdminUpdateNoticeListResponse::from)
                        .toList();

        return new PageResponseDto<>(content, req, totalCount);
    }
}