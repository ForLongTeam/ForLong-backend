package DevBackEnd.ForLong.features.community.dto;

import DevBackEnd.ForLong.core.entity.Post;
import DevBackEnd.ForLong.core.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDTO {
    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime ctDate;

    public PostListDTO(){}

    public PostListDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user = post.getUser();
        this.ctDate = post.getCtDate();
    }
}
