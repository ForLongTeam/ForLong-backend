package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Vet {

    @Id
    @Column(name = "vet_id")
    @GeneratedValue
    private Long id;

    @Column
    private String vet_name;

    @Column
    private HospitalStatus status;

    @Column
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotDate> notDates = new ArrayList<>();
}
