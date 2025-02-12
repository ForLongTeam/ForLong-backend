package DevBackEnd.ForLong.Repository;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getNickname();
    String getEmail();
    String getMobile();
}
