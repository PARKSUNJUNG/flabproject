package space.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Qualifier("adminDetailsService")
    private UserDetailsService adminDetailsService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    // 관리자
    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/admin/login") // 로그인 처리 URL
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/admin", true)

                )
                .userDetailsService(adminDetailsService)
                .logout(logout-> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                );

        return http.build();
    }

    // 사용자
    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http
                //.securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/account/login")
                        .loginProcessingUrl("/account/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                )
                .userDetailsService(userDetailsService)
                .logout(logout-> logout
                        .logoutUrl("/account/logout")
                        .logoutSuccessUrl("/account/login")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/static/**",
                        "/favicon/**"
                );
    }

}
