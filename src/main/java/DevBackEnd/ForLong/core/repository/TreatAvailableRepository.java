package DevBackEnd.ForLong.core.repository;

import DevBackEnd.ForLong.core.entity.AvailTreat;
import DevBackEnd.ForLong.core.entity.Hospital;
import DevBackEnd.ForLong.core.entity.Species;
import DevBackEnd.ForLong.core.entity.TreatAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 진료 가능 정보 리포지토리
 * <p>
 * 동물 종류, 동물 이름, 진료 분야에 따른 병원 필터링 기능을 제공합니다.
 * 단계적 필터링을 위한 옵션 조회 쿼리와 필터링된 병원 목록 조회 쿼리를 포함합니다.
 */
@Repository
public interface TreatAvailableRepository extends JpaRepository<TreatAvailable, Long> {
    /**
     * 모든 동물 종류 목록 조회
     * 
     * @return 중복 없는 동물 종류 목록
     */
    @Query("SELECT DISTINCT ta.species FROM TreatAvailable ta")
    List<Species> findDistinctSpecies();

    /**
     * 특정 동물 종류에 해당하는 동물 이름 목록 조회
     * 
     * @param species 동물 종류
     * @return 중복 없는 동물 이름 목록
     */
    @Query("SELECT DISTINCT ta.fullName FROM TreatAvailable ta WHERE ta.species = :species")
    List<String> findDistinctFullNameBySpecies(@Param("species") Species species);

    /**
     * 특정 동물 이름에 해당하는 진료 분야 목록 조회
     * 
     * @param fullName 동물 이름
     * @return 중복 없는 진료 분야 목록
     */
    @Query("SELECT DISTINCT ta.availTreat FROM TreatAvailable ta WHERE ta.fullName = :fullName")
    List<AvailTreat> findDistinctAvailTreatByFullName(@Param("fullName") String fullName);

    /**
     * 필터링 조건에 맞는 병원 목록 조회
     * <p>
     * 동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링합니다.
     * 모든 파라미터는 선택적이며, 제공되지 않은 파라미터는 필터링에서 제외됩니다.
     * 
     * @param species 동물 종류 (null 가능)
     * @param fullName 동물 이름 (null 가능)
     * @param availTreat 진료 분야 (null 가능)
     * @return 필터링된 병원 목록
     */
    @Query("SELECT DISTINCT h FROM Hospital h JOIN h.vets v JOIN TreatAvailable t ON t.vet = v " +
            "WHERE (:species IS NULL OR t.species = :species) " +
            "AND (:fullName IS NULL OR t.fullName = :fullName) " +
            "AND (:availTreat IS NULL OR t.availTreat = :availTreat)")
    List<Hospital> findHospitalsByCriteria(@Param("species") Species species,
                                           @Param("fullName") String fullName,
                                           @Param("availTreat") AvailTreat availTreat);
}