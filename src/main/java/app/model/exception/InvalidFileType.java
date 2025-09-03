package app.model.exception;

public class InvalidFileType extends RuntimeException {
    private String message;

    public InvalidFileType(String message) {
        super(message);
    }
}
