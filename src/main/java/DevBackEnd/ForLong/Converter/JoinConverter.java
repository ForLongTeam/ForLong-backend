package DevBackEnd.ForLong.Converter;

import DevBackEnd.ForLong.Dto.JoinDTO;
import DevBackEnd.ForLong.Entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinConverter {

    public static JoinDTO toDTO(User user) {
        return new JoinDTO(
                user.getJoinId(),
                user.getPassword(),
                user.getNickname(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    public static User toEntity(JoinDTO joinDTO) {
        log.info("JoinDTO 변환 시작 : {}", joinDTO.toString());
        User user = new User();

        user.setJoinId(joinDTO.getUserId());
        user.setPassword(joinDTO.getPassword());
        user.setNickname(joinDTO.getNickname());
        user.setEmail(joinDTO.getEmail());
        user.setPhone(joinDTO.getPhone());
        user.setRole(joinDTO.getRole());

        log.info("JoinDTO 변환 완료 : {}", user.toString());
        return user;
    }

}