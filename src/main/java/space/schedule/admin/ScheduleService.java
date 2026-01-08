package space.schedule.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<Long> register(ScheduleRegisterRequest request){

        validateRequest(request);

        List<Long> resultIds = new ArrayList<>();

        for(ScheduleTargetDto target : request.getTargets()){

            Schedule schedule;

            if(target.getType() == ScheduleTargetType.GROUP){
                schedule = Schedule.createForGroup(
                        target.getGroupCode(),
                        request.getStartDateTime(),
                        request.getEndDateTime(),
                        request.getPlace(),
                        request.getContent(),
                        request.getMemo()
                );
            } else {
                schedule = Schedule.createForMember(
                        target.getMemberIds(),
                        request.getStartDateTime(),
                        request.getEndDateTime(),
                        request.getPlace(),
                        request.getContent(),
                        request.getMemo()
                );
            }

            scheduleRepository.save(schedule);
            resultIds.add(schedule.getId());
        }

        return resultIds;
    }

    private void validateRequest(ScheduleRegisterRequest request){

        if(request.getTargets() == null || request.getTargets().isEmpty()){
            throw new IllegalArgumentException("등록 대상이 없습니다.");
        }

        for(ScheduleTargetDto target : request.getTargets()){
            if(target.getType() == ScheduleTargetType.GROUP &&
                target.getGroupCode() == null){
                throw new IllegalArgumentException("Group 타입에는  groupCode가 필요합니다.");
            }

            if(target.getType() == ScheduleTargetType.MEMBER &&
                (target.getMemberIds() == null || target.getMemberIds().isEmpty())){
                throw new IllegalArgumentException("MEMBER 타입에는 memberIds가 필요합니다.");
            }
        }
    }
}
