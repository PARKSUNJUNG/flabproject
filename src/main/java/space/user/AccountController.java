package space.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

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
}
