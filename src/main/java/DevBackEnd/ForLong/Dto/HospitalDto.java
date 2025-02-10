package DevBackEnd.ForLong.Dto;

import DevBackEnd.ForLong.Entity.Address;
import DevBackEnd.ForLong.Entity.HospitalStatus;
import DevBackEnd.ForLong.Entity.Vet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime time; // 병원 운영 시간
    private String explainHospital; // 병원 설명
    private LocalDateTime breaktime; // 병원 휴식 시간
    private Address address; // 병원 주소
    
    @Builder.Default
    private List<Vet> vets = new ArrayList<>(); // 병원 수의사 목록

    /**
     * 운영 시간 포맷팅 메서드
     * @return 포맷팅된 운영 시간 문자열
     */
    public String getFormattedTime() {
        return time != null ? time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    /**
     * 휴식 시간 포맷팅 메서드
     * @return 포맷팅된 휴식 시간 문자열
     */
    public String getFormattedBreaktime() {
        return breaktime != null ? breaktime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    /**
     * 병원이 현재 영업 중인지 확인하는 메서드
     * @return 영업 중 여부
     */
    public boolean isCurrentlyOpen() {
        if (time == null) return false;
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time) && (breaktime == null || now.isBefore(breaktime));
    }

    /**
     * 수의사 수를 반환하는 메서드
     * @return 수의사 수
     */
    public int getVetCount() {
        return vets != null ? vets.size() : 0;
    }
}