package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefresh(String refresh);
    Boolean existsByUserId(String loginId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.refresh = :refresh")
    void deleteByRefresh(String refresh);

    @Transactional
    void deleteByUserId(String userId);
}
