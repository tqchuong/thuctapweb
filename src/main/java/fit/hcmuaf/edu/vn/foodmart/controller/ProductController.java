package fit.hcmuaf.edu.vn.foodmart.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "products", value = "/products")
public class ProductController extends HttpServlet {
    private final Jedis jedis = new Jedis("localhost", 6379); // Kết nối tới Redis
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cacheKey = "products_list";
        String cachedData = jedis.get(cacheKey);
        List<Products> products;

        if (cachedData != null) {
            // Lấy dữ liệu từ Redis
            products = gson.fromJson(cachedData, new TypeToken<List<Products>>(){}.getType());
        } else {
            // Truy vấn CSDL và lưu vào Redis
            ProductService productService = new ProductService();
            products = productService.getAllProducts();
            jedis.setex(cacheKey, 3600, gson.toJson(products)); // Cache 1 giờ
        }

        // Đặt danh sách sản phẩm vào request scope
        request.setAttribute("products", products);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    // Phương thức để làm mới cache khi dữ liệu thay đổi
    public static void invalidateCache(Jedis jedis) {
        jedis.del("products_list");
    }

    @Override
    public void destroy() {
        jedis.close(); // Đóng kết nối Redis khi Servlet bị hủy
    }
}