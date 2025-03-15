package DevBackEnd.ForLong.features.reservation.service;

import DevBackEnd.ForLong.core.entity.Hospital;
import DevBackEnd.ForLong.core.entity.NotDate;
import DevBackEnd.ForLong.core.entity.Vet;
import DevBackEnd.ForLong.core.repository.HospitalRepository;
import DevBackEnd.ForLong.core.repository.NotDateRepository;
import DevBackEnd.ForLong.core.repository.VetRepository;
import DevBackEnd.ForLong.features.reservation.dto.NotDateRequestDTO;
import DevBackEnd.ForLong.features.reservation.dto.NotDateResponseDTO;
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


    public NotDateResponseDTO createNotDate(NotDateRequestDTO requestDTO) {
        // 1. Vet, Hotpital 엔티티 조회.
        Vet vet = vetRepository.findById(requestDTO.getVetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 의사 ID: " + requestDTO.getVetId())); // 의사 ID가 없을 경우

        Hospital hospital = hospitalRepository.findById(requestDTO.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원 ID: " + requestDTO.getHospitalId())); // 병원 ID가 없을 경우

        // 2. NotDate 엔티티 생성.
        NotDate newNotDate = new NotDate();
        newNotDate.setVet(vet);
        newNotDate.setHospital(hospital);
        newNotDate.setNotDate(requestDTO.getNotDate());

        // 3. NotDate 엔티티 저장.
        NotDate savedNotDate = notDateRepository.save(newNotDate);
        return new NotDateResponseDTO(savedNotDate);
    }
}