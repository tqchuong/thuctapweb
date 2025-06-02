package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.GoogleAccount;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginGoogleServlet", value = "/loginGoogle")
public class LoginGoogleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        GoogleAccount acc = gg.getUserInfo(accessToken);
        acc.setLoginType("Google");
        System.out.println("Acc: " + acc);

        UserDAO userDAO = new UserDAO();
        Users user = new Users();
        if (userDAO.emailExists(acc.getEmail())) {
            // Email đã tồn tại -> kiểm tra isDelete
            user = userDAO.getUserByEmail(acc.getEmail());

            // Kiểm tra xem tài khoản có bị xóa không
            if (user.getIsDelete()) {
                // Nếu tài khoản bị xóa, thông báo và không cho phép đăng nhập
                System.out.println("Tài khoản đã bị xóa.");
                request.setAttribute("loginError", "Tài khoản của bạn đã bị xóa!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Lưu thông tin đăng nhập vào session
                request.getSession().setAttribute("auth", user);
                SessionManager.addSession(user.getUsername(), request.getSession());
                response.sendRedirect("home.jsp");

            }
        } else {
            // Email chưa tồn tại -> lưu Google info vào session tạm và chuyển đến trang nhập username/password
            request.getSession().setAttribute("account", acc);
            response.sendRedirect("complete_register.jsp"); // trang nhập thêm thông tin
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}