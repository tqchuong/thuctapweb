package fit.hcmuaf.edu.vn.foodmart.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.*;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import java.util.ArrayList;

import java.util.List;

public class OrderAdminDAO {
    private static Jdbi jdbi;

    static {
        jdbi = DBConnect.getJdbi(); // Kết nối với DB thông qua DBConnect
    }

    public List<Order> getAllOrders() {
        String sql = """
        SELECT o.*, s.ShippingStatus 
        FROM orders o 
        LEFT JOIN shipping s ON o.Id = s.OrderId
    """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .map((rs, ctx) -> {
                        Order order = new Order();
                        order.setId(rs.getInt("Id"));
                        order.setOrderDate(rs.getTimestamp("OrderDate"));
                        order.setReceiverPhone(rs.getString("ReceiverPhone"));
                        order.setTotalAmount(rs.getDouble("TotalAmount"));
                        order.setOrderStatus(rs.getString("OrderStatus"));
                        // Gán Shipping
                        Shipping shipping = new Shipping();
                        shipping.setShippingStatus(rs.getString("ShippingStatus"));
                        order.setShipping(shipping);
                        return order;
                    })
                    .list();
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
                     SELECT p.Id, p.ProductName, p.Price, p.ImageURL
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
//                        product.setWeight(result.getInt("Weight"));
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
        String sql = """
        SELECT SUM(od.Quantity) AS TotalQuantity
        FROM orderdetails od
        JOIN orders o ON od.OrderID = o.Id
        JOIN shipping s ON o.Id = s.OrderId
        WHERE s.ShippingStatus = 'Đã giao'
    """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }
    public List<Products> getProductsNeedRestock() {
        String sql = """
        SELECT 
            p.Id, p.ProductName, p.ImageURL, 
            COALESCE(w.Quantity, 0) AS stock_quantity,
            COALESCE(SUM(od.Quantity), 0) AS sold_last_week
        FROM products p
        LEFT JOIN warehouse w ON p.Id = w.product_id
        LEFT JOIN orderdetails od ON p.Id = od.ProductID
        LEFT JOIN orders o ON od.OrderID = o.Id 
            AND o.OrderDate >= DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY)
        GROUP BY p.Id, p.ProductName, p.ImageURL, w.Quantity
        HAVING stock_quantity <= 15 AND sold_last_week >= 5
        ORDER BY sold_last_week DESC
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            Products product = new Products();
                            product.setID(rs.getInt("Id"));
                            product.setProductName(rs.getString("ProductName"));
                            product.setImageURL(rs.getString("ImageURL"));

                            Warehouse warehouse = new Warehouse();
                            warehouse.setQuantity(rs.getInt("stock_quantity"));
                            product.setWarehouse(warehouse);

                            product.setSoldQuantity(rs.getInt("sold_last_week")); // bạn nên thêm field soldQuantity

                            return product;
                        }).list()
        );
    }


    public List<Products> getSlowSellingProducts() {
        String sql = """
        SELECT
            p.Id,
            p.ProductName,
            p.ImageURL,
            COALESCE(SUM(od.Quantity), 0) AS total_sold
        FROM products p
        LEFT JOIN orderdetails od ON p.Id = od.ProductID
        LEFT JOIN orders o ON od.OrderID = o.Id
            AND o.OrderDate >= DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY)
        GROUP BY p.Id, p.ProductName, p.ImageURL
        HAVING total_sold < 10
        ORDER BY total_sold DESC
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            Products p = new Products();
                            p.setID(rs.getInt("Id"));
                            p.setProductName(rs.getString("ProductName"));
                            p.setImageURL(rs.getString("ImageURL"));
                            p.setSoldQuantity(rs.getInt("total_sold"));
                            return p;
                        }).list()
        );
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET OrderStatus = :newStatus WHERE Id = :orderId";

        try (Handle handle = jdbi.open()) {
            System.out.println("SQL Query: " + sql);
            System.out.println("Thực thi với orderId: " + orderId + ", newStatus: " + newStatus);

            int rowsUpdated = handle.createUpdate(sql)
                    .bind("newStatus", newStatus)
                    .bind("orderId", orderId)
                    .execute();

            System.out.println("Rows affected: " + rowsUpdated);
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            SUM(od.Quantity) AS total_quantity,
            SUM(od.Quantity * od.UnitPrice) AS total_revenue,
            COALESCE(w.Quantity, 0) AS stock_quantity
        FROM products p
        JOIN orderdetails od ON p.Id = od.ProductID
        LEFT JOIN warehouse w ON p.Id = w.product_id
        GROUP BY p.Id, p.ProductName, p.ImageURL, w.Quantity
        ORDER BY total_revenue DESC
    """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            Products product = new Products();
                            product.setID(rs.getInt("product_id"));
                            product.setProductName(rs.getString("product_name"));
                            product.setImageURL(rs.getString("product_image"));

                            // Gán tồn kho
                            Warehouse w = new Warehouse();
                            w.setQuantity(rs.getInt("stock_quantity"));
                            product.setWarehouse(w); // Đảm bảo Product có setWarehouse

                            OrderDetails detail = new OrderDetails();
                            detail.setProduct(product);
                            detail.setQuantity(rs.getInt("total_quantity"));
                            detail.setUnitPrice(rs.getDouble("total_revenue"));
                            return detail;
                        }).list()
        );
    }


    public boolean updateShippingStatus(int orderId, String newStatus) {
        String sql = "UPDATE shipping SET ShippingStatus = :newStatus WHERE OrderID = :orderId";

        try (Handle handle = jdbi.open()) {
            System.out.println("SQL Query: " + sql);
            System.out.println("Thực thi với orderId: " + orderId + ", newStatus: " + newStatus);

            int rowsUpdated = handle.createUpdate(sql)
                    .bind("newStatus", newStatus)
                    .bind("orderId", orderId)
                    .execute();

            System.out.println("Rows affected: " + rowsUpdated);
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalOrders() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM orders")
                        .mapTo(Integer.class)
                        .findOnly()
        );
    }

    public int getProcessedOrders() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM orders WHERE OrderStatus = 'Đã xử lý'")
                        .mapTo(Integer.class)
                        .findOnly()
        );
    }

    public int getShippedOrders() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM shipping WHERE ShippingStatus = 'Đã giao'")
                        .mapTo(Integer.class)
                        .findOnly()
        );
    }

    public double getRevenueToday() {
        return jdbi.withHandle(handle ->
                handle.createQuery("""
            SELECT SUM(od.Quantity * od.UnitPrice)
            FROM orderdetails od
            JOIN orders o ON od.OrderID = o.Id
            WHERE DATE(o.OrderDate) = CURRENT_DATE
        """)
                        .mapTo(Double.class)
                        .findOne()
                        .orElse(0.0)
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
