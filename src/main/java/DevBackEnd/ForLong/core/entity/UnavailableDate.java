package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "unavailable_date")
public class UnavailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime not_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vet_id")
    private Vet vet;
}
