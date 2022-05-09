package md.ex.demo.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:twilio.properties")
public class TwilioService {

    @Value("${twilio.account_sid}")
    public String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    public String AUTH_TOKEN;
    @Value("${twilio.phone_no}")
    public String FROM_PHONE_NO;
    @Value("${twilio.pozitiv_msg}")
    public String POZITIV_MSG;
    @Value("${twilio.carantina_msg}")
    public String CARANTINA_MSG;

    private void initTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void notifyInfected(String phoneNo) {
        initTwilio();
        log.info("<______________________>Twilio service initialized");
        Message.creator(
                        new PhoneNumber("+373" + phoneNo),
                        new PhoneNumber(FROM_PHONE_NO),
                        POZITIV_MSG)
                .create();
        log.info("<______________________>Twilio message sent");
    }

    public void notifyQuarantin(String phoneNo) {
        initTwilio();
        log.info("<______________________>Twilio service initialized");
        Message.creator(
                        new PhoneNumber("+373" + phoneNo),
                        new PhoneNumber(FROM_PHONE_NO),
                        CARANTINA_MSG)
                .create();
        log.info("<______________________>Twilio message sent");
    }
}