package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import com.google.gson.Gson;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.ProductAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/categoriesController")
public class CategoriesControllerServlet extends HttpServlet {

    private ProductAdminDAO dao = new ProductAdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> category = dao.getAllCategories();
        req.setAttribute("categoryJSON", new Gson().toJson(category));
        req.getRequestDispatcher("/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action2 = req.getParameter("action2"); // add | edit
        String categoriesName = req.getParameter("categoriesName");
        String categoriesIdParam = req.getParameter("categoriesId");

        if (categoriesName == null || categoriesName.trim().isEmpty()) {
            String msg = URLEncoder.encode("Tên danh mục không được để trống!", StandardCharsets.UTF_8);
            resp.sendRedirect("admin.jsp?message=" + msg);
            return;
        }

        Category category = new Category();
        category.setCategoryName(categoriesName.trim());

        if ("add".equals(action2)) {
            boolean isAdded = dao.addCategories(category);
            String msg = isAdded ? "Thêm danh mục thành công" : "Lỗi: Không thể thêm danh mục";
            resp.sendRedirect("admin.jsp?message=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
        } else if ("edit".equals(action2)) {
            if (categoriesIdParam == null || categoriesIdParam.isEmpty()) {
                String msg = URLEncoder.encode("Thiếu ID danh mục khi chỉnh sửa!", StandardCharsets.UTF_8);
                resp.sendRedirect("admin.jsp?message=" + msg);
                return;
            }

            try {
                int categoriesId = Integer.parseInt(categoriesIdParam);
                category.setCategoryID(categoriesId);

                boolean isUpdated = dao.updateCategories(category);
                String msg = isUpdated ? "Cập nhật danh mục thành công" : "Lỗi: Không thể cập nhật danh mục";
                resp.sendRedirect("admin.jsp?message=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
            } catch (NumberFormatException e) {
                String msg = URLEncoder.encode("ID danh mục không hợp lệ!", StandardCharsets.UTF_8);
                resp.sendRedirect("admin.jsp?message=" + msg);
            }
        } else {
            String msg = URLEncoder.encode("Hành động không hợp lệ!", StandardCharsets.UTF_8);
            resp.sendRedirect("admin.jsp?message=" + msg);
        }
    }
}
