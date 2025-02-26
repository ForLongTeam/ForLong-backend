package DevBackEnd.ForLong.features.hospital.dto;

import DevBackEnd.ForLong.core.entity.AvailTreat;
import DevBackEnd.ForLong.core.entity.Species;
import lombok.Data;

import java.util.List;

/**
 * 병원 필터링 옵션 응답 DTO
 * <p>
 * 단계적 필터링을 위한 옵션 데이터를 담는 DTO 클래스입니다.
 * 동물 종류(species), 동물 이름(fullNames), 진료 분야(availTreats) 목록을 포함합니다.
 * 요청 파라미터에 따라 세 가지 필드 중 하나만 채워집니다.
 */
@Data
public class FilterOptionResponseDto {
    /**
     * 동물 종류 목록
     * species 파라미터가 없을 때 반환됩니다.
     */
    private List<Species> species;

    /**
     * 동물 이름 목록
     * species 파라미터만 있을 때 반환됩니다.
     */
    private List<String> fullNames;

    /**
     * 진료 분야 목록
     * species 와 fullName 파라미터가 모두 있을 때 반환됩니다.
     */
    private List<AvailTreat> availTreats;

    /**
     * 동물 종류 목록으로 DTO 를 생성하는 정적 팩토리 메서드
     *
     * @param species 동물 종류 목록
     * @return 동물 종류 목록이 설정된 DTO
     */
    public static FilterOptionResponseDto ofSpecies(List<Species> species) {
        FilterOptionResponseDto response = new FilterOptionResponseDto();
        response.species = species;
        return response;
    }

    /**
     * 동물 이름 목록으로 DTO 를 생성하는 정적 팩토리 메서드
     *
     * @param fullNames 동물 이름 목록
     * @return 동물 이름 목록이 설정된 DTO
     */
    public static FilterOptionResponseDto ofFullNames(List<String> fullNames) {
        FilterOptionResponseDto response = new FilterOptionResponseDto();
        response.fullNames = fullNames;
        return response;
    }

    /**
     * 진료 분야 목록으로 DTO 를 생성하는 정적 팩토리 메서드
     *
     * @param availTreats 진료 분야 목록
     * @return 진료 분야 목록이 설정된 DTO
     */
    public static FilterOptionResponseDto ofAvailTreats(List<AvailTreat> availTreats) {
        FilterOptionResponseDto response = new FilterOptionResponseDto();
        response.availTreats = availTreats;
        return response;
    }

    /**
     * DTO 가 비어있는지 확인하는 메서드
     * 모든 필드가 null 이거나 비어있으면 true 를 반환합니다.
     *
     * @return 모든 필드가 비어있으면 true, 아니면 false
     */
    public boolean isEmpty() {
        return (species == null || species.isEmpty()) &&
                (fullNames == null || fullNames.isEmpty()) &&
                (availTreats == null || availTreats.isEmpty());
    }
}
