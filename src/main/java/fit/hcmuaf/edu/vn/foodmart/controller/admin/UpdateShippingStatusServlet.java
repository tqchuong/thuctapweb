package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

@WebServlet("/updateShippingStatus")
public class UpdateShippingStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Lấy dữ liệu từ request
            String requestData = request.getReader().lines().reduce("", String::concat);
            JsonObject json = JsonParser.parseString(requestData).getAsJsonObject();

            // Lấy orderId từ JSON
            int orderId = json.get("orderId").getAsInt();

            // Lấy thông tin đơn hàng từ DAO
            OrderAdminDAO orderDAO = new OrderAdminDAO();
            boolean isUpdated = orderDAO.updateShippingStatus(orderId, "Đã giao");

            // Phản hồi kết quả
            response.setContentType("application/json");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", isUpdated);
            jsonResponse.addProperty("message", isUpdated ? "Cập nhật trạng thái giao hàng thành công." : "Cập nhật trạng thái giao hàng thất bại.");
            if (isUpdated) {
                jsonResponse.addProperty("orderId", orderId);
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi hệ thống.\"}");
        }
    }
}
