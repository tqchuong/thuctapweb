package fit.hcmuaf.edu.vn.foodmart.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class TokenGenerator {


    private static final String SECRET_KEY = "downton@";

    public static String generateToken(String username) {
        String rawToken = UUID.randomUUID().toString() + "|" + username + "|" + Instant.now().getEpochSecond();
        String signature = hmacSha256(rawToken, SECRET_KEY);
        return rawToken + "|" + signature; // Không cần encode toàn bộ token
    }

    private static String hmacSha256(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tạo chữ ký bảo mật", e);
        }
    }

    public static void main(String[] args) {
        String token = generateToken("testUser");
        System.out.println("Token: " + token);
    }


}
