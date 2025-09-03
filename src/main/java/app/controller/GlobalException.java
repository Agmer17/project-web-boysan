package app.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.model.exception.AuthCredentialsException;
import app.model.exception.PostsDataNotValidException;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(PostsDataNotValidException.class)
    public String handleValidationExceptions(PostsDataNotValidException ex, RedirectAttributes redirectAttributes) {

        System.out.println(">>> GlobalException KE-TRIGGER"); // cek log
        if (ex.getResult() != null) {
            Map<String, String> errors = ex.getResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (msg1, _) -> msg1));
            redirectAttributes.addFlashAttribute("validationErrors", errors);

        }

        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        if (ex.getPage() != null) {
            return "redirect:/" + ex.getPage();
        } else {
            return "redirect:/home"; // nanti benerin ini
        }
    }

    @ExceptionHandler(AuthCredentialsException.class)
    public String authDataCredentialsHandler(AuthCredentialsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());

        if (!ex.getPage().isEmpty()) {
            return "redirect:/" + ex.getPage();
        }

        return "redirect:/login";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String duplicateKeyHandler(DuplicateKeyException ex, RedirectAttributes redirect) {
        redirect.addFlashAttribute("errorMessage", ex.getMessage());

        return "redirect:/sign-in";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String emptyResultHandler(EmptyResultDataAccessException e) {
        return "redirect:/NotFound";
    }
}
