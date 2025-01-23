package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Converter.JoinConverter;
import DevBackEnd.ForLong.Dto.JoinDTO;
import DevBackEnd.ForLong.Entity.User;
import DevBackEnd.ForLong.Repository.UserRepository;
import DevBackEnd.ForLong.Util.HashUtil;
import DevBackEnd.ForLong.Util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public JoinService(UserRepository userRepository, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
    }

    @Transactional
    public User saveUser(JoinDTO joinDTO){
        String joinId = joinDTO.getUserId();

        if(userRepository.existsByJoinId(joinId)){
           log.info("중복된 userId : {}", joinId);
           throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        }
        joinDTO.setPassword(passwordUtil.encodePassword(joinDTO.getPassword()));

        if(joinDTO.getPhone() != null){
            log.info("휴대폰 해시화 시작");
            String phone = phoneNumCleaner(joinDTO.getPhone());
            joinDTO.setPhone(HashUtil.hashPhoneNum(phone));
            log.info("휴대폰 해시화 성공");
        }

        try{
            if (joinDTO.getRole() == null) {
                log.info("회원 역할 저장 시작");
                joinDTO.setRole("ROLE_USER"); // 역할이 설정되지 않은 경우 기본값 설정
                log.info("회원 역할 저장 완료");
            }
        } catch (Exception e) {
            log.error("회원 역할 저장 중 오류 발생 : {}", e.getMessage(), e);
            throw e;
        }

        User user = JoinConverter.toEntity(joinDTO);

        try{
            log.info("회원 정보 저장 시작 : {}", user.toString());
            userRepository.save(user);
        } catch (Exception e){
            log.error("회원 정보 저장 중 오류 발생 : {}", e.getMessage(), e);
            throw e;
        }
        log.info("회원 정보 저장 완료 : {}", user);

        return userRepository.findByJoinId(joinDTO.getUserId());
    }

    public String phoneNumCleaner(String phone){
        String cleanPhone = phone.replaceAll("-", "");
        return cleanPhone;
    }
}
