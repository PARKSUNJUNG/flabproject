package space.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ScheduleMember {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    private Long memberId;

    public ScheduleMember(Schedule schedule, Long memberId) {
        this.schedule = schedule;
        this.memberId = memberId;
    }
}
