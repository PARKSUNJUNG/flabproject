package space.schedule.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleCalendarResponse {

    private int year;
    private int month;
    private List<DayScheduleSummary> days;

    @Getter
    @AllArgsConstructor
    public static class DayScheduleSummary {
        private LocalDate date;
        private List<TargetSummary> targets;
    }

    @Getter
    @AllArgsConstructor
    public static class TargetSummary {
        private String key;
        private String label;
    }
}
