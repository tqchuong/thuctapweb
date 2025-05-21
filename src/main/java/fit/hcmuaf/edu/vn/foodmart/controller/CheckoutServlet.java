package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import fit.hcmuaf.edu.vn.foodmart.dao.*;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.model.Coupon;

import jakarta.servlet.RequestDispatcher;
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
        final Users user = (Users) session.getAttribute("auth");
        final Cart cart = (Cart) session.getAttribute("cart");

        if (user == null || cart == null || cart.getlist().isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty_cart_or_user");
            return;
        }

        try {
            final String shippingMethod = request.getParameter("shippingMethod");
            final String shippingAddress = request.getParameter("recipientAddress");
            final String orderNote = request.getParameter("orderNote");
            final String paymentMethod = request.getParameter("paymentType");
            final Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));
            final String deliveryTime = request.getParameter("specificDeliveryTime");
            final String receiverName = request.getParameter("recipientName");
            final String receiverPhone = request.getParameter("recipientPhone");
            final String paymentStatus = request.getParameter("paymentStatus");

            // Lấy coupon từ session nếu có
            Coupon appliedCoupon = (Coupon) session.getAttribute("appliedCoupon");

            double originalShippingFee = Double.parseDouble(request.getParameter("shippingFee"));
            double originalTotalAmount = Double.parseDouble(request.getParameter("totalAmount"));

            double discount = 0;
            double finalShippingFee = originalShippingFee;
            double finalTotalAmount = originalTotalAmount;

            if (appliedCoupon != null) {
                discount = appliedCoupon.getDiscountAmount();
                if ("TotalOrder".equals(appliedCoupon.getApplyTo())) {
                    finalTotalAmount = Math.max(0, originalTotalAmount - discount);
                } else if ("ShippingFee".equals(appliedCoupon.getApplyTo())) {
                    finalShippingFee = Math.max(0, originalShippingFee - discount);
                }
            }

            final double shippingFeeFinal = finalShippingFee;
            final double totalAmountFinal = finalTotalAmount;
            System.out.println(">>> Coupon applied: -" + discount + " VND, ApplyTo: " + (appliedCoupon != null ? appliedCoupon.getApplyTo() : "None"));
            System.out.println(">>> Final total: " + finalTotalAmount);
            System.out.println(">>> Final shipping fee: " + finalShippingFee);

            Jdbi jdbi = DBConnect.getJdbi();
            int orderId = jdbi.withHandle(handle -> {
                OrderDao orderDAO = new OrderDao(handle);
                int newOrderId = orderDAO.createOrder(user, totalAmountFinal, shippingMethod, deliveryDate,
                        deliveryTime, paymentMethod, orderNote, receiverName, receiverPhone, shippingAddress);

                OrderDetailDAO orderDetailDAO = new OrderDetailDAO(handle.getJdbi());
                for (CartProduct item : cart.getlist()) {
                    orderDetailDAO.addOrderDetail(newOrderId, item);
                }

                ProductDAO productDAO = new ProductDAO();
                for (CartProduct item : cart.getlist()) {
                    productDAO.updateProductQuantity(item.getProductId(), item.getQuantity());
                }

                ShippingDAO shippingDAO = new ShippingDAO(handle.getJdbi());
                shippingDAO.addShipping(newOrderId, shippingFeeFinal);

                PaymentDAO paymentDAO = new PaymentDAO();
                paymentDAO.addPayment(newOrderId, paymentStatus);

                return newOrderId;
            });

            // Lưu thông tin đơn hàng vào session
            session.setAttribute("currentOrderId", orderId);
            session.setAttribute("orderTotalAmount", finalTotalAmount);

            if ("VNPAY".equals(paymentMethod)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/vnpay-payment");
                dispatcher.forward(request, response);
                return;
            }

            session.removeAttribute("cart");
            session.removeAttribute("appliedCoupon"); // Xóa coupon sau khi dùng
            response.sendRedirect("home.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
