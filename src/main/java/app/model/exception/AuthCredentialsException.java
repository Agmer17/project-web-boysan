package app.model.exception;

public class AuthCredentialsException extends RuntimeException {

    private String page;

    public AuthCredentialsException(String msg) {
        super(msg);
    }

    public AuthCredentialsException(String msg, String page) {
        super(msg);
        this.page = page;
    }

    public String getPage() {
        return this.page;
    }

}
