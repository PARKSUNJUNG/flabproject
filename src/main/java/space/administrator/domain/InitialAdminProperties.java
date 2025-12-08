package space.administrator.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "initial-admin")
@Getter @Setter
public class InitialAdminProperties {
    private String email;
    private String password;
}