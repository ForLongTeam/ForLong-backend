package DevBackEnd.ForLong.Dto;

import DevBackEnd.ForLong.Repository.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class NaverUserDetails implements OAuth2UserInfo {

    private Map<String,Object> attributes;
    private Map<String, Object> response;

    public NaverUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.response = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        if (response != null && response.containsKey("id")) {
            return (String) response.get("id");
        } else if (attributes.containsKey("id")) {
            return (String) attributes.get("id");
        } else {
            log.error("Provider ID를 찾을 수 없습니다.");
            return null;
        }
    }

    @Override
    public String getProvider() {
        return "Naver";
    }

    @Override
    public String getNickname() {
        return (String) response.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) response.get("email");
    }

    @Override
    public String getMobile() {
        return (String) response.get("mobile");
    }
}
