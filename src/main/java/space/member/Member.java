package space.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean active = true;

    private LocalDateTime createdAt;

    @Builder
    public Member(String name, boolean active, String email, String password) {
        this.name = name;
        this.active = active;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
