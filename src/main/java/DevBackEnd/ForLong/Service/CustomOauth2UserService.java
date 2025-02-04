package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Converter.JoinConverter;
import DevBackEnd.ForLong.Dto.CustomUserDetail;
import DevBackEnd.ForLong.Dto.JoinDTO;
import DevBackEnd.ForLong.Dto.KakaoUserDetails;
import DevBackEnd.ForLong.Dto.NaverUserDetails;
import DevBackEnd.ForLong.Entity.RefreshToken;
import DevBackEnd.ForLong.Entity.User;
import DevBackEnd.ForLong.Repository.OAuth2UserInfo;
import DevBackEnd.ForLong.Repository.RefreshRepository;
import DevBackEnd.ForLong.Repository.UserRepository;
import DevBackEnd.ForLong.Util.CookieUtil;
import DevBackEnd.ForLong.Util.HashUtil;
import DevBackEnd.ForLong.Util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

        return processOAuth2User(request,oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {

        String provider = request.getClientRegistration().getClientName();
        OAuth2UserInfo oAuth2UserInfo = null;
        log.info("provider: {}", provider);

        if(provider.equals("naver")){
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        } else if (provider.equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserDetails(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자입니다 : " + provider);
        }

//        String providerId = provider +"_"+ oAuth2UserInfo.getProviderId();

        Object providerIdObj = oAuth2User.getAttributes().get("id");
        String providerId = provider + (providerIdObj != null ? providerIdObj.toString() : null);
//        String loginId = providerId;
        log.info("provider_Id: {}", providerId);
        String loginId = oAuth2UserInfo.getEmail();

        String nickname = oAuth2UserInfo.getNickname();
        String role = "ROLE_OAUTH2_USER";
        String email = oAuth2UserInfo.getEmail();
        String phone = oAuth2UserInfo.getMobile();
//        Optional<User> userOptional = Optional.ofNullable(userRepository.findByLoginId(loginId));

        User user = userRepository.findByLoginId(loginId);

        if (user == null){
            String cleanPhone = (phone != null) ? phone.replaceAll("-", "") : "";
            String HashedPhone = HashUtil.hashPhoneNum(cleanPhone);

            JoinDTO joinDTO = new JoinDTO(loginId,"OAuth2 default value",nickname,email,
                    HashedPhone,role,provider,providerId);
            log.info("신규 회원 저장 시도: {}", loginId);
            User savedUser = joinService.saveUser(joinDTO);
            log.info("신규 회원 저장 완료 : {}", joinDTO);

            String refresh = jwtUtil.createJwt("refresh", loginId, role, REFRESHMS);
            session.setAttribute("refresh", refresh);
            response.addCookie(cookieUtil.createCookie("refresh", refresh));

            log.info("refresh 토큰 저장 시도");
            refreshRepository.deleteByUserId(loginId);
            addRefreshEntity(loginId,refresh,REFRESHMS);
            log.info("refresh 토큰 저장 완료");

            return new CustomUserDetail(savedUser, oAuth2User.getAttributes());
        }

        log.info("기존 사용자 로그인 : {}, {}", loginId, user);
        return new CustomUserDetail(user, oAuth2User.getAttributes());

    }

    private void addRefreshEntity(String loginId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setUserId(loginId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

}
