package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.Cart.Cart;
import fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Coupon;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "ApplyCouponServlet", value = "/apply-coupon")
public class ApplyCouponServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
        String couponCode = jsonObject.getString("couponCode");

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        double totalAmount = cart != null ? cart.getTotalAmount() : 0;

        CouponDAO couponDAO = new CouponDAO();
        Coupon coupon = couponDAO.getCouponByCode(couponCode);

        // Kiểm tra mã có tồn tại không
        if (coupon == null) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá không hợp lệ!\"}");
            return;
        }

        // Kiểm tra thời hạn
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.before(coupon.getStartDate()) || now.after(coupon.getEndDate())) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá đã hết hạn!\"}");
            return;
        }

        // Kiểm tra đơn tối thiểu
        if (totalAmount < coupon.getMinOrderAmount()) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Đơn hàng chưa đạt giá trị tối thiểu!\"}");
            return;
        }

        // Lưu vào session toàn bộ coupon
        req.getSession().setAttribute("appliedCoupon", coupon);

        // Nếu giảm cho tổng đơn thì tính luôn newTotal
        if ("TotalOrder".equals(coupon.getApplyTo())) {
            double discountAmount = coupon.getDiscountAmount();
            double newTotal = Math.max(0, totalAmount - discountAmount);

            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("discountAmount", discountAmount);
            result.put("newTotal", newTotal);
            result.put("applyTo", "TotalOrder");
            result.put("couponCode", couponCode);

            resp.getWriter().write(result.toString());
        } else {
            // Nếu giảm phí ship, không trừ tại đây
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("discountAmount", coupon.getDiscountAmount());
            result.put("applyTo", "ShippingFee");
            result.put("couponCode", couponCode);

            resp.getWriter().write(result.toString());
        }
    }
}
