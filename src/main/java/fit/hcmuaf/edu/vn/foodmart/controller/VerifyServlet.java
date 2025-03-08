package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;

import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "VerifyServlet", value = "/verifyServlet")
public class VerifyServlet extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String enteredOtp = request.getParameter("otp");

        try {
            Optional<String> storedOtp = jdbi.withHandle(handle ->
                    handle.createQuery("SELECT otp FROM users WHERE email = :email AND is_verified = FALSE")
                            .bind("email", email)
                            .mapTo(String.class)
                            .findOne()
            );

            if (storedOtp.isPresent() && enteredOtp.equals(storedOtp.get())) {
                // Cập nhật trạng thái is_verified = TRUE
                jdbi.useHandle(handle ->
                        handle.createUpdate("UPDATE users SET is_verified = TRUE WHERE email = :email")
                                .bind("email", email)
                                .execute()
                );

                // Lấy thông tin người dùng sau khi cập nhật
                Optional<Users> user = jdbi.withHandle(handle ->
                        handle.createQuery("SELECT * FROM users WHERE email = :email")
                                .bind("email", email)
                                .mapToBean(Users.class)
                                .findOne()
                );

                if (user.isPresent()) {
                    // Lưu user vào session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("auth", user);
                    response.sendRedirect("home.jsp");
                } else {
                    response.sendRedirect("verify.jsp?email=" + email + "&error=nouser");
                }
            } else {
                response.sendRedirect("verify.jsp?email=" + email + "&error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("verify.jsp?email=" + email + "&error=server");
        }
    }
}
