package fit.hcmuaf.edu.vn.foodmart.controller.cart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Update", value = "/update-cart")
public class Update extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int pid = Integer.parseInt(request.getParameter("pid"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            if (cart != null) {
                boolean updated = cart.update(pid, quantity);

                if (updated) {
                    session.setAttribute("cart", cart);
                    session.setAttribute("totalAmount", cart.getTotalAmount());
                    session.setAttribute("totalQuantity", cart.getTotalQuantity());
                    session.removeAttribute("discountedTotal");
                    // Trả về JSON response
                    PrintWriter out = response.getWriter();

                    // Lấy thông tin sản phẩm vừa cập nhật
                    CartProduct updatedProduct = cart.getlist().stream()
                            .filter(p -> p.getId() == pid)
                            .findFirst()
                            .orElse(null);

                    if (updatedProduct != null) {
                        // Tạo JSON object cho response
                        JsonObject responseJson = new JsonObject();
                        responseJson.addProperty("success", true);
                        responseJson.addProperty("totalAmount", cart.getTotalAmount());
                        responseJson.addProperty("totalQuantity", cart.getTotalQuantity());
                        responseJson.add("updatedProduct", new Gson().toJsonTree(updatedProduct)); // Thêm updatedProduct vào JSON object

                        // Trả về JSON response
                        out.print(responseJson.toString());
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"success\": false, \"message\": \"Cập nhật thất bại!\"}");
                }
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