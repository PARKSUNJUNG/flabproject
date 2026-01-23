package space.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 10)
    private String gender;

    @Column(length = 20)
    private String phone;

    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    private LocalDateTime createAt;

    @Builder
    public User(String email, String password, String name, String nickname, String gender, String phone, String address, String addressDetail, LocalDateTime createAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.addressDetail = addressDetail;
        this.createAt = createAt;
    }

    public void update(EditUserRequest req){
        if (StringUtils.hasText(req.getName())) this.name = req.getName();
        if (StringUtils.hasText(req.getNickname())) this.nickname = req.getNickname();
        if (StringUtils.hasText(req.getPhone())) this.phone = req.getPhone();
        if (StringUtils.hasText(req.getAddress())) this.address = req.getAddress();
        if (StringUtils.hasText(req.getAddressDetail())) this.addressDetail = req.getAddressDetail();
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
