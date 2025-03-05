package DevBackEnd.ForLong.features.user.dto;


import DevBackEnd.ForLong.core.entity.Pet;
import DevBackEnd.ForLong.core.entity.PetGender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDTO {
    private Long id;
    String pet_name;
    String getAgeMonths;
    String getAgeYears;
    String weight;
    String pet_type;
    PetGender gender;
    String petImage;

    public PetDTO(){}

}
