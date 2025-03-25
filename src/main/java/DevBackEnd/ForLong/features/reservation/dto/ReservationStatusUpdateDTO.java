package DevBackEnd.ForLong.features.reservation.dto;

import DevBackEnd.ForLong.core.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationStatusUpdateDTO {
    private ReservationStatus status;
}
