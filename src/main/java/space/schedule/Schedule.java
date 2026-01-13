package space.schedule;

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

    public void update(
            LocalDateTime start,
            LocalDateTime end,
            String place,
            String content,
            String memo
    ){
        this.startDateTime = start;
        this.endDateTime = end;
        this.place = place;
        this.content = content;
        this.memo = memo;
    }

    public void changeTarget(ScheduleTargetDto target) {
        this.scheduleMembers.clear(); // orphanRemoval

        if (target.getType() == ScheduleTargetType.GROUP) {
            this.targetType = ScheduleTargetType.GROUP;
            this.groupCode = target.getGroupCode();
        } else {
            this.targetType = ScheduleTargetType.MEMBER;
            this.groupCode = null;

            for (Long memberId : target.getMemberIds()) {
                this.scheduleMembers.add(new ScheduleMember(this, memberId));
            }
        }
    }

}
