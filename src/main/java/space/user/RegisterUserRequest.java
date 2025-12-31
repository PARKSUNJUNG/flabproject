package space.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RegisterUserRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String gender;
    private String phone;
    private String address;
    private String addressDetail;

    public User toEntity(String encrypedPassword){
        return User.builder()
                .email(this.email)
                .password(encrypedPassword)
                .name(this.name)
                .nickname(this.nickname)
                .gender(this.gender)
                .phone(this.phone)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .createAt(LocalDateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return "RegisterUserRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                '}';
    }
}
