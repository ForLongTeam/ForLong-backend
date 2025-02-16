package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 병원 정보를 관리하는 Repository 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공
 * Hospital 엔티티와 Long 타입의 ID를 사용
 */
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    
    /**
     * 병원 이름에 특정 키워드가 포함된 병원들을 검색하는 메소드
     * Spring Data JPA의 명명 규칙을 따라 자동으로 쿼리 생성
     * 
     * @param keyword 검색할 병원 이름의 키워드 (부분 일치)
     * @return 검색된 병원들의 목록
     */
    List<Hospital> findByHospitalNameContaining(String keyword);
    
    /**
     * 주어진 위도/경도 좌표를 기준으로 5km 반경 내의 병원들을 검색하는 메소드
     * MySQL의 ST_Distance_Sphere 함수를 사용하여 구면 상의 두 지점 간 거리를 계산
     * 
     * @param latitude 기준 위치의 위도
     * @param longitude 기준 위치의 경도
     * @return 5km 반경 내의 병원 목록
     * 
     * 쿼리 설명:
     * - hospital과 address 테이블을 조인
     * - ST_Distance_Sphere 함수로 두 지점 간의 거리를 미터 단위로 계산
     * - POINT 함수로 위도/경도 좌표를 공간 데이터로 변환
     * - 5000미터(5km) 이내의 병원만 필터링
     */
    @Query(value = "SELECT h.* FROM hospital h " +
           "JOIN address a ON h.address_id = a.address_id " +
           "WHERE ST_Distance_Sphere(" +
           "    POINT(CAST(a.longitude AS DOUBLE), CAST(a.latitude AS DOUBLE)), " +
           "    POINT(?2, ?1)" +
           ") <= 5000", nativeQuery = true)
    List<Hospital> findHospitalsWithin5km(double latitude, double longitude);
}