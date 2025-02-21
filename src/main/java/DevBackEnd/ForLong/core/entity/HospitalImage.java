package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HospitalImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String iamgeUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    public HospitalImage(){}

    public HospitalImage(String iamgeUrl, Hospital hospital) {
        this.iamgeUrl = iamgeUrl;
        this.hospital = hospital;
    }


}
