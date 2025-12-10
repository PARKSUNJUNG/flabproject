package space.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RegisterUserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;

    public static RegisterUserResponse from(User user){
        RegisterUserResponse res = new RegisterUserResponse();
        res.setUserId(user.getId());
        res.setEmail(user.getEmail());
        res.setNickname(user.getNickname());
        res.setCreatedAt(user.getCreateAt());
        return res;
    }
}