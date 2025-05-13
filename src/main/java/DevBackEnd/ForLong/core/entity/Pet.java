package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String pet_name;

    @Column
    private Integer ageYears;

    @Column
    private Integer ageMonths;

    @Column
    private Integer weight;

    @Column
    private String pet_type;

    @Column
    private String petImage;

    @Column
    private PetGender gender;

    @Column
    private Boolean isRepresentative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
