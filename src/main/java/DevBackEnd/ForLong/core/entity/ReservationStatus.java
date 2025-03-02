package DevBackEnd.ForLong.core.entity;

import lombok.Getter;

@Getter
public enum ReservationStatus { // 예약 상태..
    PENDING("대기"),      // 대기
    APPROVED("허가"),     // 허가
    REJECTED("거부");     // 거부

    private final String koreanValue; // 한글로 DB에 저장되도록 쓰기. 유지보수 측면에서 이게 더 나을 듯?

    ReservationStatus(String koreanValue) {
        this.koreanValue = koreanValue;
    }
}
