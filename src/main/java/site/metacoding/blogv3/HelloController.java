package site.metacoding.blogv3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.metacoding.blogv3.user.ResponseDTO;

@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public ResponseDTO<String> hello() {
        ResponseDTO<String> response = new ResponseDTO<>("성공이지", "안녕이다");
        return response;
    }
}
