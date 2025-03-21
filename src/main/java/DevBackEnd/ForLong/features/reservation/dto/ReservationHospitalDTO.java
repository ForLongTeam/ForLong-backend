package DevBackEnd.ForLong.features.reservation.dto;

import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationHospitalDTO {

    private final Long ReservationId;
    private final LocalDateTime reservation_date;
    private final ReservationStatus status;
    private final long user_id;
    private final String nickname;


    public ReservationHospitalDTO(Reservation reservation) {
        this.ReservationId = reservation.getId();
        this.reservation_date = reservation.getReservation_date();
        this.status = reservation.getStatus();
        this.nickname = reservation.getUser().getNickname();
        this.user_id = reservation.getUser().getId();
    }
}
