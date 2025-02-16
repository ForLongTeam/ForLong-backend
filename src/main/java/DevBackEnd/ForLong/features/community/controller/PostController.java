package DevBackEnd.ForLong.features.community.controller;


import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.features.community.dto.EditPostDTO;
import DevBackEnd.ForLong.features.community.dto.PostResponseDTO;
import DevBackEnd.ForLong.features.community.dto.PostSaveDTO;
import DevBackEnd.ForLong.features.community.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Post 상세 조회 api
     * */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 상세보기 조회 성공"),
            @ApiResponse(responseCode = "400", description = "해당 게시물 없음"),
            @ApiResponse(responseCode = "500", description = "서버 조회 오류")
    })
    @Operation(summary = "게시물 상세 조회",
            description = "postId를 통해 해당 게시물 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<PostResponseDTO>> getPost(@PathVariable Long postId){
        PostResponseDTO userPostDTO = postService.getUserPost(postId);

        ApiResponseDTO<PostResponseDTO> response = new ApiResponseDTO<>("success", "성공적으로 게시물 조회 했습니다.", userPostDTO);

        return ResponseEntity.ok(response);
    }

    /**
     * Post 수정
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 수정 성공"),
            @ApiResponse(responseCode = "400", description = "해당 게시물 없음"),
            @ApiResponse(responseCode = "500", description = "서버 수정 오류")
    })
    @Operation(summary = "게시물 수정",
            description = "postId를 통해 해당 게시물 수정 및 EditPostDTO를 통해 값 수정")
    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<Void>> EditPost(@PathVariable Long postId,
                                                         @RequestBody EditPostDTO editPostDTO){
        postService.editPost(postId,editPostDTO);

        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success", "게시물 수정 성공", null);

        return ResponseEntity.ok(response);
    }

    /**
     * Post 삭제
     * */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당 게시물 없음"),
            @ApiResponse(responseCode = "500", description = "서버 수정 오류")
    })
    @Operation(summary = "게시물 삭제",
            description = "postId를 통해 해당 게시물 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDTO<Void>> deletePost(@PathVariable Long postId){

        postService.deletePost(postId);
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success", "게시물 삭제 완료했습니다.", null);
        return ResponseEntity.ok(response);
    }
}
