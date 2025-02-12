package DevBackEnd.ForLong.Controller;

import DevBackEnd.ForLong.Entity.NotDate;
import DevBackEnd.ForLong.Service.NotDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class NotDateController {

    private final NotDateService notDateService;

    @Autowired
    public NotDateController(NotDateService notDateService) {
        this.notDateService = notDateService;
    }

    @PostMapping("/api/not_dates")
    public ResponseEntity<NotDate> createNotDate(@RequestBody Map<String, Object> requestBody) {
        Long vetId = Long.parseLong(requestBody.get("vetId").toString());
        Long hospitalId = Long.parseLong(requestBody.get("hospitalId").toString());
        LocalDateTime notDate = LocalDateTime.parse(requestBody.get("notDate").toString());

        NotDate createdNotDate = notDateService.createNotDate(vetId, hospitalId, notDate); // Service 메소드 호출

        return new ResponseEntity<>(createdNotDate, HttpStatus.CREATED);
    }
}