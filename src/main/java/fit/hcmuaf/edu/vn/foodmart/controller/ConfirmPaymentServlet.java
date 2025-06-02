package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.PaymentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirm-payment")
public class ConfirmPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

            // Lưu thông tin vào request để chuyển tiếp
            request.setAttribute("orderId", orderId);
            request.setAttribute("totalAmount", totalAmount);

            // Chuyển tiếp sang VNPay
            RequestDispatcher dispatcher = request.getRequestDispatcher("/vnpay-payment");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?message=Invalid payment request");
        }
    }
}