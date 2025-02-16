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
    private Integer pet_age;

    @Column
    private String pet_type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
