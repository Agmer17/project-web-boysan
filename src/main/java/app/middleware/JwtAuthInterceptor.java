package app.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("claims") != null) {
            return true;
        }

        String cookie = null;

        if (request.getCookies() == null) {
            response.sendRedirect("/login");
            return false;
        }

        for (Cookie c : request.getCookies()) {
            if ("Credentials".equalsIgnoreCase(c.getName())) {
                cookie = c.getValue();
                System.out.println(c.getValue());
            }
        }

        if (cookie == null || cookie.isEmpty() || cookie.isBlank()) {
            response.sendRedirect("/login");
            response.setStatus(401);
            return false;
        }

        try {
            Claims claims = jwtUtil.parseToken(cookie);
            request.getSession(true).setAttribute("claims", claims);
            return true;
        } catch (ExpiredJwtException e) {
            response.sendRedirect("/login");
            return false;
        } catch (JwtException e) {
            response.sendRedirect("/login");
            return false;
        }

    }
}
