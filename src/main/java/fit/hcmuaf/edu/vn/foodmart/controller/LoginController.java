package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.LoggerUtil;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import fit.hcmuaf.edu.vn.foodmart.utils.TokenGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private static final Jdbi jdbi = DBConnect.getJdbi();
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
            String userCaptcha  = request.getParameter("captcha");

            // Kiểm tra đăng nhập
            UserDAO userDAO = new UserDAO();
            String loginStatus = userDAO.checkLogin(username, password);
            String email = userDAO.getEmailByUsername(username);

            // Lấy mã CAPTCHA từ session
            HttpSession session = request.getSession(true);
            String correctCaptcha = (String) session.getAttribute("captcha");

            if (!userCaptcha.equals(correctCaptcha)) {
                request.setAttribute("loginError", "Mã CAPTCHA không đúng!");
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi CAPTCHA sai
                LoggerUtil.log(request, "Login Failed", "ERROR", "login.jsp", "CAPTCHA", "", "Incorrect CAPTCHA");

            }else if (loginStatus.equals("LOGIN_SUCCESS")) {
                // Nếu đăng nhập thành công, lấy thông tin người dùng từ cơ sở dữ liệu
                Users user = userDAO.getUserByUsername(username);

                // Tạo session và lưu thông tin người dùng
                //HttpSession session = request.getSession(true);
                session.setAttribute("auth", user);
                SessionManager.addSession(user.getUsername(), session);

                // Ghi log khi đăng nhập thành công
                LoggerUtil.log(request, "Login Success", "INFO", "login.jsp", "User Login", "", "");

                // Chuyển hướng đến trang chủ sau khi đăng nhập thành công
                response.sendRedirect("home.jsp");
            } else {
                // Nếu đăng nhập không thành công, thông báo lỗi
                String errorMessage = "";
                String level_log ="";
                switch (loginStatus) {
                    case "ACCOUNT_DELETED":
                        errorMessage = "Tài khoản của bạn đã bị xóa!";
                        break;
                    case "ACCOUNT_LOCKED":
                        errorMessage = "Tài khoản của bạn đã bị khóa!";
                        level_log = "DANGER";
                        break;
                    case "INCORRECT_PASSWORD":
                        errorMessage = "Tài khoản hoặc mật khẩu không đúng!";
                        break;
                    case "ACCOUNT_TEMP_LOCKED":
                        errorMessage = "Tài khoản bị khóa tạm thời do đăng nhập sai quá 5 lần.\n Vui lòng thử lại sau 5 phút!";
                        level_log = "DANGER";
                        break;
                    case "ERROR":
                        errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại!";
                        break;
                    case "VERIFY_ACCOUNT":
                        response.sendRedirect("verify.jsp?email=" + email);
                        return;
                }
                request.setAttribute("loginError", errorMessage);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi đăng nhập thất bại
                LoggerUtil.log(request, "Login Failed", level_log, "login.jsp", "User Login", "", errorMessage);

            }
        } else if (action.equals("res")) {
            // Xử lý hành động đăng ký
            String username = request.getParameter("username");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("passwordConfirm");

            // Kiểm tra mật khẩu tối thiểu 6 ký tự
            if (password == null || password.length() < 6) {
                request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi mật khẩu quá ngắn
                LoggerUtil.log(request, "Registration Failed", "ERROR", "login.jsp", "User Registration", "", "Password too short");

                return;
            }
            // Kiểm tra mật khẩu mới khớp với xác nhận mật khẩu
            if(passwordConfirm == null || !passwordConfirm.equals(password)) {
                request.setAttribute("error", "Mật khẩu không giống nhau.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi mật khẩu xác nhận không khớp
                LoggerUtil.log(request, "Registration Failed", "ERROR", "login.jsp", "User Registration", "", "Password mismatch");

                return;
            }
            String hashedPassword = PasswordUtils.hashPassword(password);

            UserDAO userDAO = new UserDAO();
            Users user = new Users(username, hashedPassword,email,phone);

            if (userDAO.userExists(username) || userDAO.emailExists(email)) {
                request.setAttribute("error", "Tên người dùng hoặc email đã tồn tại.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi tên đăng nhập hoặc email đã tồn tại
                LoggerUtil.log(request, "Registration Failed", "ERROR", "login.jsp", "User Registration", "", "Username or email already exists");

                return;
            }



            // Tạo token xác thực
            String token = TokenGenerator.generateToken(username);
            Timestamp expiry = Timestamp.from(Instant.now().plusSeconds(24 * 60 * 60)); // Hết hạn sau 24h
            user.setVerification_token(token);
            user.setToken_expiry(expiry);


            if (userDAO.add(user)) {
                // Gửi email xác thực
                String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
                String verifyLink = "http://localhost:8080/project/verify?token=" + encodedToken ;
                String subject = "Xác thực tài khoản FoodMart";
                String message = "Xin chào " + user.getUsername() + ",\n\n"
                        + "Bạn đã yêu cầu gửi lại email xác thực. Vui lòng bấm vào liên kết dưới đây để xác thực tài khoản của bạn:\n"
                        + verifyLink + "\n\n"
                        + "\nLink có hiệu lực trong 24 giờ."+ "\n\n"
                        + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.\n\n"
                        + "Trân trọng,\nFoodMart Team";
                new Thread(() -> UserDAO.sendMail(email, subject, message)).start();


                response.sendRedirect("verify.jsp?email=" + email);

                // Ghi log khi đăng ký thành công
                LoggerUtil.log(request, "Registration Success", "INFO", "login.jsp", "User Registration", "", "");

            } else {
                request.setAttribute("error", "Có lỗi xảy ra trong quá trình đăng ký.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                // Ghi log khi đăng ký thất bại
                LoggerUtil.log(request, "Registration Failed", "ERROR", "login.jsp", "User Registration", "", "Registration error");

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

            // Ghi log khi đăng xuất
            LoggerUtil.log(request, "Logout", "INFO", "home.jsp", "User Logout", "", "");
        }else if(action.equals("forgetPass")) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");

            // Khởi tạo đối tượng UserDAO để kiểm tra trong DB
            UserDAO userDAO = new UserDAO();
            Users user = new Users(username, email);



            // Kiểm tra xem username và email có tồn tại trong database không
            if(userDAO.isUserExist(username, email)) {
                // Tạo token xác thực
                String token = TokenGenerator.generateToken(username);
                Timestamp expiry = Timestamp.from(Instant.now().plusSeconds(24 * 60 * 60)); // Hết hạn sau 24h
                user.setVerification_token(token);
                user.setToken_expiry(expiry);


                userDAO.updateVerificationToken(email, token,expiry);

                // Tạo link xác thực
                String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8.toString());
                String verifyLink = "http://localhost:8080/project/passwordRecorvery?token=" + encodedToken;
                String subject = "Lấy lại mật khẩu tài khoản FoodMart";
                String message = "Xin chào " + user.getUsername() + ",\n\n"
                        + "Bạn đã yêu cầu gửi lấy lại mật khẩu tài khoản. Vui lòng bấm vào liên kết dưới đây để lấy lại mật khẩu tài khoản của bạn:\n"
                        + verifyLink + "\n\n"
                        + "\nLink có hiệu lực trong 24 giờ."+ "\n\n"
                        + "Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.\n\n"
                        + "Trân trọng,\nFoodMart Team";
                new Thread(() -> UserDAO.sendMail(email, subject, message)).start();

                // Ghi log khi gửi lại mật khẩu
                LoggerUtil.log(request, "Password Recovery", "INFO", "login.jsp", "Password Recovery", "", "");

                // Chuyển hướng với thông báo thành công
                response.sendRedirect("login.jsp?message=" + URLEncoder.encode("Link xác thực đã được gửi đến email của bạn!", StandardCharsets.UTF_8.toString()));


            } else {
                // Nếu không tìm thấy username và email trong database
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc email không đúng.");
                request.setAttribute("showForgotPasswordForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }


    }



}
