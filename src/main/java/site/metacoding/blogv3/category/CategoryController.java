package site.metacoding.blogv3.category;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.metacoding.blogv3.user.User;
import site.metacoding.blogv3.user.UserService;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/s/category/writeForm")
    public String caWriteForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public String getCategory(@RequestParam String categoryName){
        User user = (User) session.getAttribute("sessionUser");
        if(user == null){
           return "redirect:/login-form";
        }
//        System.out.println("user = " + user);

        //category 객체 생성 및 속성 지정
        Category category = new Category();
        category.setCategoryName(categoryName);

        //카테고리 저장
        category.setUser(user);
        categoryService.save(category);
        return "redirect:/s/post/write-form";
    }



}
