package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    boolean existsByJoinId(String joinId);
    User findByJoinId(String joinId);
}
