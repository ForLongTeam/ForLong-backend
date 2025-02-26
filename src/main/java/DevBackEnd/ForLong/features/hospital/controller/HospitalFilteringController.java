package DevBackEnd.ForLong.features.hospital.controller;

import DevBackEnd.ForLong.common.utils.ApiResponseDTO;
import DevBackEnd.ForLong.core.entity.AvailTreat;
import DevBackEnd.ForLong.core.entity.Species;
import DevBackEnd.ForLong.features.hospital.dto.FilterOptionResponseDto;
import DevBackEnd.ForLong.features.hospital.dto.HospitalDto;
import DevBackEnd.ForLong.features.hospital.service.HospitalFilteringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 병원 필터링 관련 API 컨트롤러
 * <p>
 * 동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링하는 기능을 제공합니다.
 * 단계적 필터링을 위한 옵션 조회 API 와 필터링된 병원 목록 조회 API 를 포함합니다.
 */
@RestController
@RequestMapping("/api/hospitals/filtering")
@Tag(name = "병원 필터링 API", description = "동물 종류, 이름, 진료 분야별 병원 필터링 기능")
public class HospitalFilteringController {
    private static final Logger logger = LoggerFactory.getLogger(HospitalFilteringController.class);
    
    private final HospitalFilteringService hospitalFilteringService;

    public HospitalFilteringController(HospitalFilteringService hospitalFilteringService) {
        this.hospitalFilteringService = hospitalFilteringService;
    }

