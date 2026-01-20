package space.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.member.Member;
import space.member.MemberRepository;

import java.util.Optional;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isPresent()){
            User user = userOpt.get();
            return new UserPrincipal(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    Role.USER
            );
        }

        Optional<Member> memberOpt = memberRepository.findByEmail(email);

        if(memberOpt.isPresent()){
            Member member = memberOpt.get();
            return new UserPrincipal(
                    member.getId(),
                    member.getEmail(),
                    member.getPassword(),
                    Role.MEMBER
            );
        }

        throw new UsernameNotFoundException("User or Member not found");
    }
}
