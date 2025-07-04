package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private  Handle handle;
    private static final Jdbi jdbi = DBConnect.getJdbi();
    public OrderDao(Handle handle) {
        this.handle = handle;
    }
    public OrderDao() {

    }

    public int createOrder(Users user, double totalAmount, String shippingMethod,
                           Date deliveryDate, String deliveryTime, String paymentMethod, String orderNote,
                           String receiverName, String receiverPhone, String shippingAddress) {

        String sql = """
            INSERT INTO orders (UserId, OrderDate, TotalAmount, ShippingMethod, DeliveryDate, DeliveryTime, PaymentMethod, OrderNote, ReceiverName, ReceiverPhone, ShippingAddress, created_at, updated_at)
            VALUES (:id, NOW(), :totalAmount, :shippingMethod, :deliveryDate, :deliveryTime, :paymentMethod, :orderNote, :receiverName, :receiverPhone, :shippingAddress, NOW(), NOW())
            """;
        return handle.createUpdate(sql)
                .bind("id", user.getId())
                .bind("totalAmount", totalAmount)
                .bind("shippingMethod", shippingMethod)
                .bind("deliveryDate", deliveryDate)
                .bind("deliveryTime", deliveryTime)
                .bind("paymentMethod", paymentMethod)
                .bind("orderNote", orderNote)
                .bind("receiverName", receiverName)
                .bind("receiverPhone", receiverPhone)
                .bind("shippingAddress", shippingAddress)
                .executeAndReturnGeneratedKeys("orderId")
                .mapTo(Integer.class)
                .one();
    }
    // Hàm lấy danh sách đơn hàng và chi tiết của từng đơn hàng
    public List<Order> getOrdersByUserIdWithDetails(int userId) {
        // Câu truy vấn SQL để lấy danh sách đơn hàng và chi tiết sản phẩm
        String sql = """
                    SELECT
                        o.Id AS orderId,
                        o.UserID AS userID,
                        o.OrderDate AS orderDate,
                        o.TotalAmount AS totalAmount,
                        o.ShippingMethod AS shippingMethod,
                        DATE_FORMAT(o.DeliveryDate, '%d/%m/%Y') AS deliveryDate,
                        o.DeliveryTime AS deliveryTime,
                        o.PaymentMethod AS paymentMethod,
                        o.OrderNote AS orderNote,
                        o.ReceiverName AS receiverName,
                        o.ReceiverPhone AS receiverPhone,
                        o.ShippingAddress AS shippingAddress,
                        o.OrderStatus AS orderStatus,
                        p.ProductName AS productName,  -- Lấy tên sản phẩm
                        p.ImageURL AS imageURL,
                        od.ProductID AS productID,
                        od.Quantity AS quantity,
                        od.UnitPrice AS unitPrice
                    FROM Orders o
                    JOIN OrderDetails od ON o.Id = od.OrderID
                    JOIN Products p ON od.ProductID = p.Id  -- JOIN với bảng Products để lấy tên sản phẩm
                    WHERE o.UserID = :userId
                    ORDER BY o.OrderDate DESC;
                """;

        try (Handle handle = jdbi.open()) {
            // Tạo Map để nhóm các sản phẩm theo orderId
            Map<Integer, Order> orderMap = new HashMap<>();

            // Sử dụng mapList() để lấy danh sách kết quả
            handle.createQuery(sql)
                    .bind("userId", userId)
                    .map((rs, ctx) -> {
                        int orderId = rs.getInt("orderId");

                        // Kiểm tra nếu đơn hàng đã có trong map chưa
                        Order order = orderMap.computeIfAbsent(orderId, id -> {
                            Order newOrder = new Order();
                            newOrder.setId(id);
                            try {
                                newOrder.setUserId(rs.getInt("userId"));
                                newOrder.setOrderDate(rs.getTimestamp("orderDate"));
                                newOrder.setTotalAmount(rs.getDouble("totalAmount"));
                                newOrder.setShippingMethod(rs.getString("shippingMethod"));
                                newOrder.setDeliveryDate(rs.getString("deliveryDate"));
                                newOrder.setDeliveryTime(rs.getString("deliveryTime"));
                                newOrder.setPaymentMethod(rs.getString("paymentMethod"));
                                newOrder.setOrderNote(rs.getString("orderNote"));
                                newOrder.setReceiverName(rs.getString("receiverName"));
                                newOrder.setReceiverPhone(rs.getString("receiverPhone"));
                                newOrder.setShippingAddress(rs.getString("shippingAddress"));
                                newOrder.setOrderStatus(rs.getString("orderStatus"));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            return newOrder;
                        });

                        // Tạo đối tượng OrderDetail và thêm vào Order
                        OrderDetails orderDetail = new OrderDetails();
                        orderDetail.setProductID(rs.getInt("productID"));
                        orderDetail.setProductName(rs.getString("productName"));
                        orderDetail.setImg(rs.getString("imageURL"));
                        orderDetail.setQuantity(rs.getInt("quantity"));
                        orderDetail.setUnitPrice(rs.getDouble("unitPrice"));

                        // Thêm chi tiết vào đơn hàng
                        order.addProduct(orderDetail);

                        return null; // Chúng ta không cần trả về gì từ `map()`
                    })
                    .list(); // Trả về danh sách các kết quả (dù không sử dụng trực tiếp)

            // Trả về danh sách các đơn hàng đã được nhóm
            return new ArrayList<>(orderMap.values());
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn danh sách đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public boolean cancelOrder(int orderId) {
        try (Handle handle = jdbi.open()) {
            handle.useTransaction(transactionHandle -> {
                // 1. Lấy danh sách sản phẩm từ OrderDetails
                String selectDetails = "SELECT ProductID, Quantity FROM OrderDetails WHERE OrderID = :orderId";
                List<Map<String, Object>> items = transactionHandle
                        .createQuery(selectDetails)
                        .bind("orderId", orderId)
                        .mapToMap()
                        .list();

                for (Map<String, Object> item : items) {
                    Number productIdNumber = (Number) item.get("productid");
                    Number quantityNumber = (Number) item.get("quantity");

                    if (productIdNumber == null || quantityNumber == null) {
                        throw new RuntimeException("Thiếu ProductID hoặc Quantity trong OrderDetails của OrderID = " + orderId);
                    }

                    int productId = productIdNumber.intValue();
                    int quantity = quantityNumber.intValue();

                    String updateWarehouse = "UPDATE Warehouse SET quantity = Quantity + :qty WHERE product_id = :productId";
                    int affected = transactionHandle.createUpdate(updateWarehouse)
                            .bind("qty", quantity)
                            .bind("productId", productId)
                            .execute();

                    if (affected == 0) {
                        // Tùy vào logic hệ thống, thêm vào warehouse nếu chưa có
                        String insertWarehouse = "INSERT INTO Warehouse (product_id, quantity) VALUES (:productId, :qty)";
                        transactionHandle.createUpdate(insertWarehouse)
                                .bind("productId", productId)
                                .bind("qty", quantity)
                                .execute();
                    }
                }

                // 3. Cập nhật trạng thái đơn hàng
                String updateOrder = "UPDATE Orders SET OrderStatus = 'Đã hủy đơn hàng' WHERE Id = :orderId";
                int rows = transactionHandle.createUpdate(updateOrder)
                        .bind("orderId", orderId)
                        .execute();

                if (rows == 0) {
                    throw new RuntimeException("Không tìm thấy đơn hàng để hủy.");
                }
            });

            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi hủy đơn hàng và hoàn kho: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE Orders SET OrderStatus = :newStatus, updated_at = NOW() WHERE Id = :orderId";

        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind("newStatus", newStatus)
                    .bind("orderId", orderId)
                    .execute();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Hàm main để kiểm tra
    public static void main(String[] args) {
        OrderDao dao = new OrderDao();

        // Thay đổi userId theo nhu cầu
//        int userId = 2;
//        List<Order> orders = dao.getOrdersByUserIdWithDetails(userId);
//
//        if (orders != null && !orders.isEmpty()) {
//            System.out.println("Danh sách đơn hàng của user ID " + userId + ":");
//
//            // Duyệt qua từng đơn hàng
//            for (Order order : orders) {
//                // In thông tin đơn hàng
//                System.out.println("Mã đơn hàng: " + order.getId() +
//                        ", Ngày đặt: " + order.getOrderDate() +
//                        ", Người nhận: " + order.getReceiverName() +
//                        ", Địa chỉ: " + order.getShippingAddress() +
//                        ", Tổng tiền: " + order.getTotalAmount() +
//                        ", Hình thức: " + order.getShippingMethod() +
//                        ", Ngày giao: " + order.getDeliveryDate() +
//                        ", Thanh toán: " + order.getPaymentMethod()  +
//                        ", Trạng thái: " + order.getOrderStatus());
//
//                // Tạo danh sách các sản phẩm để nhóm lại
//                System.out.println("{");
//                for (OrderDetails detail : order.getOrderDetails()) {
//                    System.out.println("Sản phẩm: " + detail.getProductName() +
//                            ", Số lượng: " + detail.getQuantity() +
//                            ", Đơn giá: " + detail.getUnitPrice());
//                }
//                System.out.println("}");
//            }
//        } else {
//            System.out.println("Không có đơn hàng nào cho user ID " + userId + " hoặc có lỗi khi truy vấn.");
//        }

        // Thử hủy đơn hàng với ID 1
        int orderId = 25;
        boolean isCanceled = dao.cancelOrder(orderId);

        if (isCanceled) {
            System.out.println("Đơn hàng với ID " + orderId + " đã được hủy thành công.");
        } else {
            System.out.println("Không thể hủy đơn hàng với ID " + orderId + " hoặc đơn hàng không tồn tại.");
        }
    }

    public Order getOrderById(int orderId) {
        String sql = """
        SELECT * FROM Orders o join payments p on o.Id=p.OrderID WHERE o.Id=:orderId
    """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("Id"));
                        order.setUserId(rs.getInt("UserId"));
                        order.setOrderDate(rs.getTimestamp("OrderDate"));
                        order.setTotalAmount(rs.getDouble("TotalAmount"));
                        order.setShippingMethod(rs.getString("ShippingMethod"));
                        order.setDeliveryDate(rs.getDate("DeliveryDate").toString());
                        order.setDeliveryTime(rs.getString("DeliveryTime"));
                        order.setPaymentMethod(rs.getString("PaymentMethod"));
                        order.setOrderNote(rs.getString("OrderNote"));
                        order.setReceiverName(rs.getString("ReceiverName"));
                        order.setReceiverPhone(rs.getString("ReceiverPhone"));
                        order.setShippingAddress(rs.getString("ShippingAddress"));
                        order.setOrderStatus(rs.getString("OrderStatus"));


                        Payments payments = new Payments();
                        payments.setPaymentStatus(rs.getString("PaymentStatus"));
                        order.setPayments(payments);

                        return order;
                    })
                    .findOne()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}