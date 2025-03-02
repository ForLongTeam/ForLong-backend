package DevBackEnd.ForLong.features.login.service;

import DevBackEnd.ForLong.features.login.dto.CustomUserDetail;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.UserRepository;
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
