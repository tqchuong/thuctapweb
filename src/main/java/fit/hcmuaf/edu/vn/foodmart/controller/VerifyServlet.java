package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@WebServlet(name = "VerifyServlet", value = "/verify")
public class VerifyServlet extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Đặt encoding cho response
        String token = request.getParameter("token");

        if (token != null) {
            token = URLDecoder.decode(token, StandardCharsets.UTF_8.toString());
        }
        if (token == null) {
            sendErrorResponse(response, "Token không hợp lệ.");
            return;
        }

        // Truy vấn user dựa trên token
        String sql = """
                    SELECT username, verification_token, token_expiry 
                    FROM users 
                    WHERE verification_token = ? AND is_verified = FALSE LIMIT 1
                    """;

        try (Handle handle = jdbi.open()) {
            Users user = handle.createQuery(sql)
                    .bind(0, token)
                    .mapToBean(Users.class)
                    .findOne()
                    .orElse(null);

            if (user == null) {
                sendErrorResponse(response, "Token không hợp lệ.");
                return;
            }

            // Kiểm tra thời gian hết hạn
            if (user.getToken_expiry().before(new Timestamp(System.currentTimeMillis()))) {
                sendErrorResponse(response, "Link xác thực không hợp lệ hoặc đã hết hạn.");
                return;
            }

            // Xác thực thành công -> cập nhật DB
            String updateSql = "UPDATE users SET is_verified = TRUE, verification_token = NULL WHERE username = ?";
            int rowsUpdated = handle.createUpdate(updateSql).bind(0, user.getUsername()).execute();

            if (rowsUpdated > 0) {
                System.out.println("Xác thực thành công cho người dùng: " + user.getUsername());
                // Hiển thị HTML khi xác thực thành công
                try (PrintWriter out = response.getWriter()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                    out.println("<meta charset=\"UTF-8\">");
                    out.println("<title>Verify Account</title>");
                    out.println("<link rel='stylesheet' type='text/css' href='css/verify.css'>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div class='container'>");
                    out.println("<h1>Đã xác thực thành công!</h1>");
                    out.println("<p>Hãy quay lại trang đăng nhập.</p>");
                    out.println("<a href='login.jsp'>Quay lại đăng nhập</a>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");
                }
            } else {
                System.out.println("Không có bản ghi nào được cập nhật.");
                sendErrorResponse(response, "Có lỗi xảy ra trong quá trình xác thực.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Có lỗi xảy ra trong quá trình xử lý.");
        }
    }

    // Phương thức hỗ trợ để gửi thông báo lỗi
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Lỗi xác thực</title>");
            out.println("<link rel='stylesheet' type='text/css' href='css/verify.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lỗi</h1>");
            out.println("<p>" + message + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}