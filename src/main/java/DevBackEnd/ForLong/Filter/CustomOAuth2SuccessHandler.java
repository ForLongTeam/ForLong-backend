package DevBackEnd.ForLong.Filter;

import DevBackEnd.ForLong.Dto.CustomUserDetail;
import DevBackEnd.ForLong.Entity.RefreshToken;
import DevBackEnd.ForLong.Repository.RefreshRepository;
import DevBackEnd.ForLong.Util.CookieUtil;
import DevBackEnd.ForLong.Util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Authentication successful.\"}");
    }


}
