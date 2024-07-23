package site.metacoding.blogv3.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    //글쓰기

    //유저아이디로 조회_게시글
    List<Board> findByUser_UserId(Integer userId);

}
