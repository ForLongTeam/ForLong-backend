package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    boolean existsByLoginId(String loginId);
    User findByLoginId(String loginId);
    User findByProvider(User user);
}
