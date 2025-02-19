package DevBackEnd.ForLong.features.user.dto;

import DevBackEnd.ForLong.core.entity.Pet;
import DevBackEnd.ForLong.core.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FindUserDTO {

    private Long userId;
    private String loginid;
    private String nickname;
    private String email;
    private String provider;
    private String providerId;
    private String role;
    private List<String> pets;

    public FindUserDTO(User user){
        this.loginid = user.getLoginId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
        this.role = user.getRole();
        this.userId = user.getId();
        this.pets = user.getPets().stream()
                .map(Pet::getPet_name)
                .collect(Collectors.toList());
    }


}
