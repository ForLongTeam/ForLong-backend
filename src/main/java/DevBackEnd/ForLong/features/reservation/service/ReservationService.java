package DevBackEnd.ForLong.features.reservation.service;

import DevBackEnd.ForLong.core.entity.Hospital;
import DevBackEnd.ForLong.core.entity.Reservation;
import DevBackEnd.ForLong.core.entity.ReservationStatus;
import DevBackEnd.ForLong.core.entity.User;
import DevBackEnd.ForLong.core.repository.HospitalRepository;
import DevBackEnd.ForLong.core.repository.ReservationRepository;
import DevBackEnd.ForLong.core.repository.UserRepository;
import DevBackEnd.ForLong.features.reservation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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


    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) {

        // 1. User, Hospital 엔티티 조회.
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID: " + requestDTO.getUserId())); // 유저 ID가 없을 경우 예외 처리

        Hospital hospital = hospitalRepository.findById(requestDTO.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + requestDTO.getHospitalId())); // 병원 ID가 없을 경우 예외 처리

        // 2. Reservation 엔티티 생성.
        Reservation newReservation = new Reservation();
        newReservation.setUser(user); // User 엔티티 설정
        newReservation.setHospital(hospital); // Hospital 엔티티 설정
        newReservation.setReservation_date(requestDTO.getReservationDate()); // 예약 날짜 설정
        newReservation.setReservation_time(requestDTO.getReservationTime()); // 예약 시간 설정
        // newReservation.setStatus(ReservationStatus.PENDING); // 예약 상태 초기값 설정.. 필요할 거 같다면 주석 해제

        // 3. Reservation 엔티티 저장.
        Reservation savedReservation = reservationRepository.save(newReservation);

        return new ReservationResponseDTO(savedReservation);
    }


    /**
     * hospitalId 기반 예약 목록 조회
     * @param hospitalId 병원 ID
     * @return 해당 병원의 예약 목록
     */
    public List<ReservationHospitalDTO> getReservationsByHospitalId(Long hospitalId) {
        // 1. Hospital 엔티티 존재 여부 확인
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + hospitalId));
        // 2. hospitalId 기반 예약 목록 조회
        List<Reservation> repository = reservationRepository.findByHospitalId(hospitalId);

        return repository.stream()
                .map(ReservationHospitalDTO::new)  // 예약 리스트를 DTO 리스트로 변환
                .collect(Collectors.toList());
    }


    /**
     * userId 기반 예약 목록 조회
     * @param userId 유저 ID
     * @return 해당 유저의 예약 목록
     */
    public List<ReservationUserDTO> getReservationsByUserId(Long userId) {
        // 1. User 엔티티 존재 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 ID: " + userId));

        // 2. userId 기반 예약 목록 조회
        List<Reservation> reservations  = reservationRepository.findByUser(user);

        return reservations.stream()
                .map(ReservationUserDTO::new)  // 예약 리스트를 DTO 리스트로 변환
                .collect(Collectors.toList());
    }



    /**
     * Long reservationId : 변경 할 예약 아이디
     * ReservationStatusUpdateDTO : 변경할 상태가 들어있는 DTO
     * */
    public Long updateReservationStatus(Long reservationId, ReservationStatusUpdateDTO newStatusDTO) {

        ReservationStatus newStatus = newStatusDTO.getStatus();
        // 1. reservationId로 Reservation 엔티티 조회
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 ID: " + reservationId));

        // 2. 예약 상태 변경
        reservation.changeStatus(newStatus);

        // 3. 상태가 변경된 Reservation 엔티티 저장
        reservationRepository.save(reservation);

        return reservation.getId();
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