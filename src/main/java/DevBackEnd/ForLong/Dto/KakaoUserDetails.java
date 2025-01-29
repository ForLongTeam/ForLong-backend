package DevBackEnd.ForLong.Dto;

import DevBackEnd.ForLong.Repository.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class KakaoUserDetails implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    private Map<String, Object> kakaoAccountAttributes;
    private Map<String, Object> profileAttributes;

    public KakaoUserDetails(Map<String, Object> attributes){
        this.attributes = attributes;
        this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");

        if (kakaoAccountAttributes != null) {
            this.profileAttributes = (Map<String, Object>) kakaoAccountAttributes.get("profile");
        } else {
            this.profileAttributes = null;
        }
    }

    @Override
    public String getProviderId() {
        if(attributes.containsKey("id")){
            return (String) attributes.get("id");
        } else {
            log.error("Provider Id를 찾을 수 없습니다.");
            return null;
        }
    }

    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getNickname() {
        if(profileAttributes != null && profileAttributes.containsKey("nickname")){
            return (String) kakaoAccountAttributes.get("nickname");
        }
        log.warn("Not found Nickname");
        return null;
    }

    @Override
    public String getEmail() {
        if(kakaoAccountAttributes != null && kakaoAccountAttributes.containsKey("email")){
            return (String) kakaoAccountAttributes.get("email");
        }
        log.warn("Not found Email");
        return null;
    }

    @Override
    public String getMobile() {
        if (kakaoAccountAttributes != null && kakaoAccountAttributes.containsKey("phone_number")) {
            return (String) kakaoAccountAttributes.get("phone_number");
        }
        log.warn("휴대폰 번호를 찾을 수 없습니다.");
        return null;
    }
}
