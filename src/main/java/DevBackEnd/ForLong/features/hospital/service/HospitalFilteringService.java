package DevBackEnd.ForLong.features.hospital.service;

import DevBackEnd.ForLong.core.entity.AvailTreat;
import DevBackEnd.ForLong.core.entity.Species;
import DevBackEnd.ForLong.core.repository.TreatAvailableRepository;
import DevBackEnd.ForLong.features.hospital.dto.FilterOptionResponseDto;
import DevBackEnd.ForLong.features.hospital.dto.HospitalDto;
import DevBackEnd.ForLong.features.hospital.mapper.HospitalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 병원 필터링 서비스
 * <p>
 * 동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링하는 기능을 제공합니다.
 * 단계적 필터링을 위한 옵션 조회와 필터링된 병원 목록 조회 기능을 포함합니다.
 */
@Service
public class HospitalFilteringService {
    private static final Logger logger = LoggerFactory.getLogger(HospitalFilteringService.class);
    
    private final TreatAvailableRepository treatAvailableRepository;
    private final HospitalMapper hospitalMapper;

    public HospitalFilteringService(TreatAvailableRepository treatAvailableRepository, HospitalMapper hospitalMapper) {
        this.treatAvailableRepository = treatAvailableRepository;
        this.hospitalMapper = hospitalMapper;
    }

    /**
     * 필터링 옵션 조회
     * <p>
     * 파라미터에 따라 다른 필터링 옵션을 반환합니다:
     * - species 가 null 인 경우: 모든 동물 종류 목록 반환
     * - species 만 제공된 경우: 해당 종의 동물 이름 목록 반환
     * - species 와 fullName 모두 제공된 경우: 해당 동물의 진료 분야 목록 반환
     * 
     * @param species 동물 종류 (null 가능)
     * @param fullName 동물 이름 (null 가능)
     * @return 필터링 옵션이 담긴 DTO
     * @throws RuntimeException 데이터 조회 중 오류 발생 시
     */
    public FilterOptionResponseDto findFilteringOptions(Species species, String fullName) {
        logger.debug("필터링 옵션 조회 요청 - species: {}, fullName: {}", species, fullName);
        
        try {
            FilterOptionResponseDto result;
            
            if (species == null) {
                logger.debug("모든 동물 종류 목록 조회");
                List<Species> speciesList = treatAvailableRepository.findDistinctSpecies();
                logger.debug("조회된 동물 종류 수: {}", speciesList.size());
                result = FilterOptionResponseDto.ofSpecies(speciesList);
            } else if (fullName == null) {
                logger.debug("동물 종류 '{}' 에 대한 동물 이름 목록 조회", species);
                List<String> fullNames = treatAvailableRepository.findDistinctFullNameBySpecies(species);
                logger.debug("조회된 동물 이름 수: {}", fullNames.size());
                result = FilterOptionResponseDto.ofFullNames(fullNames);
            } else {
                logger.debug("동물 이름 '{}' 에 대한 진료 분야 목록 조회", fullName);
                List<AvailTreat> availTreats = treatAvailableRepository.findDistinctAvailTreatByFullName(fullName);
                logger.debug("조회된 진료 분야 수: {}", availTreats.size());
                result = FilterOptionResponseDto.ofAvailTreats(availTreats);
            }
            
            logger.debug("필터링 옵션 조회 결과: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("필터링 옵션 조회 중 오류 발생", e);
            throw new RuntimeException("필터링 옵션 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 필터링 조건에 맞는 병원 목록 조회
     * <p>
     * 동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링하여 반환합니다.
     * 모든 파라미터는 선택적이며, 제공되지 않은 파라미터는 필터링에서 제외됩니다.
     * 
     * @param species 동물 종류 (null 가능)
     * @param fullName 동물 이름 (null 가능)
     * @param availTreat 진료 분야 (null 가능)
     * @return 필터링된 병원 DTO 목록
     * @throws RuntimeException 데이터 조회 중 오류 발생 시
     */
    public List<HospitalDto> findHospitalsByCriteria(Species species, String fullName, AvailTreat availTreat) {
        logger.debug("병원 필터링 검색 요청 - species: {}, fullName: {}, availTreat: {}", species, fullName, availTreat);
        
        try {
            List<HospitalDto> hospitals = treatAvailableRepository.findHospitalsByCriteria(species, fullName, availTreat).stream()
                    .map(hospitalMapper::toHospitalDto)
                    .collect(Collectors.toList());
            
            logger.debug("필터링된 병원 수: {}", hospitals.size());
            return hospitals;
        } catch (Exception e) {
            logger.error("병원 목록 조회 중 오류 발생", e);
            throw new RuntimeException("병원 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
}