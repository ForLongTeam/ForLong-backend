package DevBackEnd.ForLong.features.hospital.dto;

import java.time.LocalTime;
import java.util.List;

import DevBackEnd.ForLong.core.entity.Address;
import DevBackEnd.ForLong.core.entity.HospitalStatus;
import DevBackEnd.ForLong.core.entity.Vet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 병원 정보를 전달하기 위한 DTO(Data Transfer Object) 클래스
 * <p>
 * 이 클래스는 병원 관련 데이터를 Entity 와 View 계층 사이에서 전송하는 역할을 합니다.
 * 병원의 기본 정보, 운영 시간, 위치 정보, 수의사 정보 등을 포함합니다.
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

    //===== 기본 정보 =====//
    /**
     * 병원 고유 식별자
     */
    private Long hospitalId;

    /**
     * 병원 이름
     */
    private String hospitalName;

    /**
     * 병원 전화번호
     */
    private String hospitalPhone;

    /**
     * 병원 운영 상태 (영업중, 휴업 등)
     */
    private HospitalStatus hospitalStatus;

    /**
     * 병원 소개 및 설명
     */
    private String explainHospital;

    /**
     * 병원 평점 (1-5점)
     */
    private int rating;

    //===== 운영 시간 정보 =====//
    /**
     * 영업 시작 시간
     * HH:mm 형식으로 직렬화됨
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    /**
     * 영업 종료 시간
     * HH:mm 형식으로 직렬화됨
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    /**
     * 휴식 시간 시작
     * HH:mm 형식으로 직렬화됨
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime breakStartTime;

    /**
     * 휴식 시간 종료
     * HH:mm 형식으로 직렬화됨
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime breakEndTime;

    //===== 위치 정보 =====//
    /**
     * 병원 주소 객체 (상세 주소 정보 포함)
     */
    private Address address;

    /**
     * 우편번호
     */
    private String zipcode;

    /**
     * 전체 주소 (도로명 또는 지번)
     */
    private String fullAddress;

    /**
     * 위도 좌표
     */
    private String latitude;

    /**
     * 경도 좌표
     */
    private String longitude;

    //===== 관련 정보 =====//
    /**
     * 병원 소속 수의사 목록
     * 병원에 근무하는 모든 수의사 정보를 포함
     */
    private List<Vet> vets;

    /**
     * 병원 이미지 URL 목록
     * 병원 내/외부 사진, 시설 사진 등의 URL
     */
    private List<String> imageUrls;
}