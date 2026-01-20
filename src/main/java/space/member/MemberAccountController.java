package space.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import space.user.RegisterUserRequest;
import space.user.RegisterUserResponse;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberAccountController {

    private final MemberService memberService;

    /**
     * 회원 가입
     */
    @GetMapping("/register")
    public String registerPage(){ return "admin/member/account"; }

    @GetMapping("/checkemail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam String email){
        boolean exists = memberService.emailExists(email);

        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.");
        return response;
    }

    @PostMapping("/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterMemberResponse register(@RequestBody RegisterMemberRequest req){
        return memberService.register(req);
    }

}
