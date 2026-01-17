package space.update;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private final UpdateFileRepository updateFileRepository;
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

    @Transactional(readOnly = true)
    public AdminUpdateNoticeEditResponse getNoticeEditView(Long id){
        UpdateNotice updateNotice = updateNoticeRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        return AdminUpdateNoticeEditResponse.from(updateNotice);
    }

    public void update(Long id,
                       AdminUpdateNoticeEditRequest req,
                       List<MultipartFile> files,
                       String deleteFileIds
    ){
        UpdateNotice notice = updateNoticeRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // 게시글 수정
        req.apply(notice);

        // 삭제할 파일이 있을 때만 동작
        if(deleteFileIds != null && !deleteFileIds.isBlank()){
            for(String idStr : deleteFileIds.split(",")){
                Long fileId = Long.valueOf(idStr);

                UpdateFile file = updateFileRepository.findById(fileId)
                        .orElseThrow(()-> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

                notice.getFiles().remove(file);
            }
        }

        // 새 파일 추가
        if(files != null && !files.isEmpty()){
            for(MultipartFile file : files){
                if(file.isEmpty()) continue;

                FileSaveResult result = fileService.saveFile(file, FileCategory.UPDATE_NOTICE);

                UpdateFile updateFile = new UpdateFile(
                        result.getOriginalName(),
                        result.getStoredName(),
                        result.getFilePath()
                );

                notice.addFile(updateFile);
            }
        }
    }

    public void delete(Long id){
        UpdateNotice notice = updateNoticeRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        updateNoticeRepository.delete(notice);
    }
}