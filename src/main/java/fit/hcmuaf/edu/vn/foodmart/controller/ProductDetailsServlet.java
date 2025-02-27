package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.service.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailsServlet", value = "/productDetail") // Sửa value thành /productDetail
public class ProductDetailsServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo ProductService trong init() để tối ưu, thay vì tạo mới mỗi lần request
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("id");

        if (productIdStr != null && !productIdStr.isEmpty()) {
            try {
                int productId = Integer.parseInt(productIdStr);
                // Cập nhật lượt xem
                ProductDAO productDAO = new ProductDAO();
                boolean updated = productDAO.updateView(productId);

                // Lấy thông tin sản phẩm từ ProductService
                Products productDetails = productService.getProductDetailsById(productId);

                if (productDetails != null) {
                    // Tính toán các giá trị
                    double averageRating = productService.getAverageRating(productId);
                    int reviewCount = (productDetails.getReviews() != null) ? productDetails.getReviews().size() : 0;
                    int viewCount = productDetails.getProductViews();
                    // Lấy danh sách sản phẩm liên quan
                    List<Products> randomRelatedProducts = productService.getRandomRelatedProducts(productDetails);
                    request.setAttribute("randomRelatedProducts", randomRelatedProducts);
                    // Đặt các attribute vào request
                    request.setAttribute("product", productDetails);

                    request.setAttribute("averageRating", averageRating);
                    request.setAttribute("reviewCount", reviewCount);
                    request.setAttribute("viewCount", viewCount);

                    // Forward đến JSP
                    request.getRequestDispatcher("productdetail.jsp").forward(request, response);

                } else {
                    // Xử lý trường hợp không tìm thấy sản phẩm
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (NumberFormatException e) {
                // Xử lý lỗi khi productId không phải là số
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            }
        } else {
            // Xử lý lỗi khi không có productId
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý POST request nếu cần
    }
}