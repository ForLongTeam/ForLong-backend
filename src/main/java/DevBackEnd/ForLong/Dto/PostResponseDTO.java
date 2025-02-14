package DevBackEnd.ForLong.Dto;

import DevBackEnd.ForLong.Entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createdAt;

}
