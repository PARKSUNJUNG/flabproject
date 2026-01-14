package space.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserScheduleDayResponse {

    private LocalDate date;
    private List<UserScheduleItem> schedules;

    @Getter
    @AllArgsConstructor
    public static class UserScheduleItem {

        private String targets;
        private String startTime;
        private String endTime;
        private String content;
    }
}
