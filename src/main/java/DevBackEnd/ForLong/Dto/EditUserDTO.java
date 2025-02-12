package DevBackEnd.ForLong.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값 필드 제외
public class EditUserDTO {

    private String nickname;
    private String email;
    private List<PetDTO> pets;

}
