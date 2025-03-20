package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy ID khách hàng từ request
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;

        UserAdminDAO userDao = new UserAdminDAO();
        Users customer = userDao.getUserById(id); // Lấy thông tin khách hàng từ database

        if (customer != null) {
            request.setAttribute("customer", customer); // Truyền dữ liệu vào request
            request.getRequestDispatcher("edit-customer.jsp").forward(request, response);
        } else {
            response.sendRedirect("admin.jsp?message=Lỗi: Không tìm thấy khách hàng");
        }
    }
}
