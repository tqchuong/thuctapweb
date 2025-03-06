package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.ProductAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import com.google.gson.JsonArray;


@WebServlet("/getOrderDetailsByProductId")
public class GetOrderDetailsByProductIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String productIdParam = request.getParameter("productId");

        try {
            int productId = Integer.parseInt(productIdParam);

            // Gọi DAO để lấy danh sách OrderDetails
            OrderAdminDAO orderAdminDAO = new OrderAdminDAO();
            List<OrderDetails> details = orderAdminDAO.getOrderDetailsByProductId(productId);

            JsonObject jsonResponse = new JsonObject();

            if (!details.isEmpty()) {
                jsonResponse.addProperty("success", true);

                JsonArray detailArray = new JsonArray();
                for (OrderDetails detail : details) {
                    JsonObject detailJson = new JsonObject();
                    detailJson.addProperty("orderId", detail.getOrderId());
                    detailJson.addProperty("quantity", detail.getQuantity());
                    detailJson.addProperty("unitPrice", detail.getUnitPrice());
                    detailJson.addProperty("created_at", detail.getCreated_at().toString());
                    detailArray.add(detailJson);
                }

                jsonResponse.add("details", detailArray);
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Không tìm thấy thông tin đơn hàng.");
            }

            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi hệ thống.\"}");
        }
    }
}
