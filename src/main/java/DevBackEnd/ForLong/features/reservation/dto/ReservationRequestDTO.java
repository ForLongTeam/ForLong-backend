package DevBackEnd.ForLong.features.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequestDTO {

    // 예약 요청 유저 ID
    private Long userId;
    // 예약 요청 병원 ID
    private Long hospitalId;

    private LocalDateTime reservationDate;
    private LocalDateTime reservationTime;

}
