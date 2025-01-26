package DevBackEnd.ForLong.Filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class CustomOAuth2FailuerHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String redirectUri = request.getParameter("redirect_uri");
        String errorMessage;
        int statusCode;

        if (redirectUri == null || redirectUri.isEmpty()) {
            log.info("400 오류 발생");
            errorMessage = "Missing or invalid redirect_url parameter";
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else{
            log.info("500 오류 발생");
            errorMessage ="OAuth2 authentication failed due to a server error.";
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(String.format("{\"error\": \"%s\", \"status\": %d}", errorMessage, statusCode));
    }
}
