package fit.hcmuaf.edu.vn.foodmart.controller;


import fit.hcmuaf.edu.vn.foodmart.dao.OrderDao;
import fit.hcmuaf.edu.vn.foodmart.dao.PaymentDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import fit.hcmuaf.edu.vn.foodmart.model.Payments;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderController", value = "/order-his")
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Users auth = (Users) request.getSession().getAttribute("auth");

            if (auth != null) {
                OrderDao orderDAO = new OrderDao();
                PaymentDAO paymentDAO = new PaymentDAO();

                // Lấy danh sách đơn hàng của người dùng
                List<Order> orders = orderDAO.getOrdersByUserIdWithDetails(auth.getId());

                // Duyệt qua danh sách đơn hàng và lấy thông tin thanh toán
                for (Order order : orders) {
                    Payments payments = paymentDAO.getPaymentByOrderId(order.getId());
                    order.setPayments(payments);
                }

                request.setAttribute("orders", orders);
            }

            request.getRequestDispatcher("order-his.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
