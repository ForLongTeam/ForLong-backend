package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}