package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);
    Post findById(long id);
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
}
