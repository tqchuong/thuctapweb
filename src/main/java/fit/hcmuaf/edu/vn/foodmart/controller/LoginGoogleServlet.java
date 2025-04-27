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
        System.out.println("Acc: " + acc);

        UserDAO userDAO = new UserDAO();
        Users user = new Users();
        if (userDAO.emailExists(acc.getEmail())) {
            // Email đã tồn tại -> đăng nhập
            user = userDAO.getUserByUsername(acc.getGiven_name());
            // Lưu thông tin đăng nhập vào session
            request.getSession().setAttribute("auth", user);
            SessionManager.addSession(user.getUsername(), request.getSession());
            response.sendRedirect("home.jsp");
        } else {
            // Email chưa tồn tại -> tạo tài khoản mới
//            user = new Users();
//            user.setUsername(acc.getGiven_name());
//            user.setPassword("GOOGLE_OAUTH");
//            user.setEmail(acc.getEmail());
//            user.setFullName(acc.getName());
//            user.setIs_verified(true);
//            user.setLoginType("google");
//            userDAO.insert(user);
//
//            // Sau khi thêm thì gán session
//            Users newUser = userDAO.getUserByUsername(acc.getGiven_name());
//            request.getSession().setAttribute("auth", newUser);
//            SessionManager.addSession(newUser.getUsername(), request.getSession());
//            response.sendRedirect("home.jsp");

            // Email chưa tồn tại -> lưu Google info vào session tạm và chuyển đến trang nhập username/password
            request.getSession().setAttribute("google_acc", acc);
            response.sendRedirect("complete_register.jsp"); // trang nhập thêm thông tin
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
