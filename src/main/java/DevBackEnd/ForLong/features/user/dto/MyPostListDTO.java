package DevBackEnd.ForLong.features.user.dto;

import DevBackEnd.ForLong.core.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostListDTO {

    private Long postId;
    private String loginId;
    private String nickName;
    private String title;
    private String content;
    private LocalDateTime ctDate;

    public MyPostListDTO(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.ctDate = post.getCtDate();
        this.loginId = post.getUser().getLoginId();
        this.nickName = post.getUser().getNickname();
    }
}
