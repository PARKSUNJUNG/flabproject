package space.member;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterMemberResponse {

    private Long userId;
    private String email;
    private LocalDateTime createdAt;

    public static RegisterMemberResponse from(Member member){
        RegisterMemberResponse res = new RegisterMemberResponse();
        res.setUserId(member.getId());
        res.setEmail(member.getEmail());
        return res;
    }
}
