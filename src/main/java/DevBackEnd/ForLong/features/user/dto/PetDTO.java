package DevBackEnd.ForLong.features.user.dto;


import DevBackEnd.ForLong.core.entity.Pet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDTO {
    private Long id;
    String pet_name;
    String pet_age;
    String pet_type;

    public PetDTO(){}

    public PetDTO(Pet pet){
        this.id = pet.getId();
        this.pet_name = pet.getPet_name();
        this.pet_age = String.valueOf(pet.getPet_age());
        this.pet_type = pet.getPet_type();
    }
}
