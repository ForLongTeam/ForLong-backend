package DevBackEnd.ForLong.features.reservation.controller;

import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import DevBackEnd.ForLong.features.reservation.dto.*;
import DevBackEnd.ForLong.features.reservation.service.ReservationService;
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


    @PostMapping("/api/reservations")
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO request) {

        ReservationResponseDTO responseDTO = reservationService.createReservation(request);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    /**
     * hospitalId 기반 예약 목록 조회 API
     * @param hospitalId 병원 ID
     * @return 해당 병원의 예약 목록
     */
    @GetMapping("/api/reservations/hospital/{hospitalId}")
    public ResponseEntity<List<ReservationHospitalDTO>> getReservationsByHospitalId(@PathVariable Long hospitalId) {
        List<ReservationHospitalDTO> reservations = reservationService.getReservationsByHospitalId(hospitalId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    /**
     * userId 기반 예약 목록 조회 API
     * @param userId 유저 ID
     * @return 해당 유저의 예약 목록
     */
    @GetMapping("/api/reservations/user/{userId}")
    public ResponseEntity<List<ReservationUserDTO>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationUserDTO> reservations = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }



    @PutMapping("/api/reservations/{reservationId}/status")
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
     * @param reservationId 취소할 예약 ID
     * @return 성공 여부
     */
    @DeleteMapping("/api/reservations/{reservationId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "예약 취소 성공", null));
    }
}