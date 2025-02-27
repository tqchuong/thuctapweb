package fit.hcmuaf.edu.vn.foodmart.controller.cart;

import com.google.gson.Gson;
import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ShowCart", value = "/ShowCart")
public class Show extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }

        Gson gson = new Gson();
        String jsonCart = gson.toJson(cart);
        response.getWriter().write(jsonCart);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}


