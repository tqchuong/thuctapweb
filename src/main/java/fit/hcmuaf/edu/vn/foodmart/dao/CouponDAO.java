package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Coupon;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class CouponDAO {

    private static Jdbi jdbi = DBConnect.getJdbi();

    // Lấy danh sách các mã giảm giá còn hiệu lực
    public List<Coupon> getActiveCoupons() {
        String sql = "SELECT " +
                "c.Id AS couponID, " +
                "c.CouponCode AS couponCode, " +
                "c.DiscountAmount AS discountAmount, " +
                "c.Description AS shortDescription, " +
                "c.StartDate AS startDate, " +
                "c.EndDate AS endDate, " +
                "c.MinOrderAmount AS minOrderAmount, " +
                "c.DiscountType AS discountType, " +
                "c.MaxUsage AS maxUsage, " +
                "c.UsedCount AS usedCount, " +
                "c.Status AS status " +
                "FROM Coupons c " +
                "WHERE c.StartDate <= CURRENT_DATE " +
                "AND c.EndDate >= CURRENT_DATE " +
                "AND c.Status = 'Active' " +
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
                        coupon.setDiscountType(rs.getString("discountType"));
                        coupon.setMaxUsage(rs.getInt("maxUsage"));
                        coupon.setUsedCount(rs.getInt("usedCount"));
                        coupon.setStatus(rs.getString("status"));
                        return coupon;
                    })
                    .list();
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn danh sách coupon: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Lấy mã giảm giá theo mã
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
                        coupon.setDiscountType(rs.getString("DiscountType"));
                        coupon.setMaxUsage(rs.getInt("MaxUsage"));
                        coupon.setUsedCount(rs.getInt("UsedCount"));
                        coupon.setStatus(rs.getString("Status"));
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

    // Tạo mã giảm giá mới
    public void createCoupon(Coupon coupon) {
        String sql = "INSERT INTO Coupons (CouponCode, DiscountAmount, Description, StartDate, EndDate, MinOrderAmount, DiscountType, MaxUsage, UsedCount, Status) " +
                "VALUES (:couponCode, :discountAmount, :description, :startDate, :endDate, :minOrderAmount, :discountType, :maxUsage, :usedCount, :status)";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("couponCode", coupon.getCouponCode())
                    .bind("discountAmount", coupon.getDiscountAmount())
                    .bind("description", coupon.getDescription())
                    .bind("startDate", coupon.getStartDate())
                    .bind("endDate", coupon.getEndDate())
                    .bind("minOrderAmount", coupon.getMinOrderAmount())
                    .bind("discountType", coupon.getDiscountType())
                    .bind("maxUsage", coupon.getMaxUsage())
                    .bind("usedCount", coupon.getUsedCount())
                    .bind("status", coupon.getStatus())
                    .execute();
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm mã giảm giá: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Cập nhật mã giảm giá
    public void updateCoupon(Coupon coupon) {
        String sql = "UPDATE Coupons SET " +
                "CouponCode = :couponCode, " +
                "DiscountAmount = :discountAmount, " +
                "Description = :description, " +
                "StartDate = :startDate, " +
                "EndDate = :endDate, " +
                "MinOrderAmount = :minOrderAmount, " +
                "DiscountType = :discountType, " +
                "MaxUsage = :maxUsage, " +
                "UsedCount = :usedCount, " +
                "Status = :status " +
                "WHERE Id = :id";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("id", coupon.getId())
                    .bind("couponCode", coupon.getCouponCode())
                    .bind("discountAmount", coupon.getDiscountAmount())
                    .bind("description", coupon.getDescription())
                    .bind("startDate", coupon.getStartDate())
                    .bind("endDate", coupon.getEndDate())
                    .bind("minOrderAmount", coupon.getMinOrderAmount())
                    .bind("discountType", coupon.getDiscountType())
                    .bind("maxUsage", coupon.getMaxUsage())
                    .bind("usedCount", coupon.getUsedCount())
                    .bind("status", coupon.getStatus())
                    .execute();
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật mã giảm giá: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Xóa mã giảm giá
    public void deleteCoupon(int couponId) {
        String sql = "DELETE FROM Coupons WHERE Id = :id";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("id", couponId)
                    .execute();
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa mã giảm giá: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CouponDAO dao = new CouponDAO();

        // Ví dụ: Lấy danh sách các mã giảm giá còn hiệu lực
        List<Coupon> activeCoupons = dao.getActiveCoupons();
        if (activeCoupons != null && !activeCoupons.isEmpty()) {
            System.out.println("Danh sách coupon còn hiệu lực:");
            for (Coupon coupon : activeCoupons) {
                System.out.println("Mã coupon: " + coupon.getCouponCode() +
                        ", Giảm giá: " + coupon.getDiscountAmount() +
                        ", Mô tả: " + coupon.getDescription() +
                        ", Điều kiện tối thiểu: " + coupon.getMinOrderAmount() +
                        ", Thời gian áp dụng: Từ " + coupon.getStartDate() + " đến " + coupon.getEndDate() +
                        ", Trạng thái: " + coupon.getStatus() );
            }
        } else {
            System.out.println("Không có coupon nào còn hiệu lực hoặc có lỗi khi truy vấn.");
        }
    }
}
