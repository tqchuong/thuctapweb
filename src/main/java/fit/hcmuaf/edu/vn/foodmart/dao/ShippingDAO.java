package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.model.Shipping;
import org.jdbi.v3.core.Jdbi;


    public class ShippingDAO {
        private Jdbi jdbi;

        public ShippingDAO(Jdbi jdbi) {
            this.jdbi = jdbi;
        }

        public void addShipping(int orderId, double shippingCost) {
            String sql = """
                INSERT INTO shipping (OrderId, ShippingCost)
                VALUES (:orderId,  :shippingCost)
                """;

            jdbi.useHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("orderId", orderId)
                            .bind("shippingCost", shippingCost)
                            .execute()
            );
        }

        public Shipping getShippingByOrderId(int orderId) {
            String sql = """
                SELECT * FROM shipping
                WHERE OrderId = :orderId
                """;

            return jdbi.withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("orderId", orderId)
                            .map((rs, ctx) -> {
                                Shipping shipping = new Shipping();
                                shipping.setId(rs.getInt("ID"));
                                shipping.setOrderId(rs.getInt("OrderId"));

                                shipping.setShippingCost(rs.getDouble("ShippingCost"));
                                return shipping;
                            })
                            .findOne()
                            .orElse(null));
        }

        public boolean deleteShippingByOrderId(int orderId) {
            String sql = """
                DELETE FROM shipping
                WHERE OrderId = :orderId
                """;

            return jdbi.withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind("orderId", orderId)
                            .execute()) > 0;
        }
}
