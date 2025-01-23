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

    /**
     * 위도 경도로 위치를 받을건지, 우편번호로 받을건지 모르곘음.
     * */
//    @Column
//    private String latitude;
//
//    @Column
//    private String longitude;


}
