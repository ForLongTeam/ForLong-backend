package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NotDate {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime not_date;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;
}
