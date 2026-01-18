package space.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import space.file.FileService;
import space.page.PageRequestDto;
import space.page.PageResponseDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/update/notices")
public class UserUpdateNoticeController {

    private final AdminUpdateNoticeService adminUpdateNoticeService;
    private final FileService fileService;

    @GetMapping
    public String list(PageRequestDto req, Model model){

        PageResponseDto<AdminUpdateNoticeListResponse> response =
                adminUpdateNoticeService.getNoticeList(req);

        model.addAttribute("response", response);

        return "user/update/notice/list";
    }

    @GetMapping("/{id}")
    public String noticeDetail(@PathVariable Long id, Model model){

        UpdateNotice notice = adminUpdateNoticeService.getDetail(id);

        UpdateNotice prev = adminUpdateNoticeService.getPrev(id);
        UpdateNotice next = adminUpdateNoticeService.getNext(id);

        model.addAttribute("notice", notice);
        model.addAttribute("prev", prev);
        model.addAttribute("next", next);

        return "user/update/notice/detail";
    }
}
