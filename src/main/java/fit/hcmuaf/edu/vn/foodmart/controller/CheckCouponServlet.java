package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.model.Coupon;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(name = "CheckCouponServlet", value = "/check-coupon")
public class CheckCouponServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        Coupon coupon = (Coupon) req.getSession().getAttribute("appliedCoupon");

        if (coupon == null) {
            resp.getWriter().write("{\"success\": false}");
            return;
        }

        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("discountAmount", coupon.getDiscountAmount());
        result.put("applyTo", coupon.getApplyTo());
        result.put("couponCode", coupon.getCouponCode());

        resp.getWriter().write(result.toString());
    }
}