package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import fit.hcmuaf.edu.vn.foodmart.utils.TokenGenerator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.google.gson.internal.bind.TypeAdapters.UUID;


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

            // Kiểm tra đăng nhập
            UserDAO userDAO = new UserDAO();
            String loginStatus = userDAO.checkLogin(username, password);

            if (loginStatus.equals("LOGIN_SUCCESS")) {
                // Nếu đăng nhập thành công, lấy thông tin người dùng từ cơ sở dữ liệu
                Users user = userDAO.getUserByUsername(username);

                // Tạo session và lưu thông tin người dùng
                HttpSession session = request.getSession(true);
                session.setAttribute("auth", user);


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

            if (userDAO.userExists(username) || userDAO.emailExists(email)) {
                request.setAttribute("error", "Tên người dùng hoặc email đã tồn tại.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
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
                String verifyLink = "http://localhost:8080/project/verify?token=" + encodedToken;
                String subject = "Xác thực tài khoản của bạn";
                String message = "Nhấn vào link sau để xác thực tài khoản: " + verifyLink + "\nLink có hiệu lực trong 24 giờ.";
                new Thread(() -> UserDAO.sendMail(email, subject, message)).start();


                response.sendRedirect("verify.jsp?email=" + email);
            } else {
                request.setAttribute("error", "Có lỗi xảy ra trong quá trình đăng ký.");
                request.setAttribute("showRegisterForm", true);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }



        } else if (action.equals("logout")) {
            // Xử lý hành động đăng xuất
            HttpSession session = request.getSession();
            session.invalidate();  // Hủy session khi đăng xuất
            response.sendRedirect("home.jsp");
        } else if(action.equals("forgetPass")) {
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
