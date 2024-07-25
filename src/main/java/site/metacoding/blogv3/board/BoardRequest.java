package site.metacoding.blogv3.board;

import lombok.Data;
import site.metacoding.blogv3.category.Category;

@Data
public class BoardRequest {

    @Data
    public static class WriteDTO {
        private String title;
        private String content;
        private Integer categoryId;
        private Integer userId;
        private String categoryName;

    }

    @Data
    public static class UpdateDTO {
        private String boardTitle;
        private String boardContent;
    }
}
