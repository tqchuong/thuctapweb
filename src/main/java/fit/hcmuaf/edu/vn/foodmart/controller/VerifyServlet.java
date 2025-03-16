package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.sql.Timestamp;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "VerifyServlet", value = "/verify")
public class VerifyServlet extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token != null) {
            token = URLDecoder.decode(token, StandardCharsets.UTF_8.toString());
        } else {
            response.getWriter().println("Token không hợp lệ.");
            return;
        }
        String sql = "SELECT Username, token_expiry FROM users WHERE verification_token = ? AND is_verified = FALSE";

        try (Handle handle = jdbi.open()) {
            Users user = handle.createQuery(sql)
                    .bind(0, token)
                    .mapToBean(Users.class)
                    .findOne()
                    .orElse(null);

            if (user == null || user.getToken_expiry().before(new Timestamp(System.currentTimeMillis()))) {
                response.getWriter().println("Link xác thực không hợp lệ hoặc đã hết hạn.");
                return;
            }

            // Xác thực thành công -> cập nhật DB
            String updateSql = "UPDATE users SET is_verified = TRUE, verification_token = NULL WHERE verification_token = ?";
            handle.createUpdate(updateSql).bind(0, token).execute();

            response.getWriter().println("Xác thực thành công! ");
            response.sendRedirect("home.jsp");
        }
    }
}