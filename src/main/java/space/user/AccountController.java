package space.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    /**
     * 회원 가입
     */
    @GetMapping("/register")
    public String registerPage(){
        return "user/account";
    }

    @GetMapping("/checkemail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam String email){
        boolean exists = accountService.emailExists(email);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.");
        return response;
    }

    @PostMapping("/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse register(@RequestBody RegisterUserRequest req){
        return accountService.register(req);
    }

    @GetMapping("/edit")
    public String editPage(
            @AuthenticationPrincipal UserPrincipal userDetails,
            Model model
    ) {
        String email = userDetails.getEmail();
        User user = accountService.getUserByEmail(email);

        model.addAttribute("user", user);

        return "user/my/accountEdit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody EditUserRequest request
    ){
        accountService.edit(userDetails.getId(), request);

        boolean emailChanged = StringUtils.hasText(request.getEmail()) && !request.getEmail().equals(userDetails.getEmail());
        boolean passwordChanged = StringUtils.hasText(request.getPassword());

        if(emailChanged || passwordChanged){
            User updatedUser = accountService.getUserByEmail(request.getEmail());

            UserPrincipal newPrincipal = new UserPrincipal(
                    updatedUser.getId(),
                    updatedUser.getEmail(),
                    updatedUser.getPassword(),
                    Role.USER
            );

            Authentication newAuth =
                    new UsernamePasswordAuthenticationToken(
                            newPrincipal, // 사용자 정보
                            null, // 비밀번호 (로그인 이후엔 보통 null)
                            newPrincipal.getAuthorities() // 권한 목록
                    );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("message", "회원 정보가 수정되었습니다.");

        return result;
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

}
