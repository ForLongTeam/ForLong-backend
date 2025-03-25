package DevBackEnd.ForLong.features.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotDateRequestDTO {

    private Long vetId;
    private Long hospitalId;
    private LocalDateTime notDate;

}
