package ec.com.saviasoft.air.security.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {

    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public Long getExpiryDate(){
        return System.currentTimeMillis() + 1000 * 60 * 60 * 24L; // 1 day
    }

    public long getCurrentDate() {
    	return System.currentTimeMillis();
    }
}
