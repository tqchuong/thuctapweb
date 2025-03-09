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
            String requestData = request.getReader().lines().reduce("", String::concat);
            System.out.println("Dữ liệu nhận được từ JS: " + requestData); // Log nhận request

            JsonObject json = JsonParser.parseString(requestData).getAsJsonObject();
            int orderId = json.get("orderId").getAsInt();
            String newStatus = json.get("newStatus").getAsString();

            System.out.println("Cập nhật Order ID: " + orderId + " thành trạng thái: " + newStatus);

            OrderAdminDAO orderDAO = new OrderAdminDAO();
            boolean isUpdated = orderDAO.updateOrderStatus(orderId, newStatus);

            System.out.println("Cập nhật thành công: " + isUpdated);

            response.setContentType("application/json");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", isUpdated);
            jsonResponse.addProperty("message", isUpdated ? "Cập nhật trạng thái thành công." : "Cập nhật thất bại.");
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi hệ thống.\"}");
        }
    }
}


