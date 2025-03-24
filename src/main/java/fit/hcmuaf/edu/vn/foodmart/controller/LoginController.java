package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý các yêu cầu GET (nếu có)
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            System.out.println("Không thực hiện gì hết!");
        } else if (action.equals("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Kiểm tra đăng nhập
            UserDAO userDAO = new UserDAO();
            String loginStatus = userDAO.checkLogin(username, password);

            if (loginStatus.equals("LOGIN_SUCCESS")) {
                // Nếu đăng nhập thành công, lấy thông tin người dùng từ cơ sở dữ liệu
                Users user = userDAO.getUserByUsername(username);

                // Tạo session và lưu thông tin người dùng
                HttpSession session = request.getSession(true);
                session.setAttribute("auth", user);

                SessionManager.addSession(user.getUsername(), session);


                // Chuyển hướng đến trang chủ sau khi đăng nhập thành công
                response.sendRedirect("home.jsp");
            } else {
                // Nếu đăng nhập không thành công, thông báo lỗi
                String errorMessage = "";
                switch (loginStatus) {
                    case "ACCOUNT_LOCKED":
                        errorMessage = "Tài khoản của bạn đã bị khóa!";
                        break;
                    case "INCORRECT_PASSWORD":
                        errorMessage = "Tài khoản hoặc mật khẩu không đúng!";
                        break;
                    case "ERROR":
                        errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại!";
                        break;
                }
                request.setAttribute("loginError", errorMessage);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if (action.equals("res")) {
            // Xử lý hành động đăng ký
            String username = request.getParameter("username");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Kiểm tra mật khẩu tối thiểu 6 ký tự
            if (password == null || password.length() < 6) {
                request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            String hashedPassword = PasswordUtils.hashPassword(password);

            UserDAO userDAO = new UserDAO();
            Users user = new Users(username, hashedPassword,email,phone);

            // Kiểm tra nếu tên người dùng đã tồn tại
            if (userDAO.userExists(username)) {
                request.setAttribute("error", "Tên người dùng đã tồn tại.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Tạo mã OTP ngẫu nhiên
            String otp = String.format("%06d", new Random().nextInt(999999));

            // Thêm người dùng vào cơ sở dữ liệu
            if (userDAO.add(user)) {

                UserDAO.sendMail(email,"OTP xác thực tài khoản",otp);
                response.sendRedirect("verify.jsp?email=" + email); // fix lại địa chỉ xác nhận otp

//                // Tạo session và chuyển hướng tới trang đăng nhập
//                HttpSession session = request.getSession(true);
//                session.setAttribute("auth", user);  // Lưu thông tin người dùng vào session
//                response.sendRedirect("home.jsp");

            } else {
                request.setAttribute("error", "Có lỗi xảy ra trong quá trình đăng ký.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } else if (action.equals("logout")) {
            // Xử lý hành động đăng xuất
            HttpSession session = request.getSession(false);
            if(session != null) {
                Users user = (Users) session.getAttribute("auth");
                if(user != null) {
                    SessionManager.removeSession(user.getUsername(), session);
                }
                session.invalidate();
            }
            response.sendRedirect("home.jsp");
        }else if(action.equals("forgetPass")) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            // Khởi tạo đối tượng UserDAO để kiểm tra trong DB
            UserDAO userDAO = new UserDAO();

            // Kiểm tra xem username và email có tồn tại trong database không
            if(userDAO.isUserExist(username, email)) {
                // Nếu tồn tại, tiếp tục với việc phục hồi mật khẩu
                if(userDAO.passwordRecorvery(username, email)) {
                    request.setAttribute("errorMessage", "Lấy mật khẩu thành công");
                    request.setAttribute("showForgotPasswordForm", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    // Nếu không thể phục hồi mật khẩu (có thể là lỗi khác), hiển thị thông báo
                    request.setAttribute("errorMessage", "Không thể phục hồi mật khẩu.");
                    request.setAttribute("showForgotPasswordForm", true);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                // Nếu không tìm thấy username và email trong database
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc email không đúng.");
                request.setAttribute("showForgotPasswordForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

    }
}
