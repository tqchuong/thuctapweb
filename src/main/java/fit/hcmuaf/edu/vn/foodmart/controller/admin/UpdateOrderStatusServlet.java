package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Lấy dữ liệu từ request
            String requestData = request.getReader().lines().reduce("", String::concat);
            JsonObject json = JsonParser.parseString(requestData).getAsJsonObject();

            int orderId = json.get("orderId").getAsInt();
            String newStatus = json.get("newStatus").getAsString();

            // Lấy thông tin đơn hàng từ DAO
            OrderAdminDAO orderDAO = new OrderAdminDAO();
            Order order = orderDAO.getOrderById(orderId);
            List<OrderDetails> details = orderDAO.getOrderDetailsByProductId(orderId);

            if (order == null) {
                response.setContentType("application/json");
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Không tìm thấy đơn hàng với ID: " + orderId);
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            // Cập nhật trạng thái đơn hàng
            boolean isUpdated = orderDAO.updateOrderStatus(orderId, newStatus);

            // Phản hồi kết quả
            response.setContentType("application/json");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", isUpdated);
            jsonResponse.addProperty("message", isUpdated ? "Cập nhật trạng thái thành công." : "Cập nhật trạng thái thất bại.");
            if (isUpdated) {
                jsonResponse.addProperty("orderId", orderId);
                jsonResponse.addProperty("newStatus", newStatus);
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi hệ thống.\"}");
        }
    }
}