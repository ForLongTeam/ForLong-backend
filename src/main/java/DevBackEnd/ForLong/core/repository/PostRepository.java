package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.Post;
import DevBackEnd.ForLong.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);

    Post findByUser(User user);
    Page<Post> findAllByOrderByCtDateDesc(Pageable pageable);
}
