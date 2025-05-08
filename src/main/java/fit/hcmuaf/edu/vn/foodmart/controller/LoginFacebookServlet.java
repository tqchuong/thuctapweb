package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.FacebookAccount;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginFacebookServlet", value = "/loginFacebook")
public class LoginFacebookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println(code);
        FacebookLogin fb = new FacebookLogin();
        String accessToken = fb.getToken(code);
        System.out.println(accessToken);
        FacebookAccount acc = fb.getUserInfo(accessToken);
        acc.setLoginType("Facebook");
        System.out.println(acc);

        UserDAO userDAO = new UserDAO();
        Users user = new Users();
        if (userDAO.userExists(acc.getName())) {
            // username đã tồn tại -> đăng nhập
            user = userDAO.getUserByUsername(acc.getName());
            // Lưu thông tin đăng nhập vào session
            request.getSession().setAttribute("auth", user);
            SessionManager.addSession(user.getUsername(), request.getSession());
            response.sendRedirect("home.jsp");
        } else {
            // username chưa tồn tại -> tạo tài khoản mới

            request.getSession().setAttribute("account", acc);
            response.sendRedirect("complete_register.jsp"); // trang nhập thêm thông tin
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
