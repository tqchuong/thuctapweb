package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.OrderDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.IOException;


@WebServlet(name = "CancelController", value = "/cancel-order")
public class CancelController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Lấy orderId từ form
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Gọi hàm hủy đơn hàng
        OrderDao orderDAO = new OrderDao();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try {
            boolean isCanceled = orderDAO.cancelOrder(orderId);
            if (isCanceled) {
                response.setStatus(HttpServletResponse.SC_OK); // Hủy thành công
                response.getWriter().write("Success");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Hủy thất bại
                response.getWriter().write("Fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Lỗi server
            response.getWriter().write("Error");
        }
    }
}

