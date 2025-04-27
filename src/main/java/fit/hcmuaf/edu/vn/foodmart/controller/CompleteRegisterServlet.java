package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CompleteRegisterServlet", value = "/completeRegister")
public class CompleteRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        // Check username đã tồn tại chưa
        if (userDAO.userExists(username)) {
            request.setAttribute("error", "Username đã tồn tại, hãy chọn username khác.");
            request.getRequestDispatcher("complete_register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setIs_verified(true);
        user.setLoginType("google");

        userDAO.insert(user);

        // Đăng nhập tự động
        request.getSession().removeAttribute("google_acc");
        request.getSession().setAttribute("auth", user);
        SessionManager.addSession(user.getUsername(), request.getSession());

        response.sendRedirect("home.jsp");
    }
}
