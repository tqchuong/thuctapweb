package fit.hcmuaf.edu.vn.foodmart.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    // Hàm mã hóa mật khẩu bằng MD5
    public static String hashPassword(String password) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Cập nhật mật khẩu vào MessageDigest
            md.update(password.getBytes());
            // Tính toán giá trị băm và lưu trong mảng byte
            byte[] digest = md.digest();

            // Chuyển mảng byte thành chuỗi hex (dễ đọc và lưu trữ)
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();  // Trả về mật khẩu đã băm
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    // Hàm kiểm tra mật khẩu với MD5 (so sánh trực tiếp chuỗi đã mã hóa)
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }

    public static void main(String[] args) {
        // Mật khẩu gốc
        String originalPassword = "123456";
        System.out.println("Mật khẩu gốc: " + originalPassword);

        // Mã hóa mật khẩu với MD5
        String hashedPassword = PasswordUtils.hashPassword(originalPassword);
        System.out.println("Mật khẩu đã mã hóa (MD5): " + hashedPassword);

        // Kiểm tra mật khẩu đúng
        boolean isMatch = PasswordUtils.verifyPassword(originalPassword, hashedPassword);
        System.out.println("Mật khẩu khớp: " + isMatch);
    }
}