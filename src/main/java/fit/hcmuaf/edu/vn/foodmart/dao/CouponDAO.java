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
        String sql = "SELECT * FROM Coupons " +
                "WHERE StartDate <= CURRENT_DATE " +
                "AND EndDate >= CURRENT_DATE " +
                "AND Status = 'Active' " +
                "ORDER BY StartDate DESC";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapToBean(Coupon.class)
                    .list();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách coupon còn hiệu lực: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Lấy mã giảm giá theo CouponCode
    public Coupon getCouponByCode(String couponCode) {
        String sql = "SELECT * FROM Coupons WHERE CouponCode = :couponCode";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("couponCode", couponCode)
                    .mapToBean(Coupon.class)
                    .findOne()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi truy vấn coupon: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Lấy mã giảm giá theo ID
    public Coupon getCouponById(int id) {
        String sql = "SELECT * FROM Coupons WHERE Id = :id";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("id", id)
                    .mapToBean(Coupon.class)
                    .findOne()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy coupon theo ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Thêm coupon mới vào database
    public void createCoupon(Coupon coupon) {
        String sql = "INSERT INTO Coupons (CouponCode, DiscountAmount, Description, StartDate, EndDate, MinOrderAmount, DiscountType, MaxUsage, UsedCount, MaxUsagePerUser, MaxDiscountAmount, Status) " +
                "VALUES (:couponCode, :discountAmount, :description, :startDate, :endDate, :minOrderAmount, :discountType, :maxUsage, :usedCount, :maxUsagePerUser, :maxDiscountAmount, :status)";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bindBean(coupon)
                    .execute();
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm coupon mới: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Cập nhật coupon
    public void updateCoupon(Coupon coupon) {
        String sql = "UPDATE Coupons SET " +
                "CouponCode = :couponCode, DiscountAmount = :discountAmount, Description = :description, " +
                "StartDate = :startDate, EndDate = :endDate, MinOrderAmount = :minOrderAmount, " +
                "DiscountType = :discountType, MaxUsage = :maxUsage, UsedCount = :usedCount, " +
                "MaxUsagePerUser = :maxUsagePerUser, MaxDiscountAmount = :maxDiscountAmount, Status = :status " +
                "WHERE Id = :id";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bindBean(coupon)
                    .execute();
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật coupon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Xóa coupon theo ID
    public boolean  deleteCoupon(int id) {
        String sql = "DELETE FROM Coupons WHERE Id = :id";

        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("id", id)
                .execute() > 0);
    }




    // Test các chức năng
    public static void main(String[] args) {
        CouponDAO dao = new CouponDAO();

        // Test: lấy tất cả coupon đang hoạt động
        List<Coupon> coupons = dao.getActiveCoupons();
        if (coupons != null) {
            coupons.forEach(System.out::println);
        } else {
            System.out.println("Không lấy được danh sách coupon.");
        }

        // Test: thêm coupon
        Coupon newCoupon = new Coupon();
        newCoupon.setCouponCode("TESTCOUPON10");
        newCoupon.setDiscountAmount(10);
        newCoupon.setDescription("Giảm 10% cho đơn hàng test");
        newCoupon.setStartDate(java.sql.Timestamp.valueOf("2025-05-01 00:00:00"));
        newCoupon.setEndDate(java.sql.Timestamp.valueOf("2025-05-31 23:59:59"));
        newCoupon.setMinOrderAmount(100000);
        newCoupon.setDiscountType("Percentage");
        newCoupon.setMaxUsage(100);
        newCoupon.setUsedCount(0);
        newCoupon.setMaxUsagePerUser(1);
        newCoupon.setMaxDiscountAmount(50000.0);
        newCoupon.setStatus("Active");

        dao.createCoupon(newCoupon);
        System.out.println("Đã thêm coupon mới.");

        // Test: cập nhật coupon
        Coupon existing = dao.getCouponByCode("TESTCOUPON10");
        if (existing != null) {
            existing.setDescription("Cập nhật mô tả coupon 10%");
            existing.setMaxUsage(200);
            dao.updateCoupon(existing);
            System.out.println("Đã cập nhật coupon.");
        }

        // Test: xóa coupon
        if (existing != null) {
            dao.deleteCoupon(existing.getId());
            System.out.println("Đã xóa coupon.");
        }
    }
}
