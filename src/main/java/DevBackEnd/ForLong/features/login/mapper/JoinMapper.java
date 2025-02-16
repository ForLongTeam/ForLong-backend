package DevBackEnd.ForLong.features.login.mapper;

import DevBackEnd.ForLong.features.login.dto.JoinDTO;
import DevBackEnd.ForLong.core.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinMapper {

    public static User toEntity(JoinDTO joinDTO) {
        log.info("JoinDTO 변환 시작 : {}", joinDTO.toString());
        User user = new User();

        user.setLoginId(joinDTO.getLogin_id());
        user.setPassword(joinDTO.getPassword());
        user.setNickname(joinDTO.getNickname());
        user.setEmail(joinDTO.getEmail());
        user.setPhone(joinDTO.getPhone());
        user.setRole(joinDTO.getRole());
        user.setProvider(joinDTO.getProvider());
        user.setProviderId(joinDTO.getProvider_id());

        log.info("JoinDTO 변환 완료 : {}", user.toString());
        return user;
    }

}
