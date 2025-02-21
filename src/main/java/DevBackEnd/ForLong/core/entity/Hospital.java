package DevBackEnd.ForLong.core.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA 엔티티로 지정
@Getter // 모든 필드에 대한 getter 메서드 자동 생성
@Setter // 모든 필드에 대한 setter 메서드 자동 생성
public class Hospital {

    @Id // 기본 키 지정
    @Column(name = "hospital_id") // 데이터베이스 컬럼 이름 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 생성 전략
    private Long id; // 병원 ID

    @Column(name = "hospital_name") // 데이터베이스 컬럼 이름 지정
    private String hospitalName; // 병원 이름

    @Column // 데이터베이스 컬럼
    private String hospitalPhone; // 병원 전화번호

    @Column // 데이터베이스 컬럼
    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    private HospitalStatus hospitalStatus; // 병원 상태

    @Column(name = "start_time")
    private LocalTime startTime; // 운영 시작 시간

    @Column(name = "end_time")
    private LocalTime endTime; // 운영 종료 시간

    @Column
    @Max(5) @Min(0)
    private int rating;

    @Column(columnDefinition = "TEXT") // 데이터베이스 컬럼, 텍스트 타입
    private String explainHospital; // 병원 설명

    @Column // 데이터베이스 컬럼
    private LocalDateTime breaktime; // 병원 휴식 시간

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // 일대일 관계, 연관된 엔티티 삭제 시 함께 삭제
    @JoinColumn(name = "address_id") // 외래 키 컬럼 이름 지정
    private Address address; // 병원 주소

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true) // 일대다 관계, 연관된 엔티티 삭제 시 함께 삭제
    private List<Reservation> reservations = new ArrayList<>(); // 병원 예약 목록

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // 일대다 관계, 연관된 엔티티 삭제 시 함께 삭제
    @JoinColumn(name = "hospital_id") // 외래 키 컬럼 이름 지정
    private List<Vet> vets = new ArrayList<>(); // 병원 수의사 목록

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalImage> images = new ArrayList<>();

    // 수의사 추가 메서드
    public void addVet(Vet vet) {
        vets.add(vet); // 수의사 목록에 추가
    }

    // 수의사 제거 메서드
    public void removeVet(Vet vet) {
        vets.remove(vet); // 수의사 목록에서 제거
    }

    // 예약 추가 메서드
    public void addReservation(Reservation reservation) {
        reservations.add(reservation); // 예약 목록에 추가
        reservation.setHospital(this); // 예약의 병원 설정
    }

    // 예약 제거 메서드
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation); // 예약 목록에서 제거
        reservation.setHospital(null); // 예약의 병원 설정 해제
    }

    // 이미지 추가 메서드
    public void addImage(HospitalImage image) {
        images.add(image);
        image.setHospital(this);
    }

    // 이미지 삭제 메서드
    public void removeImage(HospitalImage image) {
        images.remove(image);
        image.setHospital(null);
    }

}
