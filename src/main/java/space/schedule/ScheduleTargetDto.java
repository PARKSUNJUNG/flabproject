package space.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ScheduleTargetDto {

    private ScheduleTargetType type;

    private String groupCode;
    private List<Long> memberIds = new ArrayList<>();
}
