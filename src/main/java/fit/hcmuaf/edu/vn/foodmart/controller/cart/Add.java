package fit.hcmuaf.edu.vn.foodmart.controller.cart;

import com.google.gson.Gson;
import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.service.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ProductService ps = new ProductService();
        int productId = Integer.parseInt(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Products product = ps.getProductDetailsById(productId);

        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        boolean success = false;
        if (product != null) {
            success = cart.add(product, quantity);
            session.setAttribute("cart", cart);
            session.setAttribute("totalAmount", cart.getTotalAmount());
            session.removeAttribute("discountedTotal");

        }

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();

        // Gửi JSON phản hồi
        out.write(gson.toJson(new ApiResponse(success, cart)));
        out.flush();
    }

    class ApiResponse {
        boolean success;
        Cart cart;

        ApiResponse(boolean success, Cart cart) {
            this.success = success;
            this.cart = cart;
        }
    }


        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


