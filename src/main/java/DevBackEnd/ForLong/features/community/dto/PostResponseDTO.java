package DevBackEnd.ForLong.features.community.dto;

import DevBackEnd.ForLong.core.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String nickname;
    private LocalDateTime createdAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.createdAt = LocalDateTime.now();
    }


}
