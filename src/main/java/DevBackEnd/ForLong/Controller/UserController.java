package DevBackEnd.ForLong.Controller;

import DevBackEnd.ForLong.Dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * 실제 로그인 로직은 LoginFilter를 통해 구현됨.
     * 이 코드는 프론트앤드 개발자를 위한 명세서를 위해 적은 코드
     * */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 시 JWT 토큰 반환"),
            @ApiResponse(responseCode = "401", description = "로그인 실패 (잘못된 자격 증명)"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (필수 파라미터 누락)")
    })
    @Operation(summary = "로그인 로직 (Swagger 명세 전용)", description = "userId와 password를 포함하여 POST /login으로 요청을 보내면 기본 로그인 로직이 실행됩니다. " +
            "이 엔드포인트는 실제 요청을 처리하지 않고 문서화를 위해 제공됩니다"+"프론트엔드 팀은 JWT 토큰을 localStorage나 sessionStorage에 저장한 뒤," +
            " 인증이 필요한 API 요청 시 Authorization: Bearer <JWT 토큰> 형식으로 요청 헤더에 포함해 보내야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<Void>> loginProc(
            @Parameter(description = "사용자 ID", required = true) @RequestParam(name = "userId") @NotBlank String userId,
            @Parameter(description = "사용자 비밀번호", required = true) @RequestParam(name = "password") @NotBlank String password) {
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success","로그인 성공",null);

        return ResponseEntity.ok(response);
    }

    /**
     * 로그아웃
     * 실제 관련 로직은 filter 단에서 움직임.
     * 프론트 개발자를 위한 api 명세서를 위한 코드
     * */

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @Operation(summary = "로그아웃 로직 (Swagger 명세 전용)",
            description = "POST /logout 요청을 보내면 클라이언트 측에서 JWT 토큰을 삭제해 로그아웃을 처리합니다. 이 엔드포인트는 실제 서버에서 처리하지 않고, 문서화를 위해 제공됩니다."+
                    "로그아웃 요청 시 클라이언트 측에서 저장된 JWT 토큰을 삭제해야 합니다. " +
                    "서버는 세션을 사용하지 않으므로 JWT 기반에서는 토큰 자체를 삭제하는 것이 곧 로그아웃을 의미")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<Void>> logoutProc(){
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success", "로그아웃 성공", null);

        return ResponseEntity.ok(response);
    }
}
