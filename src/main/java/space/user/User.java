package space.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

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
    public User(String email, String password, String nickname, String gender, String phone, String address, String addressDetail, LocalDateTime createAt) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.addressDetail = addressDetail;
        this.createAt = createAt;
    }
}
