package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.OrderDao;
import fit.hcmuaf.edu.vn.foodmart.dao.OrderDetailDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("auth");


        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {

            String orderIdParam = request.getParameter("orderId");
            if (orderIdParam == null || orderIdParam.isEmpty()) {
                response.sendRedirect("orders.jsp");
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                response.sendRedirect("orders.jsp?error=invalid_id");
                return;
            }

            // Lấy thông tin đơn hàng
            OrderDao orderDAO = new OrderDao();
            Order order = orderDAO.getOrderById(orderId);

            // Kiểm tra quyền sở hữu đơn hàng
            if (order == null || order.getUserId() != user.getId()) {
                response.sendRedirect("orders.jsp?error=invalid_order");
                return;
            }
            // Lấy chi tiết đơn hàng
            OrderDetailDAO detailDAO = new OrderDetailDAO(DBConnect.getJdbi());
            List<OrderDetails> orderDetails = detailDAO.getOrderDetailsByOrderId(orderId);
            request.setAttribute("orderDetails", orderDetails);

            // Đặt thông tin đơn hàng vào request
            request.setAttribute("order", order);

            request.getRequestDispatcher("/bill.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=" + e.getMessage());
        }
    }
}
