package fit.hcmuaf.edu.vn.foodmart.model;


import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/vnpay-ipn")
public class VNPayIPNServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            fields.put(fieldName, fieldValue);
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

        String signValue = Config.hashAllFields(fields);
        JsonObject jsonResponse = new JsonObject();

        if (signValue.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
                jsonResponse.addProperty("RspCode", "00");
                jsonResponse.addProperty("Message", "Transaction successful");
            } else {
                jsonResponse.addProperty("RspCode", "01");
                jsonResponse.addProperty("Message", "Transaction failed");
            }
        } else {
            jsonResponse.addProperty("RspCode", "97");
            jsonResponse.addProperty("Message", "Invalid checksum");
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
