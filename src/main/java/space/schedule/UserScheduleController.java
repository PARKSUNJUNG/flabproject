package space.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class UserScheduleController {

    private final ScheduleService scheduleService;

    // 달력 화면
    @GetMapping
    public String SchedulePage(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ){
        LocalDate now = LocalDate.now();

        int targetYear = (year != null) ? year : now.getYear();
        int targetMonth = (month != null) ? month : now.getMonthValue();

        if(targetMonth < 1) {
            targetMonth = 12;
            targetYear--;
        } else if(targetMonth > 12){
            targetMonth = 1;
            targetYear++;
        }

        model.addAttribute("year", targetYear);
        model.addAttribute("month", targetMonth);
        model.addAttribute("today", now.toString());
        model.addAttribute("isAdmin", false);

        return "user/schedule/list";
    }
}
