package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        String action = request.getParameter("action"); // "add" hoặc "edit"
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String statusParam = request.getParameter("status");

        // Xử lý giá trị id để tránh lỗi NumberFormatException
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

// Xử lý trạng thái
        String userStatus = (statusParam != null && statusParam.equals("1")) ? "Đang hoạt động" : "Bị khóa";
        UserAdminDAO userDao = new UserAdminDAO();

        // Log kiểm tra
        System.out.println("Action: " + action);
        System.out.println("ID: " + id);
        System.out.println("Fullname: " + fullname);
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);
        System.out.println("Status: " + userStatus);


        if ("add".equals(action)) {
            if (userDao.userExists(fullname)) {
                String message = URLEncoder.encode("Lỗi: Tên người dùng đã tồn tại", StandardCharsets.UTF_8.toString());
                response.sendRedirect("admin.jsp?message=" + message);
                return;
            }


            // Thêm khách hàng mới
            Users newUser = new Users();
            newUser.setUsername(fullname);
            newUser.setPhone(phone);
            newUser.setPassword(PasswordUtils.hashPassword(password)); // Hash password
            newUser.setUserStatus("Hoạt động"); // Mặc định trạng thái
            newUser.setRole("User"); // Vai trò mặc định là User
            boolean isAdded = userDao.addUser(newUser);
            String message = isAdded ? "Thêm khách hàng thành công" : "Lỗi: Thêm khách hàng thất bại";
            response.sendRedirect("admin.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
        } else if ("edit".equals(action)) {
            System.out.println("Handling edit action...");

            // Chỉnh sửa khách hàng
            Users existingUser = userDao.getUserById(id); // Lấy thông tin user theo ID
            if (existingUser != null) {
                System.out.println("User found for editing: " + existingUser.getUsername());

                // Chỉ thay đổi số điện thoại, mật khẩu, trạng thái
                existingUser.setPhone(phone);
                if (password != null && !password.isEmpty()) {
                    existingUser.setPassword(PasswordUtils.hashPassword(password)); // Hash password nếu có thay đổi
                }

                // Chuyển đổi trạng thái
                String currentStatus = existingUser.getUserStatus();
                if ("Đang hoạt động".equals(currentStatus)) {
                    existingUser.setUserStatus("Bị khóa");
                } else if ("Bị khóa".equals(currentStatus)) {
                    existingUser.setUserStatus("Đang hoạt động");
                }

                boolean isUpdated = userDao.updateUser(existingUser);
                String message = isUpdated ? "Cập nhật khách hàng thành công" : "Lỗi: Cập nhật khách hàng thất bại";
                response.sendRedirect("admin.jsp#");
            } else {
                System.out.println("User not found for editing.");
                String message = URLEncoder.encode("Lỗi: Không tìm thấy khách hàng để chỉnh sửa", StandardCharsets.UTF_8.toString());
                response.sendRedirect("admin.jsp#");
            }
            response.sendRedirect("admin.jsp#");
        }

        response.sendRedirect("admin.jsp#");
    }
}