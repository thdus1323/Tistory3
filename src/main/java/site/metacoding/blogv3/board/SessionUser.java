package site.metacoding.blogv3.board;

import lombok.Builder;
import lombok.Data;
import site.metacoding.blogv3.user.User;

@Data
public class SessionUser {
    private Integer userId;

    @Builder
    public SessionUser(Integer userId) {
        this.userId = userId;
    }

    public SessionUser(User user) {
        this.userId = user.getUserId();
    }

}
