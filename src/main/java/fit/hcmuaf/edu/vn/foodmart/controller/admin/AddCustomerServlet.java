package fit.hcmuaf.edu.vn.foodmart.controller.admin;

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

        // Kiểm tra checkbox có được chọn không
        boolean isStatusChecked = request.getParameter("status") != null;
        boolean isRoleChecked = request.getParameter("role") != null;

        // Xử lý giá trị ID
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        UserAdminDAO userDao = new UserAdminDAO();

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
            newUser.setUserStatus("Đang hoạt động"); // Mặc định trạng thái
            newUser.setRole("User"); // Mặc định User
            boolean isAdded = userDao.addUser(newUser);
            String message = isAdded ? "Thêm khách hàng thành công" : "Lỗi: Thêm khách hàng thất bại";
            response.sendRedirect("admin.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
        } else if ("edit".equals(action)) {
            // Chỉnh sửa khách hàng
            Users existingUser = userDao.getUserById(id);
            if (existingUser != null) {
                System.out.println("User found for editing: " + existingUser.getUsername());

                // Cập nhật số điện thoại nếu có thay đổi
                existingUser.setPhone(phone);

                // Cập nhật mật khẩu nếu có nhập mới
                if (password != null && !password.isEmpty()) {
                    existingUser.setPassword(PasswordUtils.hashPassword(password));
                }

                // **Chỉ cập nhật trạng thái nếu checkbox được chọn**
                if (isStatusChecked) {
                    existingUser.setUserStatus(existingUser.getUserStatus().equals("Đang hoạt động") ? "Bị khóa" : "Đang hoạt động");
                    System.out.println("Trạng thái cập nhật thành: " + existingUser.getUserStatus());
                }

                // **Chỉ cập nhật vai trò nếu checkbox được chọn**
                if (isRoleChecked) {
                    existingUser.setRole(existingUser.getRole().equals("User") ? "Admin" : "User");
                    System.out.println("Vai trò cập nhật thành: " + existingUser.getRole());
                }

                boolean isUpdated = userDao.updateUser(existingUser);
                String message = isUpdated ? "Cập nhật khách hàng thành công" : "Lỗi: Cập nhật khách hàng thất bại";
                response.sendRedirect("admin.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
            } else {
                System.out.println("User not found for editing.");
                String message = URLEncoder.encode("Lỗi: Không tìm thấy khách hàng để chỉnh sửa", StandardCharsets.UTF_8.toString());
                response.sendRedirect("admin.jsp?message=" + message);
            }
        }
    }
}
