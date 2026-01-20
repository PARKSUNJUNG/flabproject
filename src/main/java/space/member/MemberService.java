package space.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.user.RegisterUserRequest;
import space.user.RegisterUserResponse;
import space.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Member> findAllActive(){
        return memberRepository.findByActiveTrue();
    }

    public String findNameById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getName)   // 실제 필드명에 맞게
                .orElse("(알 수 없음)");
    }

    public boolean emailExists(String email){
        return memberRepository.existsByEmail(email);
    }

    public RegisterMemberResponse register(RegisterMemberRequest req){

        if(memberRepository.existsByEmail(req.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        String encryptedPassword = passwordEncoder.encode(req.getPassword());

        // 엔티티 생성
        Member member = req.toEntity(encryptedPassword);
        Member saved = memberRepository.save(member);

        return RegisterMemberResponse.from(saved);
    }
}
