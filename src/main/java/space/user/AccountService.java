package space.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public void edit(Long userId, EditUserRequest req){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.update(req);

        // 이메일 변경
        if(StringUtils.hasText(req.getEmail()) && !req.getEmail().equals(user.getEmail())){

            if(emailExists(req.getEmail())){
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }
            user.changeEmail(req.getEmail());
        }

        // 비밀번호 변경
        if(StringUtils.hasText(req.getPassword())){
            user.changePassword(passwordEncoder.encode(req.getPassword()));
        }
    }
}
