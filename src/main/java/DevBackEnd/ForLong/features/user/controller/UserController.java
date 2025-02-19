package DevBackEnd.ForLong.features.user.controller;

import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.features.community.dto.EditUserDTO;
import DevBackEnd.ForLong.features.user.dto.FindUserDTO;
import DevBackEnd.ForLong.features.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원 정보 조회
     *
     {
     "data": {
     "loginId": "user123",
     "nickname": "JohnDoe",
     "email": "johndoe@example.com",
     "pets": [
     {
     "id": 1,
     "name": "Happy",
     "type": "강아지"
     }
     }
     *
     * */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @Operation(summary = "회원 정보 조회",
            description = "     {\n" +
                    "         \"data\": {\n" +
                    "         \"loginId\": \"user123\",\n" +
                    "         \"nickname\": \"JohnDoe\",\n" +
                    "         \"email\": \"johndoe@example.com\",\n" +
                    "         \"pets\": [\n" +
                    "         {\n" +
                    "         \"id\": 1,\n" +
                    "         \"name\": \"Happy\",\n" +
                    "         \"type\": \"강아지\"\n" +
                    "         }\n" +
                    "     }"
    )
    @GetMapping("/{loginId}")
    public ResponseEntity<ApiResponseDTO<FindUserDTO>> getUserInfo(@PathVariable String loginId){
        FindUserDTO findUserDTO = userService.getUserByLoginId(loginId);

        ApiResponseDTO<FindUserDTO> response = new ApiResponseDTO<>("sucess", "회원정보 조회 성공", findUserDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원정보 수정
     * */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "오류"),
            @ApiResponse(responseCode = "500", description = "서버 저장 오류")
    })
    @Operation(summary = "회원 정보 수정",
            description = "회원정보를 수정 가능함. 수정 할 값만 json 형식으로 넣어서 서버에 제공." +
                    "새로운 애완동물도 추가 가능")
    @PostMapping("/{loginId}")
    public ResponseEntity<ApiResponseDTO<Void>> EditUserInfo(@PathVariable String loginId,
                                                             @RequestBody EditUserDTO editUserDTO){
        userService.editUser(loginId, editUserDTO);

        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success", "회원정보 수정 성공", null);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 탈퇴
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "오류"),
            @ApiResponse(responseCode = "500", description = "서버 삭제 오류")
    })
    @Operation(summary = "회원 탈퇴",
            description = "loginId 해당 회원을 DB에서 삭제." +
                    "loginId는 \"소셜회사_소셜 계정에 등록된 아이디\", ex) kakao_test@naver.com ")
    @DeleteMapping("/{loginId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUserInfo(@PathVariable String loginId){
        userService.deleteUser(loginId);

        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success", "회원 탈퇴 성공", null);
        return ResponseEntity.ok(response);

    }
}
