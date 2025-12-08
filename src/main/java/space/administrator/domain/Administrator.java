package space.administrator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@Getter
@Setter             // 필드 변경 필요 시 추가
@NoArgsConstructor
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email; // 로그인용 이메일

    @Column(nullable = false)
    private String hashedPassword; // 암호화된 비밀번호
}