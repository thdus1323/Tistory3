package site.metacoding.blogv3.board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import site.metacoding.blogv3.category.Category;
import site.metacoding.blogv3.user.User;

import java.time.LocalDateTime;

@Table(name = "board_tb")
@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"category", "user"})
public class Board {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer boardId;

    @Column(nullable = false,unique = true, length = 20)
    private String boardTitle;

    @Column(nullable = false)
    private String boardContent;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @CreationTimestamp
    private LocalDateTime createdAt;



}
