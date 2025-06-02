package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class OrderDetailDAO {
    private final Jdbi jdbi;

    public OrderDetailDAO(Jdbi jdbi) {
        this.jdbi = jdbi;
    }



    public void addOrderDetail(int orderId, CartProduct item) {
        String sql = """
            INSERT INTO orderdetails (OrderId, ProductId, Quantity,UnitPrice,created_at,updated_at)
            VALUES (:orderId, :productId, :quantity, :unitPrice,Now(),Now())
            """;
        jdbi.useHandle(handle -> handle.createUpdate(sql)
                .bind("orderId", orderId)
                .bind("productId", item.getId())
                .bind("quantity", item.getQuantity())
                .bind("unitPrice", item.getPrice())
                .execute());
    }
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        String sql = """
        SELECT od.*, p.ProductName
        FROM orderdetails od
        JOIN products p ON od.ProductID = p.Id
        WHERE od.OrderID = :orderId
        """;

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("orderId", orderId)
                        .map((rs, ctx) -> {
                            OrderDetails detail = new OrderDetails();
                            detail.setOrderId(rs.getInt("OrderId"));
                            detail.setQuantity(rs.getInt("Quantity"));
                            detail.setUnitPrice(rs.getDouble("UnitPrice"));

                            Products p = new Products();
                            p.setProductName(rs.getString("ProductName"));
                            detail.setProduct(p);

                            return detail;

                        }).list()
        );
    }
}
