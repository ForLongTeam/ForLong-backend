package DevBackEnd.ForLong.features.reservation.controller;

import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import DevBackEnd.ForLong.features.reservation.dto.*;
import DevBackEnd.ForLong.features.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @Operation(
            summary = "예약 생성",
            description = "유저 ID와 병원 ID를 받아 예약을 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "예약 생성 성공",
                    content = @Content(schema = @Schema(implementation = ReservationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/api/reservations")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO request) {

        ReservationResponseDTO responseDTO = reservationService.createReservation(request);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    /**
     * 병원 ID 기반 예약 목록 조회 API
     */
    @Operation(
            summary = "병원 예약 목록 조회",
            description = "병원 ID를 기준으로 해당 병원의 모든 예약을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ReservationHospitalDTO.class))),
            @ApiResponse(responseCode = "404", description = "병원 ID에 해당하는 예약 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/api/reservations/hospital/{hospitalId}")
    public ResponseEntity<List<ReservationHospitalDTO>> getReservationsByHospitalId(@PathVariable Long hospitalId) {
        List<ReservationHospitalDTO> reservations = reservationService.getReservationsByHospitalId(hospitalId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    /**
     * 유저 ID 기반 예약 목록 조회 API
     */
    @Operation(
            summary = "유저 예약 목록 조회",
            description = "유저 ID를 기준으로 해당 유저의 예약 목록을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ReservationUserDTO.class))),
            @ApiResponse(responseCode = "404", description = "유저 ID에 해당하는 예약 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/api/reservations/user/{userId}")
    public ResponseEntity<List<ReservationUserDTO>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationUserDTO> reservations = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }



    /**
     * 예약 상태 변경 API
     */
    @Operation(
            summary = "예약 상태 변경",
            description = "예약 ID를 기준으로 예약 상태를 변경합니다. (PENDING, APPROVED, REJECTED)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 상태 변경 성공"),
            @ApiResponse(responseCode = "404", description = "예약 ID에 해당하는 예약 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 상태값"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<ApiResponseDTO<Long>> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody ReservationStatusUpdateDTO request
    ) {
        Long updatedReservationId = reservationService.updateReservationStatus(reservationId, request);
        ApiResponseDTO<Long> response = new ApiResponseDTO<>("success","예약 상태 변경 성공",updatedReservationId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     * 예약 취소 API
     */
    @Operation(
            summary = "예약 취소",
            description = "예약 ID를 기준으로 예약을 취소합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 취소 성공"),
            @ApiResponse(responseCode = "404", description = "예약 ID에 해당하는 예약 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/api/reservations/{reservationId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "예약 취소 성공", null));
    }
}