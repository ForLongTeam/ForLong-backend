package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String zipcode;

    @Column
    private String fullAddress;

    @Column
    private String scAddress;

    @Column
    private String latitude;

    @Column
    private String longitude;


}
