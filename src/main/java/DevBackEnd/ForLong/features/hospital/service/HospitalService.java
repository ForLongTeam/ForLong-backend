package DevBackEnd.ForLong.features.hospital.service;

import DevBackEnd.ForLong.features.hospital.mapper.HospitalMapper;
import DevBackEnd.ForLong.features.hospital.dto.HospitalDto;
import DevBackEnd.ForLong.core.entity.Hospital;
import DevBackEnd.ForLong.core.entity.Address;
import DevBackEnd.ForLong.core.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * 병원 검색 메소드
     * @param keyword 검색할 병원 이름의 키워드
     * @return 검색된 병원 목록의 DTO 리스트
     */
    public List<HospitalDto> searchHospital(String keyword) {
        List<Hospital> hospitalList;
        try {
            hospitalList = hospitalRepository.findByHospitalNameContaining(keyword);
        } catch (Exception e) {
            throw new IllegalArgumentException("병원 검색 중 오류가 발생했습니다: " + e.getMessage());
        }

        return hospitalList.stream()
                .map(HospitalMapper.INSTANCE::toHospitalDto)
                .collect(Collectors.toList());
    }

    /**
     * 병원 ID로 특정 병원의 정보를 조회하는 메소드
     * 
     * @param hospitalId 조회할 병원의 ID
     * @return 조회된 병원 정보를 담은 HospitalDto 객체
     * @throws IllegalArgumentException 해당 ID의 병원이 존재하지 않을 경우 발생
     */
    public HospitalDto getHospital(Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + hospitalId));
        
        return HospitalMapper.INSTANCE.toHospitalDto(hospital);
    }

    /**
     * 위도와 경도를 기반으로 주변 병원을 검색하는 메소드
     * @param latitude 위도 (문자열)
     * @param longitude 경도 (문자열)
     * @return 반경 5km 이내의 병원 목록 (DTO 형태)
     * @throws IllegalArgumentException 잘못된 위도/경도 형식일 경우
     * @throws RuntimeException 검색 중 오류 발생 시
     */
    public List<HospitalDto> getHospitalsByLocation(String latitude, String longitude) {
        try {
            // 문자열로 받은 위도/경도를 double 타입으로 변환
            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);
            
            // 데이터베이스의 공간 쿼리를 사용하여 5km 이내의 병원들을 조회
            // ST_Distance_Sphere 함수를 사용하여 실제 지구 표면상의 거리를 계산
            List<Hospital> nearbyHospitals = hospitalRepository.findHospitalsWithin5km(lat, lon);
            
            // 데이터베이스 쿼리 결과가 없는 경우, Haversine 공식을 사용한 백업 검색 방법으로 전환
            if (nearbyHospitals.isEmpty()) {
                return getHospitalsByHaversine(lat, lon);
            }
            
            // 조회된 Hospital 엔티티들을 DTO 로 변환하여 반환
            // 1. stream()으로 데이터 흐름 생성
            // 2. map()으로 각 Hospital 을 HospitalDto 로 변환
            // 3. collect()로 결과를 List 로 수집
            return nearbyHospitals.stream()
                    .map(HospitalMapper.INSTANCE::toHospitalDto)
                    .collect(Collectors.toList());
                    
        } catch (NumberFormatException e) {
            // 위도/경도 문자열을 숫자로 변환하는 과정에서 오류 발생 시
            throw new IllegalArgumentException("잘못된 위도/경도 형식입니다: " + e.getMessage());
        } catch (Exception e) {
            // 기타 모든 예외 상황 처리
            throw new RuntimeException("위치 기반 병원 검색 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * Haversine 공식을 사용하여 주변 병원을 검색하는 백업 메소드
     * 데이터베이스의 공간 쿼리가 실패하거나 결과가 없을 때 사용됨
     * 
     * @param lat 위도 (double)
     * @param lon 경도 (double)
     * @return 반경 5km 이내의 병원 목록 (DTO 형태)
     */
    private List<HospitalDto> getHospitalsByHaversine(double lat, double lon) {
        List<Hospital> allHospitals = hospitalRepository.findAll();
        List<HospitalDto> nearbyHospitals = new ArrayList<>();
        
        for (Hospital hospital : allHospitals) {
            Address hospitalAddress = hospital.getAddress();
            if (hospitalAddress != null && 
                hospitalAddress.getLatitude() != null && 
                hospitalAddress.getLongitude() != null) {
                
                double hospitalLat = Double.parseDouble(hospitalAddress.getLatitude());
                double hospitalLon = Double.parseDouble(hospitalAddress.getLongitude());
                
                double distance = calculateDistance(lat, lon, hospitalLat, hospitalLon);
                
                if (distance <= 5.0) {
                    nearbyHospitals.add(HospitalMapper.INSTANCE.toHospitalDto(hospital));
                }
            }
        }
        
        return nearbyHospitals;
    }

    /**
     * Haversine 공식을 사용하여 두 지점 간의 거리를 계산하는 메소드
     * 지구가 완전한 구형이라고 가정하고 계산하는 근사값임
     * 
     * @param lat1 시작 지점의 위도
     * @param lon1 시작 지점의 경도
     * @param lat2 도착 지점의 위도
     * @param lon2 도착 지점의 경도
     * @return 두 지점 간의 거리 (단위: km)
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (단위: km)
        
        // 위도와 경도의 차이를 라디안으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        // Haversine 공식 구현
        // a = sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlon/2)
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        // c = 2 * atan2(√a, √(1-a))
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // 최종 거리 계산: d = R * c
        return R * c;
    }
}