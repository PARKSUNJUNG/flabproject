package space.schedule.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleRegisterResponse {

    private List<Long> scheduleIds;
}
