package app.model.exception;

import org.springframework.validation.BindingResult;

public class PostsDataNotValidException extends RuntimeException {
    private Object formData;
    private final BindingResult result;
    private final String page;
    private final String message;

    public PostsDataNotValidException(String message) {
        this.message = message;
        this.result = null;
        this.page = null;
        this.formData = null;
    }

    public PostsDataNotValidException(BindingResult validationResult, String fromPage, String message,
            Object formData) {
        this.message = message;
        this.result = validationResult;
        this.page = fromPage;
        this.formData = formData;
    }

    public PostsDataNotValidException(String message, String fromPage) {
        this.message = message;
        this.result = null;
        this.page = fromPage;
        this.formData = null;
    }

    public BindingResult getResult() {
        return this.result;
    }

    public String getPage() {
        return this.page;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Object getFormData() {
        return this.formData;
    }
}