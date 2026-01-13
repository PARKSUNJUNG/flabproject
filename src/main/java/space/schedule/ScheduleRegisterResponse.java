package space.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleRegisterResponse {

    private List<Long> scheduleIds;
}
