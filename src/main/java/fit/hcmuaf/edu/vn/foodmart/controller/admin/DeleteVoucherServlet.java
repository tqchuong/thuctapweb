package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete-voucher")
public class DeleteVoucherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Đọc JSON từ body
        JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();

        // Gọi DAO để xóa voucher
        boolean isDeleted = new CouponDAO().deleteCoupon(id);

        // Trả về JSON cho client
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + isDeleted + "}");
    }
}

