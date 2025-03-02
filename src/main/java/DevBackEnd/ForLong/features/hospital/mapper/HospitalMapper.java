package DevBackEnd.ForLong.features.hospital.mapper;

import DevBackEnd.ForLong.features.hospital.dto.HospitalDto;
import DevBackEnd.ForLong.core.entity.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Hospital 엔티티와 HospitalDto 간의 변환을 담당하는 MapStruct 매퍼 인터페이스
 */
@Mapper(componentModel = "spring")  // Spring 빈으로 등록되도록 설정
public interface HospitalMapper {
    
    /**
     * 싱글톤 패턴으로 구현된 매퍼 인스턴스
     * 스레드 안전성이 보장되어 애플리케이션 전역에서 재사용 가능
     * 
     * 참고: componentModel="spring"을 사용할 경우 이 상수는 필요하지 않을 수 있습니다.
     */
    HospitalMapper INSTANCE = Mappers.getMapper(HospitalMapper.class);

    /**
     * Hospital 엔티티를 HospitalDto 로 변환하는 메서드
     */
    HospitalDto toHospitalDto(Hospital hospital);

    /**
     * HospitalDto 를 Hospital 엔티티로 변환하는 메서드
     */
    Hospital toHospital(HospitalDto hospitalDto);
}