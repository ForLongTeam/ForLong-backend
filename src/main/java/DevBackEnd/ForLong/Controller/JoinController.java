package DevBackEnd.ForLong.Controller;

import DevBackEnd.ForLong.Dto.ApiResponseDTO;
import DevBackEnd.ForLong.Dto.JoinDTO;
import DevBackEnd.ForLong.Service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    /**
     * 회원가입 과정
     * */
    @Operation(summary = "회원가입 처리", description = "회원 정보를 받아 회원가입을 처리합니다." +
            "joinId, password, email, phone, nickname 정보를 받아 회원가입을 처리하빈다." +
            "email은 선택이고 나머지는 모두 필수 입력 값입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/join")
    public ResponseEntity<ApiResponseDTO<Void>> join(@Validated @RequestBody JoinDTO joinDTO,
                                                     HttpServletRequest request){

        try{
            log.info("회원가입 시작 : {}", request.getRequestURI());
            joinService.saveUser(joinDTO);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "회원가입 성공", null));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ApiResponseDTO<>(e.getMessage(), "회원가입 도중 오류가 발생했습니다.", null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponseDTO<>("error", "서버 오류", null)
            );
        }
    }
}
