package DevBackEnd.ForLong.features.reservation.dto;

import DevBackEnd.ForLong.core.entity.NotDate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotDateResponseDTO {

    private final Long id;
    private final Long vetId;
    private final Long hospitalId;
    private final LocalDateTime notDate;

    public NotDateResponseDTO(NotDate notDate) {
        this.id = notDate.getId();
        this.vetId = notDate.getVet().getVetId();
        this.hospitalId = notDate.getHospital().getHospitalId();
        this.notDate = notDate.getNotDate();
    }
}
