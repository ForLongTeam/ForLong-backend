package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Dto.CustomUserDetail;
import DevBackEnd.ForLong.Entity.User;
import DevBackEnd.ForLong.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("Authenticating user: {}", loginId);

        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found : " + loginId);
        }
        if (user.getPassword() == null) {
            return new CustomUserDetail(user,null);
        }
        return new CustomUserDetail(user,null);
    }


}
