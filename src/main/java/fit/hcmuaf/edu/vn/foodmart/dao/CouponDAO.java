package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Coupon;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class CouponDAO {

    private static Jdbi jdbi = DBConnect.getJdbi();

    public List<Coupon> getActiveCoupons() {
        String sql = "SELECT " +
                "c.Id AS couponID, " +
                "c.CouponCode AS couponCode, " +
                "c.DiscountAmount AS discountAmount, " +
                "c.Description AS shortDescription, " +
                "c.StartDate AS startDate, " +
                "c.EndDate AS endDate, " +
                "c.MinOrderAmount AS minOrderAmount " +
                "FROM Coupons c " +
                "WHERE c.StartDate <= CURRENT_DATE " +
                "AND c.EndDate >= CURRENT_DATE " +
                "ORDER BY c.StartDate DESC";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .map((rs, ctx) -> {
                        Coupon coupon = new Coupon();
                        coupon.setId(rs.getInt("couponID"));
                        coupon.setCouponCode(rs.getString("couponCode"));
                        coupon.setDiscountAmount(rs.getDouble("discountAmount"));
                        coupon.setDescription(rs.getString("shortDescription"));
                        coupon.setStartDate(rs.getTimestamp("startDate"));
                        coupon.setEndDate(rs.getTimestamp("endDate"));
                        coupon.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                        return coupon;
                    })
                    .list();
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn danh sách coupon: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Coupon getCouponByCode(String couponCode) {
        String sql = "SELECT * FROM Coupons WHERE CouponCode = :couponCode";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("couponCode", couponCode) // Gắn giá trị tham số
                    .map((rs, ctx) -> {
                        Coupon coupon = new Coupon();
                        coupon.setId(rs.getInt("Id"));
                        coupon.setCouponCode(rs.getString("CouponCode"));
                        coupon.setDiscountAmount(rs.getDouble("DiscountAmount"));
                        coupon.setDescription(rs.getString("Description"));
                        coupon.setStartDate(rs.getTimestamp("StartDate"));
                        coupon.setEndDate(rs.getTimestamp("EndDate"));
                        coupon.setMinOrderAmount(rs.getDouble("MinOrderAmount"));
                        return coupon;
                    })
                    .findOne() // Trả về một kết quả (nếu có)
                    .orElse(null); // Nếu không có kết quả, trả về null
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn coupon với mã: " + couponCode);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        CouponDAO dao = new CouponDAO();

        List<Coupon> activeCoupons = dao.getActiveCoupons();
        if (activeCoupons != null && !activeCoupons.isEmpty()) {
            System.out.println("Danh sách coupon còn hiệu lực:");
            for (Coupon coupon : activeCoupons) {
                System.out.println("Mã coupon: " + coupon.getCouponCode() +
                        ", Giảm giá: " + coupon.getDiscountAmount() +
                        ", Mô tả: " + coupon.getDescription() +
                        ", Điều kiện tối thiểu: " + coupon.getMinOrderAmount() +
                        ", Thời gian áp dụng: Từ " + coupon.getStartDate() + " đến " + coupon.getEndDate());
            }
        } else {
            System.out.println("Không có coupon nào còn hiệu lực hoặc có lỗi khi truy vấn.");
        }
    }
}
