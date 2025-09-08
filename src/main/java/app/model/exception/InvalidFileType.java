package app.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidFileType extends RuntimeException {

    private String message;
    private String page;
    private String field;

    public InvalidFileType(String message, String page, String field) {
        this.message = message;
        this.page = page;
        this.field = field;
    }
}
