package space.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/api/schedules")
@RequiredArgsConstructor
public class ScheduleAdminApiController {

    private final ScheduleService scheduleService;

    /* 달력용 조회 */
    @GetMapping
    public ScheduleCalendarResponse getCalendar(
            @RequestParam int year,
            @RequestParam int month
    ){
        if (month < 1) {
            month = 12;
            year--;
        } else if (month > 12) {
            month = 1;
            year++;
        }

        return scheduleService.getMonthlyCalendar(year, month);
    }

    /* 일자 상세 조회 */
    @GetMapping("/day")
    public ScheduleDayResponse getDaySchedule(
            @RequestParam LocalDate date
    ){
        return scheduleService.getDaySchedule(date);
    }
}
