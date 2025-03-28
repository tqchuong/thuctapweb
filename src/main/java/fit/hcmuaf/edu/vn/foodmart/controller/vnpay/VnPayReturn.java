package fit.hcmuaf.edu.vn.foodmart.vnpay;

import fit.hcmuaf.edu.vn.foodmart.dao.OrderDao;
import fit.hcmuaf.edu.vn.foodmart.dao.PaymentDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VnPayReturn", value = "/vnpay_return")
public class VnPayReturn extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Xác minh chữ ký
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");

            if (!Config.hashAllFields(fields).equals(vnp_SecureHash)) {
                throw new ServletException("Invalid signature");
            }

            // 2. Kiểm tra trạng thái giao dịch
            if (!"00".equals(request.getParameter("vnp_TransactionStatus"))) {
                throw new ServletException("Transaction failed");
            }

            // 3. Lấy orderId từ tham số (không dùng session)
            String vnp_TxnRef = request.getParameter("vnp_TxnRef");
            int orderId = Integer.parseInt(vnp_TxnRef);

            // 4. Cập nhật trạng thái thanh toán
            Jdbi jdbi = DBConnect.getJdbi();
            jdbi.useTransaction(handle -> {
                PaymentDAO paymentDAO = new PaymentDAO();
                if (!paymentDAO.updatePaymentStatus(orderId, "Đã thanh toán")) {
                    throw new ServletException("Failed to update payment status");
                }
            });

            // 5. Trả về response thành công
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Payment status updated successfully");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}


