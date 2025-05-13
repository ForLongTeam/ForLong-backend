package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // hospitalId 기반 예약 목록 조회 메소드
    @Query("SELECT r FROM Reservation r WHERE r.hospital.hospitalId = :hospitalId")
    List<Reservation> findByHospitalId(@Param("hospitalId") Long hospitalId);

    List<Reservation> findByUser(User user);

}