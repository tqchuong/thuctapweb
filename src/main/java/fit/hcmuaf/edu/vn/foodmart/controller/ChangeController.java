package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.UserDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ChangeController", value = "/change")
public class ChangeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");



        if(action == null) {
            System.out.println("Không thực hiện gì hết!");
        } else if (action.equals("password")) {
            String username = request.getParameter("username");
            String passwordCur = request.getParameter("passwordCur");
            String passwordNew = request.getParameter("passwordNew");
            String passwordConfirm = request.getParameter("passwordConfirm");

            UserDAO userDAO = new UserDAO();
            Users currentUser = userDAO.getUserByUsername(username);

            if (username == null || passwordCur == null || passwordCur.isEmpty() || passwordNew == null || passwordNew.isEmpty() || passwordConfirm == null || passwordConfirm.isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }

            //kiểm tra tên tài khoản
            Users userFromSession = (Users) request.getSession().getAttribute("auth");
            String usernameFromSession = userFromSession.getUsername();
            if (username == null || !username.equals(usernameFromSession)) {
                request.setAttribute("error", "Tên tài khoản không đúng");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return; // Dừng lại nếu không đúng
            }


            String hashedPassword = currentUser.getPassword(); // Lấy mật khẩu đã mã hóa từ DB
            if (hashedPassword == null || !PasswordUtils.verifyPassword(passwordCur, hashedPassword)) {
                request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }



            // Kiểm tra mật khẩu tối thiểu 6 ký tự
            if (passwordNew == null || passwordNew.length() < 6) {
                request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }
            // Kiểm tra mật khẩu mới khớp với xác nhận mật khẩu
            if(passwordConfirm == null || !passwordConfirm.equals(passwordNew)) {
                request.setAttribute("error", "Mật khẩu không giống nhau.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }

            String hashPassword = PasswordUtils.hashPassword(passwordNew);

            // Gọi phương thức changePassword từ UserDAO
            boolean changePassword = userDAO.changePassword(username, passwordCur, hashPassword);

            if (changePassword) {
                request.setAttribute("error", "Đổi mật khẩu thành công");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Đổi mật khẩu thất bại. Vui lòng kiểm tra lại thông tin.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            }


        } else if (action.equals("infor")) {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            UserDAO userDAO = new UserDAO();
            Users userFromSession = (Users) request.getSession().getAttribute("auth");



            String usernameFromSession = userFromSession.getUsername();
            System.out.println(usernameFromSession);
            // Kiểm tra các trường trống
            if (fullName == null || fullName.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    address == null || address.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng điền đầy đủ tất cả thông tin.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }

            // Kiểm tra định dạng số điện thoại
            if (!phone.matches("\\d{10}")) {
                request.setAttribute("error", "Số điện thoại không hợp lệ.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
                return;
            }



            // Thực hiện cập nhật thông tin
            boolean changeInfor = userDAO.changeInfor(usernameFromSession, fullName, phone, email, address);

            if (changeInfor) {
                // Cập nhật thông tin trong session
                userFromSession.setFullName(fullName);
                userFromSession.setPhone(phone);
                userFromSession.setEmail(email);
                userFromSession.setAddress(address);
                request.getSession().setAttribute("auth", userFromSession);

                request.setAttribute("error", "Đổi thông tin thành công.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Đổi thông tin thất bại. Vui lòng kiểm tra lại thông tin.");
                request.getRequestDispatcher("changeInfor.jsp").forward(request, response);
            }
        }

    }
}
