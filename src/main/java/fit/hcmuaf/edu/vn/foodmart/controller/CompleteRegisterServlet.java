package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.LoggerUtil;
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
        String passwordConfirm = request.getParameter("passwordConfirm");
        String loginType = request.getParameter("loginType");
        String id = request.getParameter("id");
        int shortId = Integer.parseInt(id.substring(0,8));


        UserDAO userDAO = new UserDAO();

        // Check username đã tồn tại chưa
        if (userDAO.userExists(username)) {
            request.setAttribute("error", "Username đã tồn tại, hãy chọn username khác.");
            request.getRequestDispatcher("complete_register.jsp").forward(request, response);
            LoggerUtil.log(request, "Registration Failed", "ERROR", "complete_register.jsp", "User Registration", "", "Username ton tai");
            return;
        }
        // Kiểm tra mật khẩu tối thiểu 6 ký tự
        if (password == null || password.length() < 6) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("complete_register.jsp").forward(request, response);

            // Ghi log khi mật khẩu quá ngắn
            LoggerUtil.log(request, "Registration Failed", "ERROR", "complete_register.jsp", "User Registration", "", "Password too short");

            return;
        }
        // Kiểm tra mật khẩu mới khớp với xác nhận mật khẩu
        if(passwordConfirm == null || !passwordConfirm.equals(password)) {
            request.setAttribute("error", "Mật khẩu không giống nhau.");
            request.getRequestDispatcher("complete_register.jsp").forward(request, response);

            // Ghi log khi mật khẩu xác nhận không khớp
            LoggerUtil.log(request, "Registration Failed", "ERROR", "complete_register.jsp", "User Registration", "", "Password mismatch");

            return;
        }

        String hashedPassword = PasswordUtils.hashPassword(password);

        Users user = new Users();
        user.setId(shortId);
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setIs_verified(true);
        user.setLoginType(loginType != null && loginType.equals("Facebook") ? "facebook" : "google");

        userDAO.insert(user);

        // Đăng nhập tự động
        request.getSession().removeAttribute("account");
        request.getSession().setAttribute("auth", user);
        SessionManager.addSession(user.getUsername(), request.getSession());

        response.sendRedirect("home.jsp");
    }
}
