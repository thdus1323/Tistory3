package site.metacoding.blogv3.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;


@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @GetMapping({"/","/post/1"})
    public String index() {
//        System.out.println("체크: index 실행됨");
        return "main";
    }

    // 파일응답(Controller, ViewResolover 발동), 데이터응답(ResponseBody, MessageConverter)
    // Query String 공부하기
    // 동일 한 유저네임이 있어? 하고 물어보는거니까? 조회용도!!
    // http://localhost:8080/usernameCheck?username=ssar
    @GetMapping("/usernameCheck") //usernameCheck get 메서드
    @ResponseBody
    public ResponseDTO<String>  usernameCheck(@RequestParam String username){
        // DB에 username이 ssar인 친구가 있어? -> service, repository

        System.out.println("username : "+username);
        boolean isExists = userService.isUsernameExists(username);
        if(isExists){
            return new ResponseDTO<>("username 중복됨", "사용불가능"); // no(username 중복안됨)
        } else {
            return new ResponseDTO<>("username 중복안됨", "사용가능"); //ok(username 중복됨.)
        }
    }

    //회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO reqDTO){
        try {
            userService.join(reqDTO);
//            System.out.println("reqDTO = " + reqDTO);
            return "/user/loginForm";
        } catch (RuntimeException e) {
            System.out.println("회원가입 오류: " + e.getMessage());
            return "redirect:/joinForm?error=" + e.getMessage(); // 에러 메시지를 포함하여 리다이렉트
        }
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    //로그인
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.login(reqDTO);
        session.setAttribute("sessionUser", sessionUser);
//        System.out.println("sessionUser = " + sessionUser);
        return "redirect:/";
    }

    @GetMapping("login-form")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:login-form";
    }

    @GetMapping("/s/user")
    public String updateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("인증되지 않았습니다");
        }
        return "/user/updateForm";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(UserRequest.ChangePasswordDTO reqDTO){
            System.out.println("updateReqDTO = " + reqDTO);
            //dto에 뭐가 담겼니?
            User user = (User) session.getAttribute("sessionUser");
            //세션에서 sessionUser 정보를 가져와서 sessionUser에 담아. =>왜? 머스태치에 뿌려야지!
            if(user == null){
                throw new RuntimeException("로그인이 필요합니다.");
                //세션 user 값이 없다면, 로그인 값이 없는 거니까 로그인하라고 말해야지.
            }else {
                //sessionUser 값이 있다면, 회원정보 변경해야지.
                userService.updatePassword(reqDTO, user);
                //비밀번호 dto담은 거라 서비스로 보내
                System.out.println("비밀번호 변경 성공!");
                return  "redirect:login-form";
            }
    }
}
