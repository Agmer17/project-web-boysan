package app.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.model.exception.AuthCredentialsException;
import app.model.exception.PostsDataNotValidException;
import app.model.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalException {

    /*
     * method ini dipake buat handle error ketika upload sesuatu ke server
     * entah itu ukuran file, field yang gak sesuai constraint dll
     * mustache yang ada, harus ngikutin aturan ini :
     * 1. kasih {{#errorMessages}} yang bakal ngeluarin error ini
     * 2. kasih {{#validationErros.(fieldName)}} di setiap field yang throw error
     * ini
     */
    @ExceptionHandler(PostsDataNotValidException.class)
    public String postDataNotValidException(PostsDataNotValidException ex, RedirectAttributes redirectAttributes) {
        if (ex.getResult() != null) {
            Map<String, String> errors = ex.getResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (msg1, _) -> msg1));
            redirectAttributes.addFlashAttribute("validationErrors", errors);

        }

        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        System.out.println("Flash errorMessage = " + ex.getMessage());

        String redirectTo = "redirect:/" + ex.getPage();

        return redirectTo;
    }

    @ExceptionHandler(AuthCredentialsException.class)
    public String authDataCredentialsHandler(AuthCredentialsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/" + ex.getPage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundHandler(ResourceNotFoundException ex, RedirectAttributes redirectAttributes) {
        // redirectAttributes.addFlashAttribute(ex.getMessage());

        return "NotFound";
    }
}
