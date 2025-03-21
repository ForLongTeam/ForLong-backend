package DevBackEnd.ForLong.features.reservation.controller;

import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.core.entity.NotDate;
import DevBackEnd.ForLong.features.reservation.dto.NotDateRequestDTO;
import DevBackEnd.ForLong.features.reservation.dto.NotDateResponseDTO;
import DevBackEnd.ForLong.features.reservation.service.NotDateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class NotDateController {

    private final NotDateService notDateService;

    @Autowired
    public NotDateController(NotDateService notDateService) {
        this.notDateService = notDateService;
    }

    /**
     * 비예약 가능 일정 생성 API
     */
    @Operation(
            summary = "비예약 가능 일정 생성",
            description = "수의사(vetId)와 병원(hospitalId)을 받아 비예약 가능 일정을 추가합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "비예약 가능 일정 생성 성공",
                    content = @Content(schema = @Schema(implementation = NotDateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/api/not_dates")
    public ResponseEntity<ApiResponseDTO<NotDateResponseDTO>> createNotDate(@RequestBody NotDateRequestDTO requestDTO) {


        NotDateResponseDTO createdNotDate = notDateService.createNotDate(requestDTO); // Service 메소드 호출

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDTO<>("success", "비예약 가능 일정 생성 성공", createdNotDate));
    }
}