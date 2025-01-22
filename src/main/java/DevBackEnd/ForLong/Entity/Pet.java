package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue
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
