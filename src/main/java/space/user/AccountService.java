package space.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean emailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public RegisterUserResponse register(RegisterUserRequest req){

        if(userRepository.existsByEmail(req.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        String encryptedPassword = passwordEncoder.encode(req.getPassword());

        // 엔티티 생성
        User user = req.toEntity(encryptedPassword);
        User saved = userRepository.save(user);

        return RegisterUserResponse.from(saved);
    }
}
