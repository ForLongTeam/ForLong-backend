package DevBackEnd.ForLong.Converter;

import DevBackEnd.ForLong.Dto.JoinDTO;
import DevBackEnd.ForLong.Entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinConverter {

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
