package fit.hcmuaf.edu.vn.foodmart.controller.admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Đọc JSON từ body
        JsonObject jsonObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        int userId = jsonObject.get("id").getAsInt();

        boolean isDeleted = new UserAdminDAO().deleteUserById(userId);

        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + isDeleted + "}");
    }
}
