package DevBackEnd.ForLong.features.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSaveDTO {

    @NotBlank
    private String loginId;

    @NotBlank(message = "제목은 필수 입력 값 입니다.")
    private String title;

    @NotBlank(message = "제목은 필수 입력 값 입니다.")
    private String content;

}
