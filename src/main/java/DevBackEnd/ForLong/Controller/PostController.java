package DevBackEnd.ForLong.Controller;


import DevBackEnd.ForLong.Dto.ApiResponseDTO;
import DevBackEnd.ForLong.Dto.PostSaveDTO;
import DevBackEnd.ForLong.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 저장 성공"),
            @ApiResponse(responseCode = "400", description = "해당 회원 없음"),
            @ApiResponse(responseCode = "500", description = "서버 삭제 오류")
    })
    @Operation(summary = "게시물 작성",
            description = "loginId 해당 회원을 찾고 있는 회원이면 게시물 저장" +
                    "loginId는 \"소셜회사_소셜 계정에 등록된 아이디\", ex) kakao_test@naver.com ")
    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO<Long>> savePost(@RequestBody @Valid PostSaveDTO postSaveDTO){

        Long postId = postService.savePost(postSaveDTO);

        ApiResponseDTO<Long> response = new ApiResponseDTO<>("success", "게시물 저장이 완료되었습니다", postId);
        return ResponseEntity.ok(response);

    }
}
