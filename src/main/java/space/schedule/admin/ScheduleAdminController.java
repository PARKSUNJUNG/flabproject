package space.schedule.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import space.member.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/schedules")
public class ScheduleAdminController {

    private final ScheduleService scheduleService;
    private final MemberService memberService;

    /* 등록 화면 */
    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("scheduleForm", new ScheduleRegisterRequest());
        model.addAttribute("members", memberService.findAllActive());

        return "admin/schedule/register";
    }

    /* 등록 처리 */
    @PostMapping("/register")
    public String register(
            @ModelAttribute("scheduleForm") ScheduleRegisterRequest request,
            RedirectAttributes redirectAttributes
    ){

        List<Long> ids = scheduleService.register(request);
        redirectAttributes.addFlashAttribute("message", ids.size()+"개의 스케줄이 등록되었습니다.");

        // 날짜 기준으로 다시 리스트로
        //String date = request.getStartDateTime().toLocalDate().toString();
        //return "redirect:/admin/schedules?date=" + date;
        return "redirect:/admin/schedules";
    }

    /* 조회 */
    @GetMapping
    public String scheduleList() {
        return "admin/schedule/list";
    }
}
