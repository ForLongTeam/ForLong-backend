package DevBackEnd.ForLong.features.hospital.dto;

import DevBackEnd.ForLong.core.entity.Address;
import DevBackEnd.ForLong.core.entity.HospitalStatus;
import DevBackEnd.ForLong.core.entity.Vet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 병원 정보를 전달하기 위한 DTO 클래스
 * Entity 와 View 계층 사이의 데이터 전송 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

    private Long id; // 병원 ID
    private String hospitalName; // 병원 이름
    private String hospitalPhone; // 병원 전화번호
    private HospitalStatus hospitalStatus; // 병원 상태

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime; // 운영시작 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime; // 운영 종료 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalDateTime breaktime; // 병원 휴식 시간

    private String explainHospital; // 병원 설명
    private Address address; // 병원 주소
    private int rating; // 병원 별점
    
    @Builder.Default
    private List<Vet> vets = new ArrayList<>(); // 병원 수의사 목록

    /**
     * 병원이 현재 영업 중인지 확인하는 메서드
     * @return 영업 중 여부
     */
    public boolean isCurrentlyOpen() {
        if (startTime == null || endTime == null) return false;
        LocalTime now = LocalTime.now();
        return now.isAfter(startTime) && now.isBefore(endTime);
    }

    /**
     * 수의사 수를 반환하는 메서드
     * @return 수의사 수
     */
    public int getVetCount() {
        return vets != null ? vets.size() : 0;
    }
}