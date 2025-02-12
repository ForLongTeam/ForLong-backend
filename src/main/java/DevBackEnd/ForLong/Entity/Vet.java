package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
public class Vet {

    @Id
    @Column(name = "vet_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String vet_name;

    @Column
    @Enumerated(EnumType.STRING)
    private HospitalStatus status;

    @Column
    private LocalDateTime time;

//    @ManyToOne
//    @JoinColumn(name = "hospital_id")
//    private Hospital hospital;
// -> 병원에서만 수의사 리스트를 관리하는 것이 나을 것 같음.

    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UnavailableDate> unavailableDates = new HashSet<>();

    public void addUnavailableDate(UnavailableDate unavailableDate) {
        unavailableDates.add(unavailableDate);
        unavailableDate.setVet(this);
    }

    public void removeUnavailableDate(UnavailableDate unavailableDate) {
        unavailableDates.remove(unavailableDate);
        unavailableDate.setVet(null);
    }
}
