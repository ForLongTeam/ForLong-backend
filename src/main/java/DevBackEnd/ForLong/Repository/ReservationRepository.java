package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // hospitalId 기반 예약 목록 조회 메소드
    List<Reservation> findByHospital_Id(Long hospitalId); // 이거 맞나...???

    // userId 기반 예약 목록 조회 메소드
    List<Reservation> findByUser_Id(Long userId);
}