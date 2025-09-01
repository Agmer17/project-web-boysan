package app.model.exception;

import org.springframework.validation.BindingResult;

public class PostsDataNotValidException extends RuntimeException {

    private final BindingResult result;
    private final String page;

    public PostsDataNotValidException(String message) {
        super(message);
        this.result = null;
        this.page = null;
    }

    public PostsDataNotValidException(BindingResult validationResult, String fromPage, String message) {
        this.result = validationResult;
        this.page = fromPage;
    }

    public BindingResult getResult() {
        return this.result;
    }

    public String getPage() {
        return this.page;
    }
}