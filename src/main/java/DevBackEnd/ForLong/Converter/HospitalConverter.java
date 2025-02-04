package DevBackEnd.ForLong.Converter;

import DevBackEnd.ForLong.Dto.HospitalDto;
import DevBackEnd.ForLong.Entity.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalConverter {
    
    HospitalConverter INSTANCE = Mappers.getMapper(HospitalConverter.class);

    /**
     * Hospital 엔티티를 HospitalDto 로 변환하는 메서드
     * @param hospital 변환할 Hospital 엔티티
     * @return 변환된 HospitalDto
     */
    HospitalDto toHospitalDto(Hospital hospital);

    /**
     * HospitalDto 를 Hospital 엔티티로 변환하는 메서드
     * @param hospitalDto 변환할 HospitalDto
     * @return 변환된 Hospital 엔티티
     */
    Hospital toHospital(HospitalDto hospitalDto);
}
