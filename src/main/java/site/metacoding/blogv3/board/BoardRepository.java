package site.metacoding.blogv3.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    //글쓰기

    //유저아이디로 조회_게시글
    List<Board> findByUser_UserId(Integer userId);

    @Query("UPDATE Board b SET b.boardTitle = :title, b.boardContent = :content WHERE b.boardId = :boardId")
    int updateBoard(@Param("boardId") Integer boardId, @Param("title")String title, @Param("content") String content);
}
