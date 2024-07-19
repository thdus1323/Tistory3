package site.metacoding.blogv3.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.metacoding.blogv3.category.Category;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Data
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer userId;

    @Column(unique = true, length = 20, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String comfirmUserPassword;

    @Column(nullable = false)
    private String userEmail;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Category> categories;

    @Builder

    public User(Integer userId, String userName, String userPassword, String comfirmUserPassword, String userEmail, LocalDateTime createdAt, List<Category> categories) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.comfirmUserPassword = comfirmUserPassword;
        this.userEmail = userEmail;
        this.createdAt = createdAt;
        this.categories = categories;
    }
}
