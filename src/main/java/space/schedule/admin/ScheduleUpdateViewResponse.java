package space.schedule.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleUpdateViewResponse {

    private Long scheduleId;

    private ScheduleTargetType targetType;

    private String groupCode;
    private List<Long> memberIds;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String place;
    private String content;
    private String memo;
}
