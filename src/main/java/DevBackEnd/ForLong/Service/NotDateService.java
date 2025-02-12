package DevBackEnd.ForLong.Service;

import DevBackEnd.ForLong.Entity.Hospital;
import DevBackEnd.ForLong.Entity.NotDate;
import DevBackEnd.ForLong.Entity.Vet;
import DevBackEnd.ForLong.Repository.HospitalRepository;
import DevBackEnd.ForLong.Repository.NotDateRepository;
import DevBackEnd.ForLong.Repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotDateService {

    private final NotDateRepository notDateRepository;
    private final VetRepository vetRepository;
    private final HospitalRepository hospitalRepository;

    @Autowired
    public NotDateService(NotDateRepository notDateRepository, VetRepository vetRepository, HospitalRepository hospitalRepository) {
        this.notDateRepository = notDateRepository;
        this.vetRepository = vetRepository;
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * 예약 불가능한 날짜 및 시간 추가.............. 개같네
     * @param vetId : 의사 ID
     * @param hospitalId : 병원 ID
     * @param notDate : 예약 불가능한 날짜 및 시간 timestamp임
     * @return -> 생성된 NotDate 엔티티
     */

    public NotDate createNotDate(Long vetId, Long hospitalId, LocalDateTime notDate) {
        // 1. Vet, Hotpital 엔티티 조회.
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 의사 ID: " + vetId)); // 의사 ID가 없을 경우

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + hospitalId)); // 병원 ID가 없을 경우

        // 2. NotDate 엔티티 생성.
        NotDate newNotDate = new NotDate();
        newNotDate.setVet(vet);
        newNotDate.setHospital(hospital);
        newNotDate.setNotDate(notDate);

        // 3. NotDate 엔티티 저장.
        return notDateRepository.save(newNotDate);
    }
}