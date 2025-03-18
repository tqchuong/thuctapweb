package fit.hcmuaf.edu.vn.foodmart.controller;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import fit.hcmuaf.edu.vn.foodmart.model.VNPayService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vnpay-payment")
public class VNPayController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentUrl = VNPayService.createPaymentUrl(req);

        JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);

        resp.getWriter().write(new Gson().toJson(job));
    }
}
