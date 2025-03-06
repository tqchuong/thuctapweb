package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.Cart.CartProduct;
import fit.hcmuaf.edu.vn.foodmart.model.OrderDetails;
import org.jdbi.v3.core.Jdbi;

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
}
