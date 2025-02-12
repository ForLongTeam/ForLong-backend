package DevBackEnd.ForLong.Repository;

import DevBackEnd.ForLong.Entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepostiory extends JpaRepository<Pet, Long> {

}
