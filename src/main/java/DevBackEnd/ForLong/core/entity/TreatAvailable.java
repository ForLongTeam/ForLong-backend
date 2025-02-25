package DevBackEnd.ForLong.core.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "treat_available")
public class TreatAvailable {

    @Column(name = "treat_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String fullName;

    @Enumerated(EnumType.STRING)
    private AvailTreat availTreat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id") // 의사 id
    private Vet vet;


}
