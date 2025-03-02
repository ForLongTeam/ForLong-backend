package DevBackEnd.ForLong.features.login.Filter;

import DevBackEnd.ForLong.common.utils.CookieUtil;

import DevBackEnd.ForLong.core.entity.RefreshToken;
import DevBackEnd.ForLong.core.repository.RefreshRepository;
import DevBackEnd.ForLong.features.login.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public static final long ACCESSMS = 60 * 60 * 1000L;
    public static final long REFRESHMS = 24 * 60 * 60 * 1000L;
    private final AuthenticationManager authenticationManager;
    private final DevBackEnd.ForLong.common.utils.JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshRepository refreshRepository;

    public LoginFilter(AuthenticationManager authenticationManager, DevBackEnd.ForLong.common.utils.JwtUtil jwtUtil, CookieUtil cookieUtil, RefreshRepository refreshRepository) {
        super.setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.refreshRepository = refreshRepository;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            // POST 요청이 아닌 경우 인증을 시도하지 않음
            return null;
        }

        LoginDTO loginDTO = new LoginDTO();

        try {
            InputStream inputStream = request.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginDTO = mapper.readValue(messageBody, LoginDTO.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String loginId = loginDTO.getLoginId();
        String password = loginDTO.getPassword();

        log.info("loginid = [{}], " +
                "password = [{}]", loginId, password);

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginId, password, null);
            log.info("authToken = [{}]", authToken);

            // AuthenticationManager에 인증 요청
            return authenticationManager.authenticate(authToken);
        } catch (Exception ex) {
            log.error("\"Error during authentication: \" + ex.getMessage()");
            throw ex; // 문제의 원인을 추적하기 위해 예외 재던짐
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successful authentication");

        String userId = authResult.getName();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        if (authorities.isEmpty()) {
            log.error("Granted Authorities are empty!");
            throw new RuntimeException("User has no granted authorities");
        }
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access",userId, role, ACCESSMS);
        String refresh = jwtUtil.createJwt("refresh",userId, role, REFRESHMS);
        log.info("로그인 성공");

        //Refresh 토큰 저장
        addRefreshEntity(userId, refresh, REFRESHMS);

        // 응답설정
        response.setHeader("access", access);
        response.addCookie(cookieUtil.createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());

        chain.doFilter(request, response);

    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.error("Authentication failed: " + failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed.");
    }

    private void addRefreshEntity(String userId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(String.valueOf(new Timestamp(date.getTime())));

        refreshRepository.save(refreshToken);
    }
}
