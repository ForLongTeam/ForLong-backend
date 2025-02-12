package DevBackEnd.ForLong.Dto;

import DevBackEnd.ForLong.Entity.Pet;
import DevBackEnd.ForLong.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FindUserDTO {

    private String loginid;
    private String nickname;
    private String email;
    private List<String> pets;

    public FindUserDTO(User user){
        this.loginid = user.getLoginId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.pets = user.getPets().stream()
                .map(Pet::getPet_name)
                .collect(Collectors.toList());
    }


}
