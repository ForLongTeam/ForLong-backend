package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime reservation_date;

    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column
    private LocalDateTime reservation_time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    /**
     *  예약 상태 변경 메서드
     * */
    public void changeStatus(ReservationStatus newStatus) {
        this.status = newStatus;
    }

}
