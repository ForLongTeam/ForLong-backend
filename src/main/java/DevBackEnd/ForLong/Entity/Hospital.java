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
public class Hospital {

    @Id
    @Column(name = "hospital_id")
    @GeneratedValue
    private Long id;

    @Column
    private String hospital_name;

    @Column
    private String hospital_phone;

    @Column
    @Enumerated(EnumType.STRING)
    private HospitalStatus hospitalStatus;

    @Column
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT")
    private String explain;

    @Column
    private LocalDateTime breaktime;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vet> vets = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setHospital(this);
    }
}
