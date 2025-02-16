package DevBackEnd.ForLong.features.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {

    private String login_id;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String role;
    private String provider;
    private String provider_id;

    public JoinDTO(String loginId, String password, String nickname,
                   String email, String phone, String role,String provider, String provider_id){
        this.login_id = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
        this.provider_id = provider_id;
    }
}
