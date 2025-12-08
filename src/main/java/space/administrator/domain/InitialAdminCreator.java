package space.administrator.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class InitialAdminCreator {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private InitialAdminProperties initialAdminProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createInitialAdmin() {
        // 최초로 관리자 계정이 하나도 없을 때만 생성
        if (administratorRepository.count() == 0) {
            Administrator admin = new Administrator();
            admin.setEmail(initialAdminProperties.getEmail());
            admin.setHashedPassword(passwordEncoder.encode(initialAdminProperties.getPassword()));
            administratorRepository.save(admin);
        }
    }
}