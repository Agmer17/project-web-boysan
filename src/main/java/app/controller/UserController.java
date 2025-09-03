package app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.model.dto.UpdateUserProfileRequest;
import app.model.entity.UserProfileData;
import app.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/my-profile")
    public String getMyProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Claims claims = (Claims) session.getAttribute("claims");
        String username = claims.get("username", String.class);

        UserProfileData user = userService.getCurrentUserData(username);

        model.addAttribute("user", user);
        return "UserProfile";
    }

    @PostMapping("/me/update")
    public String updateMyProfile(@Valid @ModelAttribute UpdateUserProfileRequest update, HttpServletRequest request,
            RedirectAttributes redirectAttributes)
            throws IOException {
        UserProfileData newData = userService.updateUserData(update, request);

        redirectAttributes.addFlashAttribute("user", newData);
        return "redirect:/user/my-profile";
    }

}
