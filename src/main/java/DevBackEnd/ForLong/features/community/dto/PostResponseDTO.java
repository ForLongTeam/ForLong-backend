package DevBackEnd.ForLong.features.community.dto;

import DevBackEnd.ForLong.core.entity.Post;
import DevBackEnd.ForLong.core.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createdAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser();
        this.createdAt = LocalDateTime.now();
    }


}
