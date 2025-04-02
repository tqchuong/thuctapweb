package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "UpdatePasswordServlet", value = "/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy dữ liệu từ form
        String passwordNew = request.getParameter("passwordNew");
        String passwordConfirm = request.getParameter("passwordConfirm");

        // Lấy username từ session
        String username = (String) request.getSession().getAttribute("verifiedUser");

        // Kiểm tra dữ liệu đầu vào
        if (username == null) {
            sendErrorResponse(response, "Phiên xác thực đã hết hạn. Vui lòng thử lại.");
            return;
        }
        if (passwordNew == null || passwordConfirm == null || !passwordNew.equals(passwordConfirm)) {
            sendErrorResponse(response, "Mật khẩu không khớp hoặc không hợp lệ.");
            return;
        }
        if (passwordNew.length() < 6) { // Ví dụ: yêu cầu mật khẩu tối thiểu 6 ký tự
            sendErrorResponse(response, "Mật khẩu phải có ít nhất 6 ký tự.");
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = PasswordUtils.hashPassword(passwordNew);

        // Cập nhật mật khẩu trong cơ sở dữ liệu
        String updateSql = "UPDATE users SET password = ? WHERE username = ?";
        try (Handle handle = jdbi.open()) {
            int rowsUpdated = handle.createUpdate(updateSql)
                    .bind(0, hashedPassword)
                    .bind(1, username)
                    .execute();

            if (rowsUpdated > 0) {
                // Xóa thông tin khỏi session sau khi cập nhật thành công
                request.getSession().removeAttribute("verifiedUser");
                // Chuyển hướng đến trang đăng nhập với thông báo thành công
                response.sendRedirect("login.jsp?message=" + java.net.URLEncoder.encode("Mật khẩu đã được cập nhật thành công!", StandardCharsets.UTF_8.toString()));
            } else {
                sendErrorResponse(response, "Không thể cập nhật mật khẩu. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Có lỗi xảy ra trong quá trình cập nhật mật khẩu.");
        }
    }


    // Phương thức gửi phản hồi lỗi
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        try (java.io.PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Lỗi</title>");
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