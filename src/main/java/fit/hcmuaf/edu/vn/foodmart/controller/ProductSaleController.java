package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet(name ="flash-sale",value ="/flash-sale")
public class ProductSaleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();



        // Lấy danh sách sản phẩm Flash Sale
        List<Products> flashSaleProducts = productService.getFlashSaleProducts();

        // Đặt danh sách sản phẩm vào request scope
        request.setAttribute("flashSaleProducts", flashSaleProducts);

        // Chuyển tiếp đến trang flash-sale.jsp
        request.getRequestDispatcher("flash-sale.jsp").forward(request, response);



    }
}
