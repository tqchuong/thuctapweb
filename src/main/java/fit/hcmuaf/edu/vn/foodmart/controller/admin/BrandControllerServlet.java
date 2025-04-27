package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import com.google.gson.Gson;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.BrandAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Brands;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/brandController")
public class BrandControllerServlet extends HttpServlet {

    private final  BrandAdminDAO dao = new BrandAdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Brands> brands = dao.getAllBrands();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(brands));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action1 = req.getParameter("action1"); // add | edit
        String brandName = req.getParameter("brandName");
        String brandIdParam = req.getParameter("brandId");

        if (brandName == null || brandName.trim().isEmpty()) {
            String msg = URLEncoder.encode("Tên thương hiệu không được để trống!", StandardCharsets.UTF_8);
            resp.sendRedirect("admin.jsp?message=" + msg);
            return;
        }

        Brands brand = new Brands();
        brand.setName(brandName.trim());

        if ("add".equals(action1)) {
            boolean isAdded = dao.addBrand(brand);
            String msg = isAdded ? "Thêm thương hiệu thành công" : "Lỗi: Không thể thêm thương hiệu";
            resp.sendRedirect("admin.jsp?message=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
        } else if ("edit".equals(action1)) {
            if (brandIdParam == null || brandIdParam.isEmpty()) {
                String msg = URLEncoder.encode("Thiếu ID thương hiệu khi chỉnh sửa!", StandardCharsets.UTF_8);
                resp.sendRedirect("admin.jsp?message=" + msg);
                return;
            }

            try {
                int brandId = Integer.parseInt(brandIdParam);
                brand.setId(brandId);

                boolean isUpdated = dao.updateBrand(brand);
                String msg = isUpdated ? "Cập nhật thương hiệu thành công" : "Lỗi: Không thể cập nhật thương hiệu";
                resp.sendRedirect("admin.jsp?message=" + URLEncoder.encode(msg, StandardCharsets.UTF_8));
            } catch (NumberFormatException e) {
                String msg = URLEncoder.encode("ID thương hiệu không hợp lệ!", StandardCharsets.UTF_8);
                resp.sendRedirect("admin.jsp?message=" + msg);
            }
        } else {
            String msg = URLEncoder.encode("Hành động không hợp lệ!", StandardCharsets.UTF_8);
            resp.sendRedirect("admin.jsp?message=" + msg);
        }
    }

}
