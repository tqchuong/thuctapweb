package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;

@WebServlet(name = "CheckVerificationServlet", value = "/checkVerification")
public class CheckVerificationServlet extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        boolean isVerified = false;

        if (email != null && !email.isEmpty()) {
            UserDAO userDAO = new UserDAO();
            isVerified = userDAO.isVerified(email);
        }

        // Trả về JSON để JavaScript xử lý
        response.getWriter().write("{\"isVerified\": " + isVerified + "}");
    }
}
