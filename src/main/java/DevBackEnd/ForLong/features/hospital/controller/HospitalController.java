package DevBackEnd.ForLong.features.hospital.controller;

import DevBackEnd.ForLong.features.hospital.dto.HospitalDto;
import DevBackEnd.ForLong.features.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    // HospitalService 를 주입받는 생성자
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    /**
     * 병원 검색 API 엔드포인트
     * 
     * @param keyword 검색할 병원 이름의 키워드
     * @return ResponseEntity<List<HospitalDto>> 검색된 병원 목록과 HTTP 상태 코드
     * <p>
     * [HTTP 응답 코드]
     * 200 (OK): 검색 성공. 결과가 없어도 빈 리스트 반환
     * 400 (Bad Request): 잘못된 검색어 형식 (예: 특수문자만 입력)
     * 404 (Not Found): 검색 결과가 없는 경우
     * 500 (Internal Server Error): 서버 내부 오류
     */
    @Operation(summary = "병원 검색", description = "병원 이름으로 병원을 검색합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "검색 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 검색어 형식"),
        @ApiResponse(responseCode = "404", description = "검색 결과 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/search")
    @ResponseBody
    private ResponseEntity<List<HospitalDto>> searchHospital(@RequestParam("query") String keyword) {
        try {
            List<HospitalDto> hospitals = hospitalService.searchHospital(keyword);
            if (hospitals.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(hospitals);
            }
            return ResponseEntity.ok(hospitals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 특정 병원 정보 조회 API 엔드포인트
     * 
     * @param hospitalId 조회할 병원의 ID
     * @return ResponseEntity<HospitalDto> 병원 정보와 HTTP 상태 코드
     * <p>
     * [HTTP 응답 코드]
     * 200 (OK): 병원 정보 조회 성공
     * 400 (Bad Request): 잘못된 병원 ID 형식
     * 404 (Not Found): 해당 ID의 병원이 존재하지 않음
     * 500 (Internal Server Error): 서버 내부 오류
     */
    @Operation(summary = "병원 상세 정보 조회", description = "병원 ID로 특정 병원의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "병원 정보 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 병원 ID"),
        @ApiResponse(responseCode = "404", description = "병원을 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{hospitalId}")
    @ResponseBody
    private ResponseEntity<HospitalDto> getHospital(@PathVariable Long hospitalId) {
        try {
            HospitalDto hospital = hospitalService.getHospital(hospitalId);
            return ResponseEntity.ok(hospital);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 위치 기반 주변 병원 검색 API 엔드포인트
     * 
     * @param latitude 위도 (예: "37.5665")
     * @param longitude 경도 (예: "126.9780")
     * @return ResponseEntity<List<HospitalDto>> 주변 병원 목록과 HTTP 상태 코드
     * <p>
     * [HTTP 응답 코드]
     * 200 (OK): 주변 병원 검색 성공
     * 400 (Bad Request): 잘못된 위도/경도 형식
     * 404 (Not Found): 주변에 병원이 없음
     * 500 (Internal Server Error): 서버 내부 오류
     * <p>
     * [위도/경도 형식]
     * - 위도: -90 ~ 90 사이의 실수
     * - 경도: -180 ~ 180 사이의 실수
     */
    @Operation(summary = "주변 병원 검색", description = "현재 위치(위도/경도)를 기준으로 반경 5km 이내의 병원을 검색합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "주변 병원 검색 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 위도/경도 형식"),
        @ApiResponse(responseCode = "404", description = "주변에 병원이 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/location")
    @ResponseBody
    private ResponseEntity<List<HospitalDto>> getHospitalsByLocation(
            @RequestParam("lat") String latitude,
            @RequestParam("lon") String longitude) {
        try {
            List<HospitalDto> hospitals = hospitalService.getHospitalsByLocation(latitude, longitude);
            if (hospitals.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(hospitals);
            }
            return ResponseEntity.ok(hospitals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}