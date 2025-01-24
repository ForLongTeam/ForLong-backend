package DevBackEnd.ForLong.ServiceTest;

import DevBackEnd.ForLong.Service.JoinService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class phonehashtest {

    JoinService joinService;

    public phonehashtest(JoinService joinService) {
        this.joinService = joinService;
    }

    @Test
    void testPhoneClean(){
        String phone1 = joinService.phoneNumCleaner("010-5656-3642");
        String phone2 = "01056563642";

        assertThat(phone1).isEqualTo(phone2);
    }
}