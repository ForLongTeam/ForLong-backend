package DevBackEnd.ForLong.Controller;

import DevBackEnd.ForLong.Entity.Reservation;
import DevBackEnd.ForLong.Entity.ReservationStatus;
import DevBackEnd.ForLong.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }



    @PostMapping("/api/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Map<String, Object> requestBody) {
        Long userId = Long.parseLong(requestBody.get("userId").toString());
        Long hospitalId = Long.parseLong(requestBody.get("hospitalId").toString());
        LocalDateTime reservationDate = LocalDateTime.parse(requestBody.get("reservationDate").toString()); // String -> LocalDateTime 변환
        LocalDateTime reservationTime = LocalDateTime.parse(requestBody.get("reservationTime").toString()); // String -> LocalDateTime 변환

        Reservation createdReservation = reservationService.createReservation(userId, hospitalId, reservationDate, reservationTime); // Service 메소드 호출

        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }


    /**
     * hospitalId 기반 예약 목록 조회 API
     * @param hospitalId 병원 ID
     * @return 해당 병원의 예약 목록
     */
    @GetMapping("/api/reservations/hospital/{hospitalId}")
    public ResponseEntity<List<Reservation>> getReservationsByHospitalId(@PathVariable Long hospitalId) {
        List<Reservation> reservations = reservationService.getReservationsByHospitalId(hospitalId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    /**
     * userId 기반 예약 목록 조회 API
     * @param userId 유저 ID
     * @return 해당 유저의 예약 목록
     */
    @GetMapping("/api/reservations/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }


    /**
     * 예약 상태 변경 API
     * @param reservationId 예약 ID
     * @param newStatus 변경할 예약 상태 (PENDING, APPROVED, REJECTED 중 하나)
     * @return 성공 여부
     */
    @PutMapping("/api/reservations/{reservationId}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestParam("status") ReservationStatus newStatus
    ) {
        Reservation updatedReservation = reservationService.updateReservationStatus(reservationId, newStatus);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }


    /**
     * 예약 취소 API
     * @param reservationId 취소할 예약 ID
     * @return 성공 여부
     */
    @DeleteMapping("/api/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}