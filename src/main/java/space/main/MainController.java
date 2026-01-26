package space.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.update.AdminUpdateNoticeService;
import space.update.UpdateNotice;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AdminUpdateNoticeService adminUpdateNoticeService;

    @GetMapping({"", "/"})
    public String main(Model model) {

        model.addAttribute("noticeList", adminUpdateNoticeService.findLatest());

        return "user/main";
    }

    @GetMapping("/notice/{id}")
    public String noticeDetail(@PathVariable Long id, Model model){

        UpdateNotice notice = adminUpdateNoticeService.getDetail(id);
        model.addAttribute("notice", notice);

        return "user/update/notice/detail";
    }
}
