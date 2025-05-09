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
        System.out.println(acc.getName());

        UserDAO userDAO = new UserDAO();
        Users user = new Users();

        String fullId = acc.getId();
        String shortId = fullId.substring(0,8);


        if (userDAO.idExists(Integer.parseInt(shortId))) {
            // id đã tồn tại -> kiểm tra isDelete
            user = userDAO.getUserById(Integer.parseInt(shortId));
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
            // username chưa tồn tại -> tạo tài khoản mới
            request.getSession().setAttribute("account", acc);
            response.sendRedirect("complete_register.jsp"); // trang nhập thêm thông tin
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
