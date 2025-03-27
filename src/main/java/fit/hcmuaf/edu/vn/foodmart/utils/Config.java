package fit.hcmuaf.edu.vn.foodmart.utils;

import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;



public class Config {
    public static String vnp_TmnCode = "VZ8T3AHZ"; // Mã website của bạn trên VNPay
    public static String vnp_HashSecret = "JPSODXCRIRJYTUQG95BUTNS85NFGF8NK"; // Chuỗi bí mật VNPay
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_Returnurl = "http://localhost:8080/project/";

    // Hàm tạo số ngẫu nhiên
    public static String getRandomNumber(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    // Hàm lấy địa chỉ IP khách hàng
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


    public static String hmacSHA512(String key, String data) {
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            sha512_HMAC.init(secret_key);
            byte[] hash = sha512_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo HMAC SHA512", e);
        }
    }


    public static String hashAllFields(Map<String, String> fields) {
        // Xóa tham số vnp_SecureHash nếu có
        fields.remove("vnp_SecureHash");

        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames); // Sắp xếp tham số theo thứ tự A-Z

        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(fieldValue).append('&');
            }
        }

        // Kiểm tra và loại bỏ dấu '&' cuối cùng nếu tồn tại
        if (hashData.length() > 0 && hashData.charAt(hashData.length() - 1) == '&') {
            hashData.setLength(hashData.length() - 1);
        }

        System.out.println("Chuỗi trước khi hash: " + hashData.toString()); // Debug

        return hmacSHA512(vnp_HashSecret, hashData.toString()); // Gọi hàm mã hóa
    }



}
