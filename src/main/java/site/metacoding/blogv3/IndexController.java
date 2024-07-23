package site.metacoding.blogv3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {



    //category view----------------------------------------------



    //post view----------------------------------------------









    //user view----------------------------------------------




    @GetMapping("/logout")
    public String logout() {
        return "redirect:/user/loginForm";
    }


    @GetMapping("/user/password-reset-form")
    public String passwordResetForm() {
        return "/user/passwordResetForm";
    }













}
