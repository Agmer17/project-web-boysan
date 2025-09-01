package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import app.model.dto.LoginRequest;
import app.model.dto.SignInRequest;
import app.model.exception.PostsDataNotValidException;
import app.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService svc;

    @PostMapping("/login")
    public String LoginPostsRequest(
            @Valid @ModelAttribute("user") LoginRequest request,
            BindingResult result,
            Model model,
            HttpServletResponse responseObj) {

        if (result.hasErrors()) {
            throw new PostsDataNotValidException(result, "login",
                    "harap lengkapi dan isi dengan benar semua data login");
        }

        svc.LoginService(request.getUsername(), request.getPassword(), responseObj);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie("Credentials", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/home";
    }

    @PostMapping("/sign-in")
    public String signInRequest(@Valid @ModelAttribute SignInRequest request, BindingResult result) {

        if (result.hasErrors()) {
            throw new PostsDataNotValidException(result, "sign-in", "data yang kamu masukkan gak valid!");
        }

        if (svc.signInServcie(request)) {
            return "redirect:/login";
        }

        return "redirect:/sign-in";
    }

}
