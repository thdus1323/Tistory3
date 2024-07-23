package site.metacoding.blogv3.board;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.metacoding.blogv3.category.Category;
import site.metacoding.blogv3.category.CategoryRepository;
import site.metacoding.blogv3.user.User;
import site.metacoding.blogv3.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    //글쓰기
    public void write(User sessionUser, BoardRequest.WriteDTO reqDTO){
        Category category = categoryRepository.findById(reqDTO.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("카테고리 못 찾겟음"));
        //카테고리 못 찾으면 문구 보내시오
        User user = userRepository.findById(sessionUser.getUserId())
                        .orElseThrow(() -> new RuntimeException("유저 못 찾겠음"));
        //유저 못 찾으면 문구 보내시오

        reqDTO.setCategoryName(category.getCategoryName());
        //dto에서 카테고리 name 가져오세요
        Board board = new Board();
        //보드객체 만들구요
        board.setBoardTitle(reqDTO.getTitle());
        //dto에서 제목 가져와서 보드에 저장
        board.setBoardContent(reqDTO.getContent());
        //dto에서 내용가져와서 보드에 저장
        board.setCategory(category);
        //카테고리를 보드에 넣어요.
        board.setUser(user);
        //user를 보드에 넣어요.
        boardRepository.save(board);
        //위의 정보들을 담은 보드를 repository에 저장해요.
    }

    //글 조회
    public List<Board> findByUserId(Integer userId) {
            return boardRepository.findByUser_UserId(userId);
    }
}
