package fit.hcmuaf.edu.vn.foodmart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import java.io.IOException;

@WebServlet("/checkSession")
public class CheckSessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("text/plain");

        if (session == null || session.getAttribute("auth") == null) {
            response.getWriter().write("logout");
        } else {
            Users user = (Users) session.getAttribute("auth");
            response.getWriter().write(user.getRole());  // "Admin" hoặc "User"
        }
    }
}
