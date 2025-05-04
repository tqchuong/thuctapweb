package fit.hcmuaf.edu.vn.foodmart.utils;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;



public class LoggerUtil {
    private static Jdbi jdbi;


    static {
        // Khởi tạo đối tượng Jdbi từ DBConnect
        jdbi = DBConnect.getJdbi();
    }

    public static void log(HttpServletRequest request, String action, String levelLog,
                           String sourcePage, String resource, String oldData, String newData) {
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession(false);

        Integer userId = null;
        String username = "guest";

        // Lấy thông tin người dùng từ session (nếu có)
        if (session != null && session.getAttribute("user") != null) {
            Users user = (Users) session.getAttribute("user");
            userId = user.getId();
            username = user.getUsername();
        }


        String sql = "INSERT INTO activity_log (user_id, username, action, level_log, ip_address, source_page, resource, old_data, new_data) " +
                "VALUES (:userId, :username, :action, :levelLog, :ipAddress, :sourcePage, :resource, :oldData, :newData)";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("userId", userId)
                    .bind("username", username)
                    .bind("action", action)
                    .bind("levelLog", levelLog)
                    .bind("ipAddress", ip)
                    .bind("sourcePage", sourcePage)
                    .bind("resource", resource)
                    .bind("oldData", oldData)
                    .bind("newData", newData)
                    .execute();

            if ("DANGER".equalsIgnoreCase(levelLog)) {
                String subject = "CẢNH BÁO NHẬT KÍ HOẠT ĐỘNG TRANG WEB";
                StringBuilder body = new StringBuilder();
                body.append("⚠️ CẢNH BÁO HÀNH ĐỘNG NGUY HIỂM ⚠️\n\n")
                        .append("Người dùng: ").append(username).append("\n")
                        .append("IP: ").append(ip).append("\n")
                        .append("Hành động: ").append(action).append("\n")
                        .append("Trang nguồn: ").append(sourcePage).append("\n")
                        .append("Tài nguyên: ").append(resource).append("\n")
                        .append("Thông tin mới: ").append(newData).append("\n");

                UserDAO.sendMail("22130029@st.hcmuaf.edu.vn", subject, body.toString());
            }


        } catch (Exception e) {
            e.printStackTrace(); // Log exception nếu cần
        }
    }
}
