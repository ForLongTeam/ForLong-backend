package DevBackEnd.ForLong.Filter;

import DevBackEnd.ForLong.Dto.CustomUserDetail;
import DevBackEnd.ForLong.Entity.RefreshToken;
import DevBackEnd.ForLong.Repository.RefreshRepository;
import DevBackEnd.ForLong.Util.CookieUtil;
import DevBackEnd.ForLong.Util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    public static final long REFRESHMS = 60 * 60 * 1000L;

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public CustomOAuth2SuccessHandler(JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    /**
     * 서버에서 Access Token을 처음에 발급해서 클라이언트 개발자에게 응답 본문을 통해 제공
     * 이후 Access Token 기간이 만료되면 처음 로그인했을 때 제공받은 Refresh 토큰을 활용해 Access 토큰 재발급
     * */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공!");

        CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
        String loginId = userDetail.getUsername();
        String role = userDetail.getUser().getRole();

        String accessToken = jwtUtil.createJwt("access", loginId, role, REFRESHMS);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("message","Authentication successful.");
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     *  Access Token 재발급 흐름
     * Access Token 만료 시 클라이언트가 /auth/access-token API 호출:
     *
     * 클라이언트는 Refresh Token을 쿠키로 서버에 전송.
     * 서버는 Refresh Token의 유효성 검증 후, 새로운 Access Token 발급.
     * 재발급된 Access Token으로 API 요청 재시도:
     *
     * 클라이언트는 새로운 Access Token을 이용해 이전 요청을 다시 시도.
     * Refresh Token 만료 시:
     *
     * 서버가 401 Unauthorized 응답을 반환.
     * 클라이언트는 다시 로그인 페이지로 리디렉션
     * */
}
