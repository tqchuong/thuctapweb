package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;

@WebServlet("/getOrderDetails")
public class OrderDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String orderIdParam = request.getParameter("orderId");
            if (orderIdParam == null || orderIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Thiếu mã đơn hàng.\"}");
                return;
            }

            int orderId = Integer.parseInt(orderIdParam);
            OrderAdminDAO orderDAO = new OrderAdminDAO();
            Order order = orderDAO.getOrderById(orderId);

            List<OrderDetails> details = orderDAO.getOrderDetailsByOrderId(orderId);

            if (order == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Không tìm thấy đơn hàng!\"}");
                return;
            }
            double totalPrice = orderDAO.calculateTotalPrice(orderId);


            // Dùng Gson để chuyển dữ liệu thành JSON
            Gson gson = new Gson();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("detailsHtml", buildDetailsHtml(details));
            jsonResponse.addProperty("summaryHtml", buildSummaryHtml(order));
            jsonResponse.addProperty("totalPrice", String.format("%,.0f", totalPrice) + " ₫");

            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Lỗi hệ thống!\"}");
        }
    }

    private String buildDetailsHtml(List<OrderDetails> details) {
        StringBuilder html = new StringBuilder();
        for (OrderDetails detail : details) {
            html.append("<div class='order-product'>")
                    .append("<div class='order-product-left'>")
                    .append("<img class='product-image' src='").append(detail.getProduct().getImageURL()).append("' alt='").append(detail.getProduct().getProductName()).append("'/>")
                    .append("<div class='order-product-info'>")
                    .append("<h4>").append(detail.getProduct().getProductName()).append("</h4>")
                    .append("<p class='order-product-note'><i class='fa-light fa-pen'></i> ")
                    .append("</p>")
                    .append("<p class='order-product-quantity'>SL: ").append(detail.getQuantity()).append("</p>")
                    .append("</div>")
                    .append("</div>")
                    .append("<div class='order-product-right'>")
                    .append("<span class='order-product-price'>").append(String.format("%,.0f", detail.getUnitPrice())).append(" ₫</span>")
                    .append("</div>")
                    .append("</div>");
        }
        return html.toString();
    }


    private String buildSummaryHtml(Order order) {
        return "<ul class='order-summary'>" +
                "<li><span class='summary-icon'><i class='fa-light fa-calendar-days'></i></span> Ngày đặt hàng: " + order.getOrderDate() + "</li>" +
                "<li><span class='summary-icon'><i class='fa-light fa-truck'></i></span> Hình thức giao: " + order.getShippingMethod() + "</li>" +
                "<li><span class='summary-icon'><i class='fa-thin fa-person'></i></span> Người nhận: " + order.getReceiverName() + "</li>" +
                "<li><span class='summary-icon'><i class='fa-light fa-phone'></i></span> Số điện thoại: " + order.getReceiverPhone() + "</li>" +
                "<li><span class='summary-icon'><i class='fa-light fa-clock'></i></span> Thời gian giao: " +
                (order.getDeliveryTime() == null ? "Chưa xác định" : order.getDeliveryTime()) + "</li>" +
                "<li><span class='summary-icon'><i class='fa-light fa-location-dot'></i></span> Địa chỉ: " + order.getShippingAddress() + "</li>" +
                "<li><span class='summary-icon'><i class='fa-light fa-note-sticky'></i></span> Ghi chú: " +
                (order.getOrderNote() == null || order.getOrderNote().isEmpty() ? "Không có ghi chú" : order.getOrderNote()) + "</li>" +
                "</ul>";
    }


}
