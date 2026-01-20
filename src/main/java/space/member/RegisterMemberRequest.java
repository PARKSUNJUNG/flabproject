package space.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterMemberRequest {

    private String email;
    private String password;
    private String name;
    private boolean active;

    public Member toEntity(String encrypedPassword){
        return Member.builder()
                .email(this.email)
                .password(encrypedPassword)
                .name(this.name)
                .active(this.active)
                .build();
    }
}
