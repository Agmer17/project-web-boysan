package app.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentialsException extends RuntimeException {

    private String page;
    private String message;
    private String field;

    public AuthCredentialsException(String msg) {
        this.message = msg;
    }

    public AuthCredentialsException(String msg, String page, String field) {
        this.message = msg;
        this.page = page;
    }

}
