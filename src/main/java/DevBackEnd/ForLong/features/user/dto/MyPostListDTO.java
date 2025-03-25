package DevBackEnd.ForLong.features.user.dto;

import DevBackEnd.ForLong.core.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPostListDTO {

    private final Long postId;
    private final String loginId;
    private final String nickName;
    private final String title;
    private final String content;
    private final LocalDateTime ctDate;

    public MyPostListDTO(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.ctDate = post.getCtDate();
        this.loginId = post.getUser().getLoginId();
        this.nickName = post.getUser().getNickname();
    }
}
