package DevBackEnd.ForLong.Converter;

import DevBackEnd.ForLong.Dto.HospitalDto;
import DevBackEnd.ForLong.Entity.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Hospital 엔티티와 HospitalDto 간의 변환을 담당하는 MapStruct 매퍼 인터페이스
 * 
 * MapStruct가 컴파일 시점에 자동으로 구현체를 생성합니다.
 * 생성된 구현체는 HospitalConverterImpl 클래스로 생성됩니다.
 * 
 * [MapStruct 특징]
 * - 컴파일 타임에 매핑 코드 생성으로 런타임 오버헤드 없음
 * - 타입 안전성 보장
 * - 자동 널 체크 및 컬렉션 처리
 */
@Mapper
public interface HospitalConverter {
    
    /**
     * 싱글톤 패턴으로 구현된 매퍼 인스턴스
     * 스레드 안전성이 보장되어 애플리케이션 전역에서 재사용 가능
     * 
     * 사용 예시:
     * HospitalDto dto = HospitalConverter.INSTANCE.toHospitalDto(hospital);
     */
    HospitalConverter INSTANCE = Mappers.getMapper(HospitalConverter.class);

    /**
     * Hospital 엔티티를 HospitalDto로 변환하는 메서드
     * 
     * MapStruct가 다음과 같은 규칙으로 자동 매핑:
     * - 동일한 이름과 호환 가능한 타입의 필드는 자동 매핑
     * - 컬렉션은 자동으로 적절한 타입으로 변환
     * - 중첩된 객체도 자동으로 매핑
     * 
     * @param hospital 변환할 Hospital 엔티티
     * @return 변환된 HospitalDto
     */
    HospitalDto toHospitalDto(Hospital hospital);

    /**
     * HospitalDto를 Hospital 엔티티로 변환하는 메서드
     * 
     * 주의사항:
     * - ID 필드가 있는 경우 기존 엔티티의 수정인지 새로운 엔티티 생성인지 구분 필요
     * - 연관 관계가 있는 엔티티의 경우 적절한 처리 필요
     * 
     * @param hospitalDto 변환할 HospitalDto
     * @return 변환된 Hospital 엔티티
     */
    Hospital toHospital(HospitalDto hospitalDto);
}
