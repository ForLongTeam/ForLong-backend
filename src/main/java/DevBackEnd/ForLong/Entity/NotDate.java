package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "not_date")
public class NotDate {

    @Id
    @Column(name = "None_reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vet_id") // 의사 id
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(name = "Not_date")
    private LocalDateTime notDate; // SQL timestamp ~= LocalDateTime?
}

