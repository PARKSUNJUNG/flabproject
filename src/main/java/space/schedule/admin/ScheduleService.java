package space.schedule.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.member.MemberService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberService memberService;

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

    public ScheduleCalendarResponse getMonthlyCalendar(int year, int month){

        // year/month 정규화
        YearMonth yearMonth = YearMonth.of(year, 1).plusMonths(month - 1);

        int fixedYear = yearMonth.getYear();
        int fixedMonth = yearMonth.getMonthValue();

        // 월의 시작 / 끝
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();

        LocalDateTime start = firstDay.atStartOfDay();
        LocalDateTime end = lastDay.atTime(LocalTime.MAX);

        // 해당 월 스케줄 조회
        List<Schedule> schedules = scheduleRepository.findByStartDateTimeBetween(start, end);

        // 날짜별 그룹핑
        Map<LocalDate, List<Schedule>> groupByDate =
                schedules.stream()
                        .collect(Collectors.groupingBy(s -> s.getStartDateTime().toLocalDate()));

        // 날짜별 TargetSummary 생성
        List<ScheduleCalendarResponse.DayScheduleSummary> days =
                groupByDate.entrySet().stream()
                        .map(entry->{
                            LocalDate date = entry.getKey();
                            List<Schedule> daySchedules = entry.getValue();

                            List<ScheduleCalendarResponse.TargetSummary> targets =
                                    extractTargetSummaries(daySchedules);

                            return new ScheduleCalendarResponse.DayScheduleSummary(date, targets);
                        })
                        .sorted(Comparator.comparing(
                                ScheduleCalendarResponse.DayScheduleSummary::getDate
                        ))
                        .toList();

        return new ScheduleCalendarResponse(year, month, days);
    }

    // 날짜 안의 스케줄들에서 "대상 조합"만 추출
    private List<ScheduleCalendarResponse.TargetSummary> extractTargetSummaries(
            List<Schedule> schedules
    ) {
        return schedules.stream()
                .map(this::toTargetSummary)
                .distinct()
                .toList();
    }

    private ScheduleCalendarResponse.TargetSummary toTargetSummary(
            Schedule schedule
    ) {
        if (schedule.getTargetType() == ScheduleTargetType.GROUP) {
            String code = schedule.getGroupCode();
            return new ScheduleCalendarResponse.TargetSummary(
                    code,
                    "[" + code + "]"
            );
        }

        // MEMBER
        List<String> names = schedule.getScheduleMembers().stream()
                .map(sm -> memberService.findNameById(sm.getMemberId()))
                .sorted()
                .toList();

        String key = String.join(",", names);
        String label = "[" + key + "]";

        return new ScheduleCalendarResponse.TargetSummary(key, label);
    }

    private String buildTargetsLabel(Schedule schedule){
        // group 일정
        if(schedule.getTargetType() == ScheduleTargetType.GROUP){
            return "["+schedule.getGroupCode()+"]";
        }

        // member 일정
        List<String> names = schedule.getScheduleMembers().stream()
                .map(sm -> memberService.findNameById(sm.getMemberId()))
                .sorted()
                .toList();

        return "[" + String.join(", ", names) + "]";
    }

    public ScheduleDayResponse getDaySchedule(LocalDate date){
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Schedule> schedules = scheduleRepository.findByStartDateTimeBetween(start, end);

        List<ScheduleDayResponse.ScheduleDetail> details =
                schedules.stream()
                        .map(this::toDetail)
                        .toList();

        return new ScheduleDayResponse(date, details);
    }

    private ScheduleDayResponse.ScheduleDetail toDetail(Schedule s){

        String targets = buildTargetsLabel(s);

        return new ScheduleDayResponse.ScheduleDetail(
                s.getId(),
                targets,
                s.getContent(),
                s.getStartDateTime().toLocalTime().toString(),
                s.getEndDateTime().toLocalTime().toString(),
                s.getPlace(),
                s.getMemo()
        );
    }

    @Transactional(readOnly = true)
    public ScheduleUpdateViewResponse getUpdateView(Long scheduleId){

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("스케줄 없음"));

        List<Long> memberIds = schedule.getTargetType() == ScheduleTargetType.MEMBER
                ? schedule.getScheduleMembers().stream()
                    .map(ScheduleMember::getMemberId)
                    .toList()
                : List.of();

        return new ScheduleUpdateViewResponse(
                schedule.getId(),
                schedule.getTargetType(),
                schedule.getGroupCode(),
                memberIds,
                schedule.getStartDateTime(),
                schedule.getEndDateTime(),
                schedule.getPlace(),
                schedule.getContent(),
                schedule.getMemo()
        );
    }

    public ScheduleUpdateResponse update(ScheduleUpdateRequest request){

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(()-> new IllegalArgumentException("스케줄 없음"));

        schedule.update(
                request.getStartDateTime(),
                request.getEndDateTime(),
                request.getPlace(),
                request.getContent(),
                request.getMemo()
        );

        if(request.getTarget() != null){
            schedule.changeTarget(request.getTarget());
        }

        return new ScheduleUpdateResponse(schedule.getId());
    }

    public void delete(Long scheduleId){

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new IllegalArgumentException("스케줄 없음"));

        scheduleRepository.delete(schedule);
    }
}
