package space.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import space.file.FileCategory;
import space.file.FileSaveResult;
import space.file.FileService;
import space.page.PageRequestDto;
import space.page.PageResponseDto;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/update/notices")
public class AdminUpdateNoticeController {

    private final AdminUpdateNoticeService adminUpdateNoticeService;
    private final FileService fileService;

    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("categories", UpdateCategory.values());
        model.addAttribute("request", new AdminUpdateRegisterRequest());

        return "admin/update/notice/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute AdminUpdateRegisterRequest request,
            RedirectAttributes redirectAttributes
    ){
        AdminUpdateRegisterResponse response =
                adminUpdateNoticeService.register(request);

        redirectAttributes.addFlashAttribute("id", response.getId());

        return "redirect:/admin/update/notices";
    }

    @PostMapping("/image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam MultipartFile image) {
        FileSaveResult url = fileService.saveFile(image, FileCategory.UPDATE_NOTICE_EDITOR);

        return Map.of(
                "url", url,
                "alt", "image"
        );
    }

    @GetMapping
    public String list(PageRequestDto req, Model model){

        PageResponseDto<AdminUpdateNoticeListResponse> response =
                adminUpdateNoticeService.getNoticeList(req);

        model.addAttribute("response", response);

        return "admin/update/notice/list";
    }
}
