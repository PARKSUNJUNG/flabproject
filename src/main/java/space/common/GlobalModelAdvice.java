package space.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute
    public void addAuthInfo(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인을 안했거나, 인증 객체가 없는 경우
        if (auth == null || !auth.isAuthenticated()) {
            model.addAttribute("isMember", false);
            return;
        }

        boolean isMember = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MEMBER"));

        model.addAttribute("isMember", isMember);
    }
}
