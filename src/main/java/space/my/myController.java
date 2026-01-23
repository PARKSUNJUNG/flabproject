package space.my;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class myController {

    @GetMapping("/list")
    public String myPage() { return "user/my/list"; }
}
