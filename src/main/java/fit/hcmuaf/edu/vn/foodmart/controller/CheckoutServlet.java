package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import fit.hcmuaf.edu.vn.foodmart.dao.OrderDao;
import fit.hcmuaf.edu.vn.foodmart.dao.OrderDetailDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.PaymentDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.ShippingDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("auth");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null || cart == null || cart.getlist().isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty_cart_or_user");
            return;
        }

        try {
            double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
            String shippingMethod = request.getParameter("shippingMethod");
            String shippingAddress = request.getParameter("recipientAddress");
            String orderNote = request.getParameter("orderNote");
            String paymentMethod = request.getParameter("paymentType");
            Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));
            String deliveryTime = request.getParameter("specificDeliveryTime");
            String receiverName = request.getParameter("recipientName");
            String receiverPhone = request.getParameter("recipientPhone");
            String paymentStatus = request.getParameter("paymentStatus");
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

            // ✅ Kiểm tra nếu chọn VNPay thì chuyển hướng đến VNPay
            if ("VNPAY".equals(paymentMethod)) {
                session.setAttribute("checkout_data", request.getParameterMap()); // Lưu dữ liệu đặt hàng
                response.sendRedirect("vnpay_pay?amount=" + totalAmount);
                return;
            }

            // ✅ Nếu không chọn VNPay, xử lý thanh toán bình thường
            Jdbi jdbi = DBConnect.getJdbi();
            jdbi.useTransaction(handle -> {
                OrderDao orderDAO = new OrderDao(handle);
                int orderId = orderDAO.createOrder(user, totalAmount, shippingMethod, deliveryDate,
                        deliveryTime, paymentMethod, orderNote, receiverName, receiverPhone, shippingAddress);

                OrderDetailDAO orderDetailDAO = new OrderDetailDAO(handle.getJdbi());
                for (CartProduct item : cart.getlist()) {
                    orderDetailDAO.addOrderDetail(orderId, item);
                }

                ShippingDAO shippingDAO = new ShippingDAO(handle.getJdbi());
                shippingDAO.addShipping(orderId, shippingFee);

                PaymentDAO paymentDAO = new PaymentDAO();
                paymentDAO.addPayment(orderId, paymentStatus);
            });

            session.removeAttribute("cart");
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}
