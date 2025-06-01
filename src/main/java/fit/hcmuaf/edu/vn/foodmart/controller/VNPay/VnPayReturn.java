package fit.hcmuaf.edu.vn.foodmart.controller.VNPay;

import com.google.gson.JsonObject;
import fit.hcmuaf.edu.vn.foodmart.dao.PaymentDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay-ipn")
public class VnPayReturn extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Chuẩn bị response
        response.setContentType("application/json");

        try {
            HttpSession session = request.getSession();
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");

            PaymentDAO paymentDAO = new PaymentDAO();
            boolean updated;

            if ("00".equals(vnp_ResponseCode)) {
                updated = paymentDAO.updatePaymentStatus(Integer.parseInt(vnp_TxnRef), "Đã thanh toán");
                session.removeAttribute("cart");
            } else {
                updated = paymentDAO.updatePaymentStatus(Integer.parseInt(vnp_TxnRef), "Thanh toán thất bại");
            }
            // 2. Chuyển hướng về trang

            response.sendRedirect("bill?orderId=" + vnp_TxnRef);

        } catch (Exception e) {
            // Nếu có lỗi vẫn chuyển hướng về home nhưng có thể thêm thông báo lỗi
            response.sendRedirect(request.getContextPath() + "/home.jsp?error=payment_error");
        }
    }
    }
