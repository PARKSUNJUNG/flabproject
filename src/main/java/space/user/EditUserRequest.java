package space.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditUserRequest {

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String address;
    private String addressDetail;

}
