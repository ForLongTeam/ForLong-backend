package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}