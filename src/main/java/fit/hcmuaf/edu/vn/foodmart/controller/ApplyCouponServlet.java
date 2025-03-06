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

@WebServlet(name = "ApplyCouponServlet", value ="/apply-coupon")
public class ApplyCouponServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json"); // Đảm bảo trả về JSON
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        JSONObject jsonObject = new JSONObject(json);
        String couponCode = jsonObject.getString("couponCode");

        Cart cart = (Cart) req.getSession().getAttribute("cart");
        double totalAmount = cart != null ? cart.getTotalAmount() : 0;

        CouponDAO couponDAO = new CouponDAO();
        Coupon coupon = couponDAO.getCouponByCode(couponCode);

        if (coupon == null) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá không hợp lệ!\"}");
            return;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.before(coupon.getStartDate()) || now.after(coupon.getEndDate())) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Mã giảm giá đã hết hạn!\"}");
            return;
        }

        if (totalAmount < coupon.getMinOrderAmount()) {
            resp.getWriter().write("{\"success\": false, \"message\": \"Đơn hàng chưa đạt giá trị tối thiểu!\"}");
            return;
        }

        double discountAmount = coupon.getDiscountAmount();
        double newTotal = totalAmount - discountAmount;
        // Xóa totalAmount khỏi session trước khi set discountedTotal
        req.getSession().removeAttribute("totalAmount");
        req.getSession().setAttribute("discountedTotal", newTotal);

        resp.getWriter().write(String.format("{\"success\": true, \"discountAmount\": %.2f, \"newTotal\": %.2f}", discountAmount, newTotal));
    }

}
