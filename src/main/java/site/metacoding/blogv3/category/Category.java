package site.metacoding.blogv3.category;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import site.metacoding.blogv3.board.Board;
import site.metacoding.blogv3.user.User;

import java.util.List;

@Entity
@Data
@Table(name = "category_tb")
@NoArgsConstructor
@ToString(exclude = {"user","boards"})
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer categoryId;

    @Column(unique = true, nullable = false)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false) //외래키 참조
    private User user;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Board> boards;

}
