package DevBackEnd.ForLong.Util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashUtil {
    public static String hashPhoneNum(String phone){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(phone.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("해싱 중 오류 발생",e);
        }
    }

    private static String bytesToHex(byte[] hash) {
         StringBuilder hexString = new StringBuilder();
         for (byte b : hash) {
             String hex = Integer.toHexString(0xff & b);
             if (hex.length() == 1) hexString.append('0');
             hexString.append(hex);
         }
         return hexString.toString();
    }
}
