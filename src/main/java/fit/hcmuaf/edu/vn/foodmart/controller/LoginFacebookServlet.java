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
        System.out.println(acc);

        UserDAO userDAO = new UserDAO();
        Users user = new Users();
        if (userDAO.emailExists(acc.getEmail())) {
            // Email đã tồn tại -> đăng nhập
            user = userDAO.getUserByUsername(acc.getEmail());
            // Lưu thông tin đăng nhập vào session
            request.getSession().setAttribute("auth", user);
            SessionManager.addSession(user.getUsername(), request.getSession());
            response.sendRedirect("home.jsp");
        } else {
            // Email chưa tồn tại -> tạo tài khoản mới
            user = new Users();
            user.setUsername(acc.getName());
            user.setPassword("FACEBOOK_OAUTH");
            user.setEmail(acc.getEmail());
            user.setFullName(acc.getName());
            user.setIs_verified(true);
            user.setLoginType("facebook");
            userDAO.insert(user);

            // Sau khi thêm thì gán session
            Users newUser = userDAO.getUserByUsername(acc.getEmail());
            request.getSession().setAttribute("auth", newUser);
            SessionManager.addSession(newUser.getUsername(), request.getSession());
            response.sendRedirect("home.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
