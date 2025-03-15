package DevBackEnd.ForLong.features.reservation.dto;

import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationResponseDTO {
    private Long reservationId;
    private LocalDateTime reservationDate;
    private LocalDateTime reservationTime;
    private String hospitalName; // 병원 이름만 전달
    private ReservationStatus status;

    public ReservationResponseDTO(Reservation reservation) {
        this.reservationId = reservation.getId();
        this.reservationDate = reservation.getReservation_date();
        this.reservationTime = reservation.getReservation_time();
        this.status = reservation.getStatus();
        this.hospitalName = reservation.getHospital().getHospitalName();
    }
}
