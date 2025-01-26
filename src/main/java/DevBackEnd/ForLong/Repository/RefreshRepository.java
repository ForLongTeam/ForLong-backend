package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefresh(String refresh);
    void save(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
