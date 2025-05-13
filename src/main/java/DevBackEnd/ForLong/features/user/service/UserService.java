package DevBackEnd.ForLong.features.user.service;

import DevBackEnd.ForLong.core.entity.Post;
import DevBackEnd.ForLong.core.repository.PostRepository;
import DevBackEnd.ForLong.features.community.dto.EditUserDTO;
import DevBackEnd.ForLong.features.user.dto.FindUserDTO;
import DevBackEnd.ForLong.features.user.dto.MyPostListDTO;
import DevBackEnd.ForLong.features.user.dto.PetDTO;
import DevBackEnd.ForLong.core.entity.Pet;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public FindUserDTO getUserByLoginId(String loginId){
        User user = userRepository.findByLoginId(loginId);
        return new FindUserDTO(user);
    }

    public MyPostListDTO getPostsByLoginId(String loginId){
        User user = userRepository.findByLoginId(loginId);
        Post post = postRepository.findByUser(user);

        return new MyPostListDTO(post);

    }

    @Transactional
    public void editUser(String loginId, EditUserDTO editUserDTO){
        User user = userRepository.findByLoginId(loginId);

        // nickname 수정 (null이 아닐 때만)
        if (editUserDTO.getNickname() != null) {
            user.setNickname(editUserDTO.getNickname());
        }

        // email 수정 (null이 아닐 때만)
        if (editUserDTO.getEmail() != null) {
            user.setEmail(editUserDTO.getEmail());
        }

        // 펫 정보 수정 (null이 아닐 때만)
        if(editUserDTO.getPets() != null){
            for (PetDTO petDTO : editUserDTO.getPets()) {
                if(petDTO.getId() != null){
                    Pet existingPet = user.getPets().stream()
                            .filter(pet -> pet.getId().equals(petDTO.getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 펫을 찾을 수 없습니다."));

                    // 이름만 수정 (name이 null이 아닌 경우)
                    if (petDTO.getPet_name() != null) {
                        existingPet.setPet_name(petDTO.getPet_name());
                    }
                    if (petDTO.getPet_type() != null) {
                        existingPet.setPet_type(petDTO.getPet_type());
                    }
                    if (petDTO.getAgeYears() != null && petDTO.getAgeMonths() != null) {
                        existingPet.setAgeYears(Integer.valueOf(petDTO.getAgeYears()));
                        existingPet.setAgeMonths(Integer.valueOf(petDTO.getAgeMonths()));
                    }
                } else {
                    Pet newPet = new Pet();
                    newPet.setPet_name(petDTO.getPet_name());
                    newPet.setPet_type(petDTO.getPet_type());
                    if (petDTO.getAgeYears() != null && petDTO.getAgeMonths() != null) {
                        newPet.setAgeYears(Integer.valueOf(petDTO.getAgeYears()));
                        newPet.setAgeMonths(Integer.valueOf(petDTO.getAgeMonths()));
                    }
                    newPet.setUser(user);
                    user.addPet(newPet);
                }

            }
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String loginId){
        User user = userRepository.findByLoginId(loginId);

        userRepository.delete(user);
    }

}
