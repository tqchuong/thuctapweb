package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.TokenGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@WebServlet(name = "ResendVerificationServlet", value = "/resendVerification")
public class ResendVerificationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("verify.jsp?error=Email không hợp lệ!");
            return;
        }

        UserDAO userDAO = new UserDAO();
        Users user = userDAO.getUserByEmail(email);



        // Tạo mã xác thực mới
        String newToken = TokenGenerator.generateToken(email);
        Timestamp expiry = Timestamp.from(Instant.now().plusSeconds(24 * 60 * 60));
        userDAO.updateVerificationToken(email, newToken,expiry);

        // Gửi email xác thực
        String verifyLink = request.getRequestURL().toString().replace("resendVerification", "verify?token=" + newToken);
        String subject = "Xác thực tài khoản FoodMart";
        String message = "Xin chào " + user.getUsername() + ",\n\n"
                + "Bạn đã yêu cầu gửi lại email xác thực. Vui lòng bấm vào liên kết dưới đây để xác thực tài khoản của bạn:\n"
                + verifyLink + "\n\n"
                + "\nLink có hiệu lực trong 24 giờ."
                + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.\n\n"
                + "Trân trọng,\nFoodMart Team";

        boolean emailSent = UserDAO.sendMail(email, subject, message);


        if (emailSent) {
            request.setAttribute("message", "Email xác thực đã được gửi lại!");
        } else {
            request.setAttribute("error", "Gửi email thất bại, vui lòng thử lại!");
        }

        request.getRequestDispatcher("verify.jsp").forward(request, response);


    }
}
