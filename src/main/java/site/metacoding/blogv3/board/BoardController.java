package site.metacoding.blogv3.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.metacoding.blogv3.category.Category;
import site.metacoding.blogv3.category.CategoryService;
import site.metacoding.blogv3.user.User;

import java.util.List;
import java.util.Map;

@Slf4j // 로그에 주석 남기는 것.
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardService boardService;
    private final CategoryService categoryService;

    //글쓰기 폼
    @GetMapping("/s/post/write-form")
    public String poWriteForm(Model model) {
        //뷰에서 뿌리기 위해 model 객체를 씀
        User user = (User) session.getAttribute("sessionUser");
        //user 객체에 sessionUser라는 이름을 가진 세션을 저장.
        if (user == null){
            return "redirect:/login-form";
            //user 가 null이면, 로그인 코너로 가게!
        }
        List<Category> categories = categoryService.getCategories();
        // 카테고리s에 카테고리를 찾아와서 저장
        model.addAttribute("categories", categories);
        //카테고리 이름의 카테고리를 model(뷰에 뿌릴 거에)에 추가.
        return "/post/writeForm";
    } //글쓰기 폼

    //글쓰기 기능
    @PostMapping("/s/post")
    public String write(BoardRequest.WriteDTO reqDTO, HttpServletRequest request){
        //사용자 요청(글쓰기)랑 dto(글쓴 정보)를 넣으면,
        User user = (User) session.getAttribute("sessionUser");
        //유저 객체에 sessionUser라고 이름 적힌 세션을 저장.
        if (reqDTO.getCategoryId() == null){
            throw new IllegalStateException("카테고리 id는 null일 수 없어요");
        } // 만약, 카테고리가 없으면, exception 터뜨려줘.(카테고리는 꼭 있어야 해!)

        //reqDTO에 userId를 설정
        reqDTO.setUserId(user.getUserId());

        boardService.write(user, reqDTO);
        //유저랑, dto를 서비스에 보내고.
//        System.out.println("reqDTO111 = " + reqDTO);
        // 디티오에 뭐 담겼는지 볼까?
        HttpSession session = request.getSession();
        //httpsession에 우리가 요청하는 세션을 저장하자.
        session.setAttribute("categoryName", reqDTO.getCategoryName());
        //어떤 요청이냐면, categoryName을 세션에 저장하는(다른 페이지에서도 쓸려면 세션에 저장)
        return "redirect:/user/"+user.getUserId() + "/post";
    }

    //목록보기
    @GetMapping("/user/{userId}/post")
    public String list(@PathVariable Integer userId, Model model) {
        List<Board> boards = boardService.findByUserId(userId);
        model.addAttribute("boards", boards);
        return "/post/list";
    }

    //상세보기
    @GetMapping("/s/user/{boardId}")
    public String detail(@PathVariable Integer boardId, Model model) {
        Board board = boardService.findByBoardId(boardId);
        User currentUser = (User) session.getAttribute("sessionUser");

        if (currentUser == null){
            return "redirect:/login-form";
        }

        Integer currentUserId = currentUser.getUserId();
        Integer pageOwnerId = board.getUser().getUserId();

        model.addAttribute("board", board);
        model.addAttribute("boardId", boardId);
        model.addAttribute("pageOwnerId", pageOwnerId);
        model.addAttribute("currentUserId", currentUserId);
        return "/post/detail";
    }

    //게시판 글쓰기 수정 메서드

    @PostMapping("/s/update/{boardId}")
   public ResponseEntity<?> updateBoard(@PathVariable("boardId") Integer boardId,@RequestBody BoardRequest.UpdateDTO reqDTO) {
        User currentUser = (User) session.getAttribute("sessionUser");

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
        }

        Board board = boardService.findByBoardId(boardId);
        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없음");
        }
        if(!board.getUser().getUserId().equals(currentUser.getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음.");
        }

        System.out.println("reqDTO = " + reqDTO);

        board.setBoardTitle(reqDTO.getBoardTitle());
        board.setBoardContent(reqDTO.getBoardContent());
        boardService.updateBoard(board);

        return ResponseEntity.ok("게시글 수정 성공");




//        return "redirect:/user/" + reqDTO.getUserId() + "/post";
    }


    //게시판 수정 폼
    @GetMapping("/s/post/update-form/{boardId}")
    public String updateForm(@PathVariable("boardId") Integer boardId, Model model) {
        User currentUser = (User) session.getAttribute("sessionUser");

        if (currentUser == null) {
            return "redirect:login-form";
        }

        Board board = boardService.findByBoardId(boardId);

//        if (!board.getUser().getUserId().equals(currentUser.getUserId())){
//            return "redirect:/s/user/" + boardId;
//        }
//        model.addAttribute("board", board);
//        model.addAttribute("username", currentUser.getUserName());

        model.addAttribute("board", board);
        model.addAttribute("username", currentUser.getUserName());
        model.addAttribute("currentUserId", currentUser.getUserId());
        model.addAttribute("isOwner", board.getUser().getUserId().equals(currentUser.getUserId()));

        System.out.println("board = " + board);
        return "/post/updateForm";
    }

    //삭제하기
    @DeleteMapping("/s/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Integer boardId, @RequestParam Integer userId){
        User currentUser = (User) session.getAttribute("sessionUser");

        if (currentUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
        }

        Board board = boardService.findByBoardId(boardId);
        if (board == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없음");
        }
        if (!board.getUser().getUserId().equals(currentUser.getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한 없음");
        }

        boardService.deleteBoard(boardId);

        return ResponseEntity.ok("게시글 삭제 성공");
    }

}
