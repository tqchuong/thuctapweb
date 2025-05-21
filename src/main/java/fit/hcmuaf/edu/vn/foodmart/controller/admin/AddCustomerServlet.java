package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import fit.hcmuaf.edu.vn.foodmart.utils.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        // Kiểm tra checkbox có được chọn không
        boolean isStatusChecked = request.getParameter("status") != null;
        boolean isAdminRoleChecked = request.getParameter("role") != null;
        boolean isUserRoleChecked = request.getParameter("role-user") != null;
        boolean isWarehouseRoleChecked = request.getParameter("role-warehouse") != null;
        boolean isOrderRoleChecked = request.getParameter("role-order") != null;

        // Xử lý giá trị ID
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        String currentUsername = (String) request.getSession().getAttribute("username");

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
            newUser.setEmail(mail);
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

                // Chỉ cập nhật trạng thái nếu checkbox được chọn
                if (isStatusChecked) {
                    existingUser.setUserStatus(existingUser.getUserStatus().equals("Đang hoạt động") ? "Bị khóa" : "Đang hoạt động");
                    System.out.println("Trạng thái cập nhật thành: " + existingUser.getUserStatus());
                }

                // Lưu vai trò cũ để kiểm tra thay đổi
                String oldRole = existingUser.getRole();
                
                // Xác định vai trò mới dựa trên các checkbox
                if (isAdminRoleChecked) {
                    existingUser.setRole("Admin"); // Set role to Admin
                    System.out.println("Vai trò cập nhật thành: Admin");
                } else if (isWarehouseRoleChecked) {
                    existingUser.setRole("WarehouseManager"); // Set role to WarehouseManager
                    System.out.println("Vai trò cập nhật thành: WarehouseManager");
                } else if (isOrderRoleChecked) {
                    existingUser.setRole("OrderConfirmator"); // Set role to OrderConfirmator
                    System.out.println("Vai trò cập nhật thành: OrderConfirmator");
                } else if (isUserRoleChecked) {
                    existingUser.setRole("User"); // Set role to User
                    System.out.println("Vai trò cập nhật thành: User");
                } else {
                    // Mặc định là User nếu không có role nào được chọn
                    existingUser.setRole("User");
                    System.out.println("Vai trò mặc định: User");
                }
                
                // Kiểm tra nếu vai trò thay đổi từ quyền cao hơn xuống thành User
                boolean isRoleDemoted = (oldRole.equals("Admin") || oldRole.equals("WarehouseManager") || oldRole.equals("OrderConfirmator")) 
                                     && existingUser.getRole().equals("User");
                
                // Hoặc chuyển từ bất kỳ vai trò nào (trừ User) xuống thành vai trò khác
                boolean isRoleChanged = !oldRole.equals(existingUser.getRole()) && !oldRole.equals("User");
                
                // Nếu vai trò bị hạ cấp hoặc thay đổi
                if (isRoleDemoted || isRoleChanged) {
                    // Cập nhật vai trò vào cơ sở dữ liệu trước khi đăng xuất
                    boolean isUpdated = userDao.updateUser(existingUser);
                    String message = isUpdated ? "Cập nhật vai trò thành " + existingUser.getRole() + " thành công. Người dùng sẽ bị đăng xuất." : "Lỗi: Không thể cập nhật vai trò.";

                    // Đặt một cookie toàn cục để báo cho các trình duyệt khác biết về sự thay đổi quyền
                    Cookie logoutCookie = new Cookie("roleChanged", "true");
                    logoutCookie.setMaxAge(300);  // Thời gian hết hạn, ví dụ: 5 phút
                    logoutCookie.setPath("/");  // Đảm bảo cookie có thể truy cập từ tất cả các đường dẫn
                    response.addCookie(logoutCookie);

                    // Chỉ vô hiệu hóa phiên của người dùng bị thay đổi quyền, không phải admin đang thực hiện
                    if (!existingUser.getUsername().equals(currentUsername)) {
                        // Xóa tất cả session của người dùng bị hạ quyền
                        SessionManager.invalidateAllSessions(existingUser.getUsername());
                        
                        // Chuyển hướng về trang admin với thông báo
                        response.sendRedirect("admin.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
                    } else {
                        // Đây là admin tự hạ quyền chính mình xuống User
                        // Đăng xuất người dùng hiện tại và xóa session, cookies
                        request.getSession().invalidate();  // Xóa session để đăng xuất người dùng

                        // Xóa tất cả cookies
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                cookie.setMaxAge(0);  // Set cookie age to 0 to delete it
                                cookie.setPath("/");   // Ensure the cookie is deleted for all paths
                                response.addCookie(cookie);  // Add the cookie to the response to delete it
                            }
                        }
                        // Chuyển hướng đến trang login với thông báo
                        response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
                    }
                    return; // Dừng lại, không tiếp tục xử lý
                } else {
                    // Nếu không phải là thay đổi vai trò quan trọng, thực hiện cập nhật bình thường
                    boolean isUpdated = userDao.updateUser(existingUser);
                    String message = isUpdated ? "Cập nhật khách hàng thành công" : "Lỗi: Cập nhật khách hàng thất bại";
                    response.sendRedirect("admin.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString()));
                }
            } else {
                System.out.println("User not found for editing.");
                String message = URLEncoder.encode("Lỗi: Không tìm thấy khách hàng để chỉnh sửa", StandardCharsets.UTF_8.toString());
                response.sendRedirect("admin.jsp?message=" + message);
            }
        }
    }
}