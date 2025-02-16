package DevBackEnd.ForLong.features.login.Filter;

import DevBackEnd.ForLong.core.repository.RefreshRepository;
import DevBackEnd.ForLong.common.Util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CutomLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CutomLogoutFilter(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Override
    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain filterChain)
            throws IOException, ServletException {
//        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        HttpServletResponse response = (HttpServletResponse) httpResponse;

        /**
         * /logout 요청 낚아챔
         * */
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/api/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }
        String requestMethod = request.getMethod();

        /**
         * POST 요청 맊아챔.
         * */
        if (!requestMethod.equals("POST")) {  //logout 요청 + POST 요청만 낚아챔.
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 쿠키에서 Refresh 토큰 가져오기
         * */
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        refresh = getRefresh(cookies, refresh);

        /**
         * 쿠키가 null인 경우 400 오류 발생
         * */
        if (refresh == null) {
            getSetStatus(response);
            return;
        }

        //만료 체크
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            getSetStatus(response);
            throw e;
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            //response status code
            getSetStatus(response);
            return;
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            //response status code
            getSetStatus(response);
            return;
        }

        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        //Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }


    private void getSetStatus(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String getRefresh(Cookie[] cookies, String refresh) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }
        return refresh;
    }
}
