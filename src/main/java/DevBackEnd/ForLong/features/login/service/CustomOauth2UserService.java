package DevBackEnd.ForLong.features.login.service;

import DevBackEnd.ForLong.features.login.dto.*;
import DevBackEnd.ForLong.core.entity.RefreshToken;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.OAuth2UserInfo;
import DevBackEnd.ForLong.core.repository.RefreshRepository;
import DevBackEnd.ForLong.core.repository.UserRepository;
import DevBackEnd.ForLong.common.utils.CookieUtil;
import DevBackEnd.ForLong.common.utils.HashUtil;
import DevBackEnd.ForLong.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    public static final long REFRESHMS = 24 * 60 * 60 * 1000L;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;
    private final JoinService joinService;
    private final HttpServletResponse response;
    private final HttpSession session;


    public CustomOauth2UserService(UserRepository userRepository, RefreshRepository refreshRepository, CookieUtil cookieUtil, JwtUtil jwtUtil,
                                   JoinService joinService, HttpServletResponse response, HttpSession session) {
        this.userRepository = userRepository;
        this.refreshRepository = refreshRepository;
        this.cookieUtil = cookieUtil;
        this.jwtUtil = jwtUtil;
        this.joinService = joinService;
        this.response = response;
        this.session = session;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        log.info("OAuth2 로그인 요청 - Provider: {}", request.getClientRegistration().getRegistrationId());
        log.info("Authorization URI: {}", request.getClientRegistration().getProviderDetails().getAuthorizationUri());
        log.info("Redirect URI: {}", request.getClientRegistration().getRedirectUri());


        OAuth2User oAuth2User = super.loadUser(request);

        log.info("OAuth2User : {}", oAuth2User.getAttributes());
        return processOAuth2User(request,oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) throws OAuth2AuthenticationException {

        String provider = request.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;
        log.info("provider: {}", provider);

        try{
            if(provider.equals("naver")){
                log.info("네이버 로그인 요청");
                oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
            } else if (provider.equals("kakao")) {
                log.info("카카오 로그인 요청");
                oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
            } else if (provider.equals("google")) {
                log.info("구글 로그인 요청");
                oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
                log.info("GoogleUserDetails 객체 생성 완료 : {}", oAuth2UserInfo);
            } else {
                throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자입니다 : " + provider);
            }
            if (oAuth2UserInfo == null) {
                throw new OAuth2AuthenticationException("OAuth2UserInfo 객체 생성 실패");
            }
        } catch (Exception e) {
            log.error("OAuth2UserInfo 매핑 실패: {}", e.getMessage());
            throw new OAuth2AuthenticationException(new OAuth2Error("oauth2_mapping_error"));
        }

        String userInfoProvider = oAuth2UserInfo.getProvider();
        String providerId = userInfoProvider +"_"+ oAuth2UserInfo.getProviderId();

        log.info("provider_Id: {}", providerId);
        String loginId = userInfoProvider +'_'+oAuth2UserInfo.getEmail();

        String nickname = oAuth2UserInfo.getNickname();
        String role = "ROLE_OAUTH2_USER";
        String email = oAuth2UserInfo.getEmail();
        String phone = oAuth2UserInfo.getMobile();

        User user = userRepository.findByLoginId(loginId);

        if (user == null){
            return registerNewUser(oAuth2User, phone, loginId, nickname, email, role, provider, providerId);
        } else if (! user.getProvider().equals(provider)) {
            return registerNewUser(oAuth2User, phone, loginId, nickname, email, role, provider, providerId);
        } else {
            log.info("기존 사용자 로그인 : {}, {}", loginId, user);
            issueAndSaveRefreshToken(loginId, user.getRole(), response);
            return new CustomUserDetail(user, oAuth2User.getAttributes());
        }
    }

    /**
     * 회원가입 메서드
     * */

    private CustomUserDetail registerNewUser(OAuth2User oAuth2User, String phone, String loginId, String nickname, String email, String role, String provider, String providerId) {

        String cleanPhone = (phone != null) ? phone.replaceAll("-", "") : "";
        String HashedPhone = HashUtil.hashPhoneNum(cleanPhone);

        if(nickname == null){
            String randomNum = String.valueOf(Math.random() * 1001);
            nickname = provider + randomNum;
        }
        JoinDTO joinDTO = new JoinDTO(loginId,"OAuth2 default value", nickname, email,
                HashedPhone, role, provider, providerId);
        log.info("신규 회원 저장 시도: {}", loginId);
        User savedUser = joinService.saveUser(joinDTO);
        log.info("신규 회원 저장 완료 : {}", joinDTO);

        issueAndSaveRefreshToken(loginId, role, response);
        return new CustomUserDetail(savedUser, oAuth2User.getAttributes());
    }

    /**
     * 신규 사용자와 기존 사용자 로그인 시에도 공통적으로 Refresh 토큰을 발급하고 쿠키로
     * 클라이언트 개발자에게 제공하기 위한 함수
     * */
    private void issueAndSaveRefreshToken(String loginId, String role, HttpServletResponse response) {
        String refresh = jwtUtil.createJwt("refresh", loginId, role, REFRESHMS);

        // Refresh Token을 안전한 쿠키로 저장
//        session.setAttribute("refresh", refresh);
        response.addCookie(cookieUtil.createCookie("refresh", refresh));

        // 기존 Refresh Token 삭제 후 새로 저장
        log.info("refresh 토큰 저장 시도");
        if(refreshRepository.existsByUserId(loginId)){
            refreshRepository.deleteByUserId(loginId);
        }
        addRefreshEntity(loginId,refresh,REFRESHMS);
        log.info("Refresh Token 갱신 완료 - User: {}", loginId);
    }

    /**
     * ReFresh 토큰 생성 메서드
     * */
    private void addRefreshEntity(String loginId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setUserId(loginId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

}