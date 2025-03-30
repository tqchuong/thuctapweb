package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Payments;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class PaymentDAO {

    private static final Jdbi jdbi = DBConnect.getJdbi();


    public void addPayment(int orderId, String paymentStatus) {
        String sql = """
            INSERT INTO Payments (OrderID, PaymentStatus, created_at, updated_at) 
            VALUES (:orderId, :paymentStatus, NOW(), NOW())
            """;
        jdbi.useHandle(handle -> handle.createUpdate(sql)
                .bind("orderId", orderId)
                .bind("paymentStatus", paymentStatus)
                .execute());
    }
    public boolean updatePaymentStatus(int orderId, String status) {
        // Triển khai logic cập nhật
        return jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE payments SET status = :status WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .bind("status", status)
                        .execute() > 0
        );
    }
    // Hàm truy vấn thông tin thanh toán theo OrderID
    public Payments getPaymentByOrderId(int orderId) {
        String sql = "SELECT * FROM Payments WHERE OrderID = :orderId";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .map((rs, ctx) -> new Payments(
                            rs.getInt("Id"),
                            rs.getInt("OrderID"),
                            rs.getTimestamp("PaymentDate"),
                            rs.getString("PaymentStatus"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    ))
                    .findOne()
                    .orElse(null); // Trả về null nếu không tìm thấy
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin thanh toán theo OrderID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }


    }

    public static void main(String[] args) {
        PaymentDAO paymentDAO = new PaymentDAO();
        int testOrderId = 1; // OrderID cần kiểm tra

        Payments payment = paymentDAO.getPaymentByOrderId(testOrderId);
        if (payment != null) {
            System.out.println("Thông tin thanh toán cho OrderID " + testOrderId + ":");
            System.out.println("ID: " + payment.getId());
            System.out.println("OrderID: " + payment.getOrderID());
            System.out.println("PaymentDate: " + payment.getPaymentDate());
            System.out.println("PaymentStatus: " + payment.getPaymentStatus());
        } else {
            System.out.println("Không tìm thấy thông tin thanh toán cho OrderID: " + testOrderId);
        }
    }

}