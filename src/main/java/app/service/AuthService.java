package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.model.dto.SignInRequest;
import app.model.exception.AuthCredentialsException;
import app.model.pojo.UserAuthDetails;
import app.repository.UserRepository;
import app.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    @Autowired
    private UserRepository repo;

    @Autowired
    JwtUtils jwtUtil;

    public boolean LoginService(String username, String password, HttpServletResponse responseObj) {

        try {
            UserAuthDetails details = repo.findDetailsUsername(username);

            if (!BCrypt.checkpw(password, details.getPasswordHash())) {
                throw new AuthCredentialsException("Password yang kamu masukkan salah!", "login");
            }

            responseObj.addCookie(this.generateCookie(details.getUsername(), details.getRole(), details.getId()));
            return true;

        } catch (EmptyResultDataAccessException e) {
            throw new AuthCredentialsException("akun tidak ditemukan", "login");
        }
    }

    public boolean signInServcie(SignInRequest request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String username = request.getUsername();
        String fullName = request.getFullName();
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        try {
            boolean isSuccess = repo.save(username, password, fullName, email);
            return isSuccess;
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("akun sudah terdaftar!");
        }
    }

    private Cookie generateCookie(String username, String role, int id) {
        Cookie cookie = new Cookie("Credentials", jwtUtil.generateToken(username, role, id));
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        return cookie;
    }

    // private void deleteCurrentLogin(HttpServletRequest request) {
    // }
}