    /**
     * String 값을 안전하게 Enum 으로 변환하는 유틸리티 메서드
     * 
     * @param <T> Enum 타입
     * @param value 변환할 String 값
     * @param enumClass Enum 클래스
     * @param paramName 파라미터 이름 (오류 메시지용)
     * @return 변환된 Enum 값 또는 null (value 가 null 이거나 빈 문자열인 경우)
     * @throws IllegalArgumentException 유효하지 않은 Enum 값인 경우
     */
    private <T extends Enum<T>> T safeEnumValueOf(String value, Class<T> enumClass, String paramName) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            logger.warn("잘못된 {} 값: {}", paramName, value);
            throw new IllegalArgumentException("잘못된 " + paramName + " 값입니다: " + value);
        }
    }

    /**
     * 필터링 옵션 조회 API
     * <p>
     * 단계적 필터링을 위한 옵션(동물 종류, 동물 이름, 진료 분야)을 조회합니다.
     * 파라미터에 따라 다른 결과를 반환합니다.
     */
    @Operation(summary = "필터링 옵션 조회",
            description = "동물 종(Species)과 이름(fullName)을 단계적으로 조회합니다.<br>" +
                    "- `species` 없음: 모든 종 목록 반환 (예: 포유류, 설치류, 어류 등)<br>" +
                    "- `species`만: 해당 종의 동물 이름 목록 반환 (예: 강아지, 고양이 등)<br>" +
                    "- `species`와 `fullName`: 해당 이름의 진료 분야 목록 반환 (예: 정형외과, 내과, 안과 등)<br><br>" +
                    "<b>URL 예시:</b><br>" +
                    "- 모든 종 목록 조회: <code>GET /api/hospitals/filtering/options</code><br>" +
                    "- 포유류의 동물 이름 목록 조회: <code>GET /api/hospitals/filtering/options?species=포유류</code><br>" +
                    "- 포유류 중 강아지의 진료 분야 목록 조회: <code>GET /api/hospitals/filtering/options?species=포유류&fullName=강아지</code>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 옵션 목록을 반환했습니다.",
                    content = @Content(mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "종 목록 예시",
                                            value = "{\"status\":\"success\",\"message\":\"모든 동물 종 목록 조회 성공\",\"data\":{\"species\":[\"포유류\",\"설치류\",\"어류\",\"파충류\",\"조류\"]}}"),
                                    @ExampleObject(name = "동물 이름 목록 예시",
                                            value = "{\"status\":\"success\",\"message\":\"동물 이름 목록 조회 성공\",\"data\":{\"fullNames\":[\"강아지\",\"고양이\",\"햄스터\"]}}")
                            })),
            @ApiResponse(responseCode = "204", description = "조건에 맞는 데이터가 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류로 조회에 실패했습니다.")
    })
    @GetMapping("/options")
    public ResponseEntity<ApiResponseDTO<FilterOptionResponseDto>> getFilteringOptions(
            @Parameter(description = "조회할 동물 종 (예: 포유류, 설치류)", 
                    schema = @Schema(type = "string", allowableValues = {"포유류", "설치류", "어류", "파충류", "조류"}))
            @RequestParam(required = false) String species,
            
            @Parameter(description = "동물 이름 (예: 강아지, 고양이)")
            @RequestParam(required = false) String fullName) {
        
        logger.info("필터링 옵션 조회 API 호출 - species: {}, fullName: {}", species, fullName);
        
        try {
            // String 을 Species Enum 으로 변환 (안전하게 처리)
            Species speciesEnum;
            try {
                speciesEnum = safeEnumValueOf(species, Species.class, "species");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", e.getMessage(), null));
            }
            
            FilterOptionResponseDto options = hospitalFilteringService.findFilteringOptions(speciesEnum, fullName);
            String message;
            if (species == null) {
                message = "모든 동물 종 목록 조회 성공";
            } else if (fullName == null) {
                message = "동물 이름 목록 조회 성공";
            } else {
                message = "진료 분야 목록 조회 성공";
            }

            if (options.isEmpty()) {
                logger.info("조건에 맞는 데이터가 없습니다 - species: {}, fullName: {}", species, fullName);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponseDTO<>("success", "조건에 맞는 데이터가 없습니다.", null));
            }

            logger.info("필터링 옵션 조회 성공 - message: {}", message);
            return ResponseEntity.ok(
                    new ApiResponseDTO<>("success", message, options));
        } catch (Exception e) {
            logger.error("필터링 옵션 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponseDTO<>("error", "옵션 조회 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }

    /**
     * 병원 필터링 검색 API
     * <p>
     * 동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링하여 검색합니다.
     * 모든 파라미터는 선택적이며, 제공되지 않은 파라미터는 필터링에서 제외됩니다.
     */
    @Operation(summary = "병원 필터링 검색",
            description = "동물 종류, 동물 이름, 진료 분야에 따라 병원을 필터링합니다.<br>" +
                    "모든 파라미터는 선택적이며, 제공되지 않은 파라미터는 필터링에서 제외됩니다.<br>" +
                    "- 모든 파라미터 생략: 모든 병원 반환<br>" +
                    "- `species`만: 해당 종을 진료하는 모든 병원 반환<br>" +
                    "- `species`와 `fullName`: 해당 종의 특정 동물을 진료하는 병원 반환<br>" +
                    "- 모든 파라미터 제공: 특정 종의 특정 동물에 대해 특정 진료가 가능한 병원 반환<br><br>" +
                    "<b>URL 예시:</b><br>" +
                    "- 모든 병원 조회: <code>GET /api/hospitals/filtering/search</code><br>" +
                    "- 포유류를 진료하는 병원 조회: <code>GET /api/hospitals/filtering/search?species=포유류</code><br>" +
                    "- 포유류 중 강아지를 진료하는 병원 조회: <code>GET /api/hospitals/filtering/search?species=포유류&fullName=강아지</code><br>" +
                    "- 포유류 중 강아지의 정형외과 진료가 가능한 병원 조회: <code>GET /api/hospitals/filtering/search?species=포유류&fullName=강아지&availTreat=정형외과</code>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 병원 목록을 반환했습니다.",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"status\":\"success\",\"message\":\"병원 목록 조회 성공\",\"data\":[{\"id\":1,\"hospitalName\":\"동물사랑병원\",\"hospitalPhone\":\"02-123-4567\",\"hospitalStatus\":\"OPEN\",\"startTime\":\"09:00\",\"endTime\":\"18:00\",\"address\":{\"city\":\"서울\",\"street\":\"강남구 테헤란로\",\"zipcode\":\"06234\"},\"rating\":4}]}"))),
            @ApiResponse(responseCode = "204", description = "조건에 맞는 병원이 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류로 조회에 실패했습니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDTO<List<HospitalDto>>> getHospitalsByCriteria(
            @Parameter(description = "동물 종류 (예: 포유류, 설치류)",
                    schema = @Schema(type = "string", allowableValues = {"포유류", "설치류", "어류", "파충류", "조류"}))
            @RequestParam(required = false) String species,
            
            @Parameter(description = "동물 이름 (예: 강아지, 고양이)")
            @RequestParam(required = false) String fullName,
            
            @Parameter(description = "진료 분야 (예: 정형외과, 내과, 안과)",
                    schema = @Schema(type = "string", allowableValues = {"정형외과", "내과", "안과"}))
            @RequestParam(required = false) String availTreat) {
        
        logger.info("병원 필터링 검색 API 호출 - species: {}, fullName: {}, availTreat: {}", species, fullName, availTreat);
        
        try {
            // String 을 Enum 으로 변환 (안전하게 처리)
            Species speciesEnum;
            AvailTreat availTreatEnum;
            
            try {
                speciesEnum = safeEnumValueOf(species, Species.class, "species");
                availTreatEnum = safeEnumValueOf(availTreat, AvailTreat.class, "진료 분야");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponseDTO<>("error", e.getMessage(), null));
            }
            
            List<HospitalDto> hospitals = hospitalFilteringService.findHospitalsByCriteria(speciesEnum, fullName, availTreatEnum);
            if (hospitals.isEmpty()) {
                logger.info("조건에 맞는 병원이 없습니다 - species: {}, fullName: {}, availTreat: {}", species, fullName, availTreat);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponseDTO<>("success", "조건에 맞는 병원이 없습니다.", null));
            }
            String message = (species == null) ? "모든 병원 조회 성공" : "병원 목록 조회 성공";
            logger.info("병원 필터링 검색 성공 - 결과 수: {}, message: {}", hospitals.size(), message);
            return ResponseEntity.ok(
                    new ApiResponseDTO<>("success", message, hospitals));
        } catch (Exception e) {
            logger.error("병원 목록 조회 중 오류 발생", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponseDTO<>("error", "병원 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), null));
        }
    }
}