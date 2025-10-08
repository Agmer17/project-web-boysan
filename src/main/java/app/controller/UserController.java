package app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import app.model.dto.UpdateUserProfileRequest;
import app.model.exception.PostsDataNotValidException;
import app.model.pojo.UserProfileData;
import app.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/my-profile")
    public String getMyProfile(Model model, HttpServletRequest request, @SessionAttribute Claims claims) {

        String username = claims.get("username", String.class);

        UserProfileData user = userService.getCurrentUserData(username);

        model.addAttribute("user", user);
        return "UserProfile";
    }

    @PostMapping("/me/update")
    public String updateMyProfile(@Valid @ModelAttribute UpdateUserProfileRequest update,
            BindingResult bindingResult, @SessionAttribute Claims claims)
            throws IOException {

        System.out.println(update);

        if (bindingResult.hasErrors()) {

            throw new PostsDataNotValidException(bindingResult, "user/my-profile", "data yang kamu masukkan gak valid",
                    update);
        }

        userService.updateUserData(update, claims);
        return "redirect:/user/my-profile";
        
    }

}
