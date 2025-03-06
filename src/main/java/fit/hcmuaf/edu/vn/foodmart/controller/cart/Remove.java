package fit.hcmuaf.edu.vn.foodmart.controller.cart;

import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Remove", value = "/remove-cart")
public class Remove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int productId = Integer.parseInt(request.getParameter("pid"));

            HttpSession session = request.getSession(true);
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart != null) {
                cart.remove(productId);

                // Cập nhật giỏ hàng trong session
                session.setAttribute("cart", cart);
                session.setAttribute("totalAmount", cart.getTotalAmount());
                session.setAttribute("totalQuantity", cart.getTotalQuantity());
                session.removeAttribute("discountedTotal");
                // Trả về JSON response
                PrintWriter out = response.getWriter();
                out.print("{\"success\": true, \"totalAmount\": " + cart.getTotalAmount() +
                        ", \"totalQuantity\": " + cart.getTotalQuantity() + "}");
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Giỏ hàng không tồn tại!\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Tham số không hợp lệ!\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}