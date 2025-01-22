package DevBackEnd.ForLong.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue
    private Long id;

    @Column
    private String zipcode;

    @Column
    private String fullAddress;

    @Column
    private String scAddress;


}
