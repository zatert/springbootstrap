package app.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Set set = (Set) userDetails.getAuthorities();
        String str = set.toString();
        if(str.equals("[ROLE_ADMIN]" )) {
            response.sendRedirect("/admin");
        }
        else if(str.equals("[ROLE_USER]" )) {
            response.sendRedirect("/user");
        }
        else {
            response.sendRedirect("/");
        }
    }
}