package space.schedule.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ScheduleRegisterRequest {

    private List<ScheduleTargetDto> targets = new ArrayList<>();

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String place;
    private String content;
    private String memo;
}
