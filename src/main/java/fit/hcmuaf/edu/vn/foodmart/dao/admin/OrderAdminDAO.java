package fit.hcmuaf.edu.vn.foodmart.dao.admin;

import java.util.Collections;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Order;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import java.util.ArrayList;

import java.util.List;

public class OrderAdminDAO {
    private static Jdbi jdbi;

    static {
        jdbi = DBConnect.getJdbi(); // Kết nối với DB thông qua DBConnect
    }

    // 1. Lấy tất cả người dùng
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapToBean(Order.class) // Đổi sang ánh xạ với lớp Order
                    .list(); // Trả về danh sách tất cả đơn hàng
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE Id = ?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, orderId)
                    .mapToBean(Order.class)
                    .findOne()
                    .orElse(null);
        }
    }

    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        System.out.println("Lấy chi tiết đơn hàng cho OrderId: " + orderId); // Log orderId
        String sql = "SELECT od.*, p.ProductName, p.ImageURL " +
                "FROM orderdetails od " +
                "JOIN products p ON od.ProductID = p.Id " +
                "WHERE od.OrderId = :orderId";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("orderId", orderId)
                        .map((rs, ctx) -> {
                            System.out.println("Chi tiết đơn hàng: " + rs.getInt("Id")); // Log từng chi tiết đơn hàng
                            OrderDetails detail = new OrderDetails();
                            detail.setId(rs.getInt("Id"));
                            detail.setOrderId(rs.getInt("OrderId"));
                            detail.setProductID(rs.getInt("ProductId"));
                            detail.setQuantity(rs.getInt("Quantity"));
                            detail.setUnitPrice(rs.getDouble("UnitPrice"));

                            Products product = new Products();
                            product.setProductName(rs.getString("ProductName"));
                            product.setImageURL(rs.getString("ImageURL"));
                            detail.setProduct(product);

                            return detail;
                        }).list()
        );
    }
    public List<Products> getSoldListProducts() {
        String sql = """
                     SELECT p.Id, p.ProductName, p.Price, SUM(od.Quantity) AS TotalQuantity, p.ImageURL
                    FROM Products p
                    JOIN OrderDetails od ON p.Id = od.ProductID
                    JOIN Orders o ON od.OrderID = o.Id
                    WHERE o.OrderStatus IN ('Đã xử lý', 'Đã vận chuyển')
                    GROUP BY p.Id, p.ProductName, p.Price, p.ImageURL;
                                   
             """;

        try (Handle handle = jdbi.open()) {
            // Ánh xạ kết quả vào danh sách các đối tượng Products
            return handle.createQuery(sql)
                    .map((result, context) -> {
                        Products product = new Products();
                        product.setID(result.getInt("Id"));
                        product.setProductName(result.getString("ProductName"));
                        product.setPrice(result.getDouble("Price"));
                        product.setStockQuantity(result.getInt("TotalQuantity"));
                        product.setImageURL(result.getString("ImageURL"));
                        return product;
                    })
                    .list(); // Trả về danh sách các đối tượng Products
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    public List<OrderDetails> getOrderDetailsByProductId(int productId) {
        String sql = """
                 SELECT o.Id AS OrderID, od.Quantity, od.UnitPrice, od.Created_at
                 FROM Orders o
                 JOIN OrderDetails od ON o.Id = od.OrderID
                 WHERE od.ProductID = :productId;
             """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("productId", productId)  // Gán giá trị cho :productId
                    .map((result, context) -> {
                        OrderDetails orderDetails = new OrderDetails();
                        orderDetails.setOrderId(result.getInt("OrderID"));
                        orderDetails.setQuantity(result.getInt("Quantity"));
                        orderDetails.setUnitPrice(result.getDouble("UnitPrice"));
                        orderDetails.setCreated_at(result.getTimestamp("Created_at"));


                        return orderDetails;
                    })
                    .list(); // Trả về danh sách các đối tượng OrderDetails
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi
        }
    }


    public int getSoldProducts() {
        String sql = "SELECT SUM(od.Quantity) AS TotalQuantity " +
                "FROM orderdetails od " +
                "JOIN orders o ON od.OrderID = o.Id " +
                "WHERE o.OrderStatus = 'Đã xử lý'";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }
    public int getSoldQuantity() {
        String sql = "SELECT SUM(od.Quantity) AS TotalQuantity " +
                "FROM orderdetails od " +
                "JOIN orders o ON od.OrderID = o.Id " +
                "WHERE o.OrderStatus = 'Chưa xử lý'";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET OrderStatus = :newStatus WHERE Id = :orderId";

        try (Handle handle = jdbi.open()) {
            System.out.println("Order ID: " + orderId + ", New Status: " + newStatus);

            int rowsUpdated = handle.createUpdate(sql)
                    .bind("newStatus", newStatus)
                    .bind("orderId", orderId)
                    .execute();
            return rowsUpdated > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public double calculateTotalPrice(int orderId) {
        String sql = "SELECT SUM(od.Quantity * od.UnitPrice) AS TotalPrice " +
                "FROM orderdetails od WHERE od.OrderId = :orderId";
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("orderId", orderId)
                            .mapTo(Double.class)
                            .findOne()
                            .orElse(0.0) // Nếu không có chi tiết, trả về 0
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0; // Trả về 0 nếu có lỗi
        }
    }

    public List<OrderDetails> getProductReport() {
        String sql = """
        SELECT 
            p.Id AS product_id,
            p.ProductName AS product_name,
            p.ImageURL AS product_image,
            od.Quantity AS total_quantity,
            (od.Quantity * od.UnitPrice) AS total_revenue
        FROM  products p
        JOIN orderdetails od
        ON p.Id = od.ProductID
        ORDER BY total_revenue DESC;""";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            // Tạo đối tượng Products
                            Products product = new Products();
                            product.setID(rs.getInt("product_id"));
                            product.setProductName(rs.getString("product_name"));
                            product.setImageURL(rs.getString("product_image"));

                            // Tạo đối tượng OrderDetails và gán sản phẩm
                            OrderDetails detail = new OrderDetails();
                            detail.setProduct(product);
                            detail.setQuantity(rs.getInt("total_quantity"));
                            detail.setUnitPrice(rs.getDouble("total_revenue"));

                            return detail;
                        }).list()
        );
    }




    public static void main(String[] args) {
        OrderAdminDAO dao = new OrderAdminDAO();

        System.out.println("====== LẤY TẤT CẢ THONG TIN ======");
        List<Order> Orders = dao.getAllOrders();
        List<OrderDetails> details = dao.getOrderDetailsByProductId(3);
        details.forEach(System.out::println);
    }
}
