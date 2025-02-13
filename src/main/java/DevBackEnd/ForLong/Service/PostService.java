package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Dto.PostSaveDTO;
import DevBackEnd.ForLong.Entity.Post;
import DevBackEnd.ForLong.Entity.User;
import DevBackEnd.ForLong.Repository.PostRepository;
import DevBackEnd.ForLong.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long savePost(PostSaveDTO postSaveDTO){
        String title = postSaveDTO.getTitle();
        String content = postSaveDTO.getContent();
        String loginId = String.valueOf(postSaveDTO.getLoginId());
        Post post = new Post();
        User user = userRepository.findByLoginId(loginId);

        if (user == null) {
            log.error("해당 로그인 ID를 가진 사용자를 찾을 수 없음: {}", loginId);
            throw new RuntimeException("사용자를 찾을 수 없습니다."); // 예외 던지기
        }

        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);

        log.info("게시물 저장 시도");
        postRepository.save(post);
        log.info("게시물 저장 완료");

        return post.getId();
    }
}
