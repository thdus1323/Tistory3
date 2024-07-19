package site.metacoding.blogv3;

import lombok.Data;

public class Response {

    @Data
    public static class SuccessResponse<T> {
        private String message;
        private T data;
    }


}
