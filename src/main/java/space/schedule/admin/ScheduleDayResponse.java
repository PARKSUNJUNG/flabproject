package space.schedule.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleDayResponse {

    private LocalDate date;
    private List<ScheduleDetail> schedules;

    @Getter
    @AllArgsConstructor
    public static class ScheduleDetail {

        private Long scheduleId;
        private String targets;
        private String content;
        private String startTime;
        private String endTime;
        private String place;
        private String memo;

    }
}
