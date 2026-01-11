package space.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAllActive(){
        return memberRepository.findByActiveTrue();
    }

    public String findNameById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getName)   // 실제 필드명에 맞게
                .orElse("(알 수 없음)");
    }
}
