package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Coupon;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/couponController")
public class AddCouponServlet extends HttpServlet {
    private CouponDAO couponDAO = new CouponDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String actionType = req.getParameter("actionType");


        try {
            String couponCode = req.getParameter("couponCode");
            String discountType = req.getParameter("discountType");
            String description = req.getParameter("description");
            String status = req.getParameter("status");

            // Kiểm tra các tham số bắt buộc không được null
            if (couponCode == null || discountType == null || description == null || status == null ||
                    req.getParameter("discountAmount") == null || req.getParameter("startDate") == null ||
                    req.getParameter("endDate") == null || req.getParameter("minOrderAmount") == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thông tin voucher không đầy đủ!");
                return;
            }

            Coupon coupon = new Coupon();
            coupon.setCouponCode(couponCode);
            coupon.setDiscountType(discountType);
            coupon.setDescription(description);
            coupon.setStatus(status);

            // Parse và kiểm tra các giá trị số
            coupon.setDiscountAmount(Double.parseDouble(req.getParameter("discountAmount")));
            coupon.setMinOrderAmount(Double.parseDouble(req.getParameter("minOrderAmount")));

            // Ngày tháng hợp lệ
            coupon.setStartDate(Timestamp.valueOf(req.getParameter("startDate") + " 00:00:00"));
            coupon.setEndDate(Timestamp.valueOf(req.getParameter("endDate") + " 23:59:59"));

            // Các tham số Optional (kiểm tra rỗng thì set null)
            coupon.setMaxDiscountAmount(req.getParameter("maxDiscountAmount").isEmpty() ? null : Double.parseDouble(req.getParameter("maxDiscountAmount")));
            coupon.setMaxUsage(req.getParameter("maxUsage").isEmpty() ? null : Integer.parseInt(req.getParameter("maxUsage")));
            coupon.setMaxUsagePerUser(req.getParameter("maxUsagePerUser").isEmpty() ? 1 : Integer.parseInt(req.getParameter("maxUsagePerUser")));

            // Mặc định khi thêm mới
            if ("add".equals(actionType)) {
                coupon.setUsedCount(0);
                couponDAO.createCoupon(coupon);
            }
            // Chỉnh sửa coupon đã tồn tại
            else if ("edit".equals(actionType)) {
                String couponIdStr = req.getParameter("couponId");
                if (couponIdStr == null || couponIdStr.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu couponId khi cập nhật!");
                    return;
                }

                coupon.setId(Integer.parseInt(couponIdStr));

                // Lấy usedCount cũ từ DB để giữ nguyên
                Coupon existingCoupon = couponDAO.getCouponById(coupon.getId());
                if (existingCoupon != null) {
                    coupon.setUsedCount(existingCoupon.getUsedCount());
                    couponDAO.updateCoupon(coupon);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy voucher cần cập nhật!");
                    return;
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thao tác không hợp lệ!");
                return;
            }

            // Redirect về trang quản lý voucher

            resp.sendRedirect(req.getContextPath() + "/admin.jsp");


        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu số không hợp lệ!");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu ngày tháng không hợp lệ!");
            e.printStackTrace();
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xử lý voucher!");
            e.printStackTrace();
        }
    }
}