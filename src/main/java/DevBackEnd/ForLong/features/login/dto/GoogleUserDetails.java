package DevBackEnd.ForLong.features.login.dto;

import DevBackEnd.ForLong.core.repository.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GoogleUserDetails implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public GoogleUserDetails(Map<String, Object> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            throw new IllegalArgumentException("GoogleUserDetails: attributes가 비어 있음");
        }
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        Object idObject = attributes.get("sub");
        if (idObject instanceof String) {
            return (String) idObject;
        } else if (idObject instanceof Long) {
            return String.valueOf(idObject);
        }
        log.error("GoogleUserDetails: providerId가 null이거나 올바르지 않음 - attributes: {}", attributes);
        return "UNKNOWN_ID";
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getNickname() {
        Object nameObject = attributes.get("given_name");

        if (nameObject instanceof String) {
            return (String) nameObject;
        }
        log.error("GoogleUserDetails: given_name이 null - attributes: {}", attributes);
        return "구글유저"; // 기본값 제공
    }

    @Override
    public String getEmail() {
        Object emailObject = attributes.get("email");
        if (emailObject instanceof String) {
            return (String) emailObject;
        }
        log.error("GoogleUserDetails: email이 null - attributes: {}", attributes);
        return "UNKNOWN_EMAIL"; // 기본값 제공
    }

    @Override
    public String getMobile() {
        return "";
    }
}