package DevBackEnd.ForLong.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {

    private String userId;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String role;

    public JoinDTO(String joinId, String password, String nickname,
                   String email, String phone, String role){
        this.userId = joinId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
