package DevBackEnd.ForLong.features.community.dto;

import DevBackEnd.ForLong.core.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDTO {
    private Long id;
    private String title;
    private String content;
    private Long user_id;
    private String nickname;
    private LocalDateTime ctDate;

    public PostListDTO(){}

    public PostListDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.user_id = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.ctDate = post.getCtDate();
    }
}
