package DevBackEnd.ForLong.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.CountQuery;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "location_id")
    @GeneratedValue
    private Long id;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Column
    private String location_name;

}
