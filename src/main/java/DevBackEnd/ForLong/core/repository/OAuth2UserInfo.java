package DevBackEnd.ForLong.core.repository;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getNickname();
    String getEmail();
    String getMobile();
}
