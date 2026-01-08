package space.schedule.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Schedule {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ScheduleTargetType targetType;

    private String groupCode;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String place;
    private String content;
    private String memo;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleMember> scheduleMembers = new ArrayList<>();

    public static Schedule createForGroup(
            String groupCode,
            LocalDateTime start,
            LocalDateTime end,
            String place,
            String content,
            String memo
    ){
        Schedule s = new Schedule();
        s.targetType = ScheduleTargetType.GROUP;
        s.groupCode = groupCode;
        s.startDateTime = start;
        s.endDateTime = end;
        s.place = place;
        s.content = content;
        s.memo = memo;
        return s;
    }

    public static Schedule createForMember(
            List<Long> memberIds,
            LocalDateTime start,
            LocalDateTime end,
            String place,
            String content,
            String memo
    ){
        Schedule s = new Schedule();
        s.targetType = ScheduleTargetType.MEMBER;
        s.startDateTime = start;
        s.endDateTime = end;
        s.place = place;
        s.content = content;
        s.memo = memo;

        for(Long memberId : memberIds) {
            s.scheduleMembers.add(new ScheduleMember(s, memberId));
        }
        return s;
    }
}
