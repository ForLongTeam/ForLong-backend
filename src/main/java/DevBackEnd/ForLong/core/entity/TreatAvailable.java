package DevBackEnd.ForLong.core.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TreatAvailable {

    @Column(name = "treat_id")
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Spiece spiece;

    private String fullName;

    private AvailTreat availTreat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id") // 의사 id
    private Vet vet;


}
