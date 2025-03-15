package DevBackEnd.ForLong.features.reservation.dto;

import DevBackEnd.ForLong.core.entity.HospitalStatus;
import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationUserDTO {

    // 예약 번호
    private final Long ReservationId;
    private final LocalDateTime reservation_date;
    private final ReservationStatus status;
    private final long HospitalId;
    private final String hospitalName;
    private final String hospitalPhone;
    private final HospitalStatus hospitalStatus;


    public ReservationUserDTO(Reservation reservation) {
        this.ReservationId = reservation.getId();
        this.reservation_date = reservation.getReservation_date();
        this.status = reservation.getStatus();
        this.HospitalId = reservation.getHospital().getHospitalId();
        this.hospitalName = reservation.getHospital().getHospitalName();
        this.hospitalPhone = reservation.getHospital().getHospitalPhone();
        this.hospitalStatus = reservation.getHospital().getHospitalStatus();
    }
}
