package space.administrator.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import space.user.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdminDetails implements UserDetails {

    private Administrator administrator;

    public AdminDetails(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ Role.ADMIN));
    }

    @Override
    public String getPassword(){
        return administrator.getHashedPassword();
    }

    @Override
    public String getUsername(){
        return administrator.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; } // 계정 만료되었는가
    @Override public boolean isAccountNonLocked() { return true; } // 계정이 잠겼는가
    @Override public boolean isCredentialsNonExpired() { return true; } // 비밀번호가 만료되었는가
    @Override public boolean isEnabled() { return true; } // 계정이 활성이 되어있는가

}
