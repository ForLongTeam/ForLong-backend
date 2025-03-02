package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
    Optional<Vet> findByVetId(Long vetId);
}