package app.middleware;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Claims userClaims = (Claims) request.getSession(false).getAttribute("claims");

        String currentUserRole = userClaims.get("role", String.class);

        if (!currentUserRole.equals("admin")) {
            response.sendRedirect("/home");
            return false;
        }
        return true;
    }

}
