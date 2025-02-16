package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepostiory extends JpaRepository<Pet, Long> {

}
