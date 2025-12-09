package space.administrator.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping({"", "/"})
    public String adminMain(){
         return "admin/main";
    }
}
