package DevBackEnd.ForLong.features.community.service;

import DevBackEnd.ForLong.features.community.dto.EditPostDTO;
import DevBackEnd.ForLong.features.community.dto.PostListDTO;
import DevBackEnd.ForLong.features.community.dto.PostResponseDTO;
import DevBackEnd.ForLong.features.community.dto.PostSaveDTO;
import DevBackEnd.ForLong.core.entity.Post;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.PostRepository;
import DevBackEnd.ForLong.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

    public PostResponseDTO getUserPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글을 찾을 수 없습니다: " + postId));

        return new PostResponseDTO(post);
    }

    @Transactional
    public void editPost(Long postId, EditPostDTO editPostDTO){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글을 찾을 수 없습니다: " + postId));

        // 제목이 비어있지 않으면 게시물 업데이트
        if(editPostDTO.getTitle() != null && !editPostDTO.getTitle().trim().isEmpty()){
            post.setTitle(editPostDTO.getTitle());
        }

        // 본문이 비어있지 않으면 게시물 업데이트
        if(editPostDTO.getContent() != null && !editPostDTO.getContent().trim().isEmpty()){
            post.setContent(editPostDTO.getContent());
        }

        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글을 찾을 수 없습니다: " + postId));
        log.info("게시물 삭제합니다.");
        postRepository.delete(post);
    }

    /**
     * 게시물 페이징 메서드
     * */
    public List<PostListDTO> getNewProducts(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "ctDate"));
        Page<Post> posts = postRepository.findAllByOrderByCtDateDesc(pageable);

        return posts
                .getContent().stream()
                .map(post -> modelMapper.map(post, PostListDTO.class))
                .toList();
    }
}
