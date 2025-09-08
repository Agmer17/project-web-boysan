package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    private JwtUtils jwtUtil;

    @GetMapping("/")
    public String rootPath() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {

        return "Home";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "Login";
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "Signin";
    }

    @GetMapping("/live-chat")
    public String getChatPage(HttpServletRequest request, Model model, HttpSession session) {
        String username = jwtUtil.getUsernameFromCookie(request);

        model.addAttribute("username", username);
        return "ChatPage";
    }

    @GetMapping("/admin/")
    public String getAdminPage() {
        return "AdminProduct";
    }

}
