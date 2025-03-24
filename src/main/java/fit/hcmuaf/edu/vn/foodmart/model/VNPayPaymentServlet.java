package fit.hcmuaf.edu.vn.foodmart.model;


import com.google.gson.JsonObject;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name="VNPayPaymentServlet",value="/vnpay-payment")
public class VNPayPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderInfo = req.getParameter("orderInfo");
        String orderType = req.getParameter("orderType");
        String txnRef = Config.getRandomNumber(8);
        String ipAddr = Config.getIpAddress(req);
        String tmnCode = Config.vnp_TmnCode;

        int amount = Integer.parseInt(req.getParameter("amount")) * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "123");
        vnp_Params.put("vnp_OrderType", "344");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", ipAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        Calendar expire = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        expire.add(Calendar.MINUTE, 15); // Hết hạn sau 15 phút
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_ExpireDate", formatter1.format(expire.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(fieldValue).append('&');
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII)).append('&');
            }
        }


        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        query.append("vnp_SecureHash=").append(vnp_SecureHash);
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash); // Bổ sung SecureHash vào params
        System.out.println("Chuỗi hash trước khi mã hóa: " + hashData.toString());

        System.out.println("---- Dữ liệu đã gửi đến VNPay ----");

        System.out.println("vnp_TmnCode: " + vnp_Params.get("vnp_TmnCode"));
        System.out.println("vnp_TxnRef: " + vnp_Params.get("vnp_TxnRef"));
        System.out.println("vnp_Amount: " + vnp_Params.get("vnp_Amount"));
        System.out.println("vnp_OrderInfo: " + vnp_Params.get("vnp_OrderInfo"));
        System.out.println("vnp_IpAddr: " + vnp_Params.get("vnp_IpAddr"));
        System.out.println("vnp_CreateDate: " + vnp_Params.get("vnp_CreateDate"));
        System.out.println("vnp_SecureHash: " + vnp_Params.get("vnp_SecureHash"));
        System.out.println("----------------------------------");

        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
        JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);

        resp.sendRedirect(paymentUrl);

    }
}
