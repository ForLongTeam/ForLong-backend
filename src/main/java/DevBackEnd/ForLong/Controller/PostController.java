package DevBackEnd.ForLong.Controller;


import DevBackEnd.ForLong.Dto.ApiResponseDTO;
import DevBackEnd.ForLong.Dto.PostSaveDTO;
import DevBackEnd.ForLong.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Post 저장 api
     */
    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO<Long>> savePost(@RequestBody @Valid PostSaveDTO postSaveDTO){

        Long postId = postService.savePost(postSaveDTO);

        ApiResponseDTO<Long> response = new ApiResponseDTO<>("success", "게시물 저장이 완료되었습니다", postId);
        return ResponseEntity.ok(response);

    }
}
