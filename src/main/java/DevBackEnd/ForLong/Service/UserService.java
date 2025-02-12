package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Dto.FindUserDTO;
import DevBackEnd.ForLong.Entity.User;
import DevBackEnd.ForLong.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FindUserDTO getUserByLoginId(String loginId){
        User user = userRepository.findByLoginId(loginId);
        return new FindUserDTO(user);
    }
}
