package space.schedule.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import space.member.MemberService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/schedules")
public class ScheduleAdminController {

    private final ScheduleService scheduleService;
    private final MemberService memberService;

    /* 등록 화면 */
    @GetMapping("/register")
    public String registerForm(
            @RequestParam(required = false) LocalDate date, Model model){

        LocalDate selectedDate = (date != null) ? date : LocalDate.now();

        ScheduleRegisterRequest form  = new ScheduleRegisterRequest();
        form.setStartDateTime(selectedDate.atTime(9, 0));
        form.setEndDateTime(selectedDate.atTime(10, 0));

        model.addAttribute("scheduleForm", form);
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

        return "redirect:/admin/schedules";
    }

    /* 조회 */
    @GetMapping
    public String schedulePage(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ) {
        LocalDate today = LocalDate.now();

        int targetYear = (year != null) ? year : today.getYear();
        int targetMonth = (month != null) ? month : today.getMonthValue();

        if(targetMonth < 1){
            targetMonth = 12;
            targetYear--;
        } else if(targetMonth > 12){
            targetMonth = 1;
            targetYear++;
        }

        model.addAttribute("year", targetYear);
        model.addAttribute("month", targetMonth);
        model.addAttribute("today", today.toString());

        return "admin/schedule/list";
    }

    /* 수정 화면 */
    @GetMapping("/{id}/edit")
    public String editForm(
            @PathVariable("id") Long scheduleId,
            Model model
    ){
        ScheduleUpdateViewResponse view = scheduleService.getUpdateView(scheduleId);

        model.addAttribute("schedule", view);
        model.addAttribute("members", memberService.findAllActive());

        return "admin/schedule/edit";
    }

    /* 수정 저장 */
    @PostMapping("/{id}")
    public String update(
            @PathVariable("id") Long scheduleId,
            @ModelAttribute ScheduleUpdateRequest request
    ){
        request.setScheduleId(scheduleId);

        scheduleService.update(request);

        return "redirect:/admin/schedules";
    }
}
