package DevBackEnd.ForLong.Filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class CustomOAuth2FailuerHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if (response.isCommitted()) {
            log.warn("OAuth2 실패 핸들러 실행 중단 - 응답이 이미 처리됨");
            return;
        }

        String redirectUri = request.getParameter("redirect_uri");
        log.info("Received redirect_uri: {}", redirectUri);
        log.info("OAuth2 error handler - full request URL: {}", request.getRequestURL());

        String errorMessage;
        int statusCode;

        if (exception instanceof OAuth2AuthenticationException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED; // 401 Unauthorized
            errorMessage = "OAuth2 authentication failed: " + exception.getMessage();
        } else {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500 Internal Server Error
            errorMessage = "Unexpected authentication failure: " + exception.getMessage();
        }

        log.info("OAuth2 실패 응답 - 상태 코드: {}, 메시지: {}", statusCode, errorMessage);

        response.sendError(statusCode, errorMessage);
    }
}
