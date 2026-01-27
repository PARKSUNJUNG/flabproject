package space.common;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.webmvc.error.ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status == null){
            status = 404; // 기본값
        }

        model.addAttribute("status", status);

        if(status == 403) return "error/403";
        if(status == 404) return "error/404";
        if(status == 500) return "error/500";

        return "error/default";
    }

}
