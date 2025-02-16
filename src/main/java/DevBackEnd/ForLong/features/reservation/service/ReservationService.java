package DevBackEnd.ForLong.features.reservation.service;

import DevBackEnd.ForLong.core.entity.Hospital;
import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.HospitalRepository;
import DevBackEnd.ForLong.core.repository.ReservationRepository;
import DevBackEnd.ForLong.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, HospitalRepository hospitalRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * 예약 요청 생성
     * @param userId 예약 요청 유저 ID
     * @param hospitalId 예약 요청 병원 ID
     * @param reservationDate 예약 날짜
     * @param reservationTime 예약 시간
     * @return 생성된 Reservation 엔티티
     */
    public Reservation createReservation(Long userId, Long hospitalId, LocalDateTime reservationDate, LocalDateTime reservationTime) {

        // 1. User, Hospital 엔티티 조회.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID: " + userId)); // 유저 ID가 없을 경우 예외 처리

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + hospitalId)); // 병원 ID가 없을 경우 예외 처리

        // 2. Reservation 엔티티 생성.
        Reservation newReservation = new Reservation();
        newReservation.setUser(user); // User 엔티티 설정
        newReservation.setHospital(hospital); // Hospital 엔티티 설정
        newReservation.setReservation_date(reservationDate); // 예약 날짜 설정
        newReservation.setReservation_time(reservationTime); // 예약 시간 설정
        // newReservation.setStatus(ReservationStatus.PENDING); // 예약 상태 초기값 설정.. 필요할 거 같다면 주석 해제

        // 3. Reservation 엔티티 저장.
        return reservationRepository.save(newReservation); // 저장 후 생성된 Reservation 엔티티 반환
    }


    /**
     * hospitalId 기반 예약 목록 조회
     * @param hospitalId 병원 ID
     * @return 해당 병원의 예약 목록
     */
    public List<Reservation> getReservationsByHospitalId(Long hospitalId) {
        // 1. Hospital 엔티티 존재 여부 확인
        hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + hospitalId));

        // 2. hospitalId 기반 예약 목록 조회
        return reservationRepository.findByHospital_Id(hospitalId);
    }


    /**
     * userId 기반 예약 목록 조회
     * @param userId 유저 ID
     * @return 해당 유저의 예약 목록
     */
    public List<Reservation> getReservationsByUserId(Long userId) {
        // 1. User 엔티티 존재 여부 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID: " + userId));

        // 2. userId 기반 예약 목록 조회
        return reservationRepository.findByUser_Id(userId);
    }


    /**
     * 예약 상태 변경
     * @param reservationId 예약 ID
     * @param newStatus 변경할 예약 상태
     * @return 상태가 변경된 Reservation 엔티티
     * @throws IllegalArgumentException 예약 ID에 해당하는 예약이 없거나, 예약 상태 변경에 실패한 경우..?
     */
    public Reservation updateReservationStatus(Long reservationId, ReservationStatus newStatus) {
        // 1. reservationId로 Reservation 엔티티 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 ID: " + reservationId));

        // 2. 예약 상태 변경
        reservation.changeStatus(newStatus);

        // 3. 상태가 변경된 Reservation 엔티티 저장
        return reservationRepository.save(reservation);
    }


    /**
     * 예약 취소
     * @param reservationId 취소할 예약 ID
     * @throws IllegalArgumentException 예약 ID에 해당하는 예약이 없는 경우
     */
    public void deleteReservation(Long reservationId) {
        // 1. reservationId로 Reservation 엔티티 존재 여부 확인
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("존재하지 않는 예약 ID: " + reservationId);
        }

        // 2. Reservation 엔티티 삭제
        reservationRepository.deleteById(reservationId);
        // 이거 알려주던데 맞나 뭔 소린지 모르겠음.-> 삭제 작업은 @Transactional 어노테이션으로 묶여 있으므로, 예외 발생 없으면 트랜잭션 커밋, 예외 발생 시 롤백
    }
}