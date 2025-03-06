package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO;
import fit.hcmuaf.edu.vn.foodmart.model.Reviews;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ReviewServlet", value = "/review")
public class ReviewServlet extends HttpServlet {

    private ProductDAO reviewDao = new ProductDAO(); // Khởi tạo ReviewDao

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("auth");

        if (user == null) {
            // Xử lý trường hợp người dùng chưa đăng nhập
            response.sendRedirect("login.jsp"); // Hoặc trang đăng nhập khác
            return;
        }

        try {
            // Lấy thông tin từ form đánh giá
            int rating = Integer.parseInt(request.getParameter("rating"));
            int productId = Integer.parseInt(request.getParameter("productId")); // Lấy productId từ form
            int userId = Integer.parseInt(request.getParameter("userId"));
            String reviewText = request.getParameter("reviewText");

            // Tạo đối tượng Reviews
            Reviews review = new Reviews();
            review.setProductID(productId);
            review.setUsersId(userId); // Lấy userId từ session
            review.setRating(rating);
            review.setReviewText(reviewText);

            // Thêm đánh giá vào database
            reviewDao.addReview(review);

            // Chuyển hướng người dùng về trang chi tiết sản phẩm (hoặc trang khác tùy ý)
            response.sendRedirect("productDetails?id=" + productId);
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi (ví dụ: hiển thị thông báo lỗi)
            response.sendRedirect("error.jsp"); // Hoặc trang lỗi khác
        }
    }
}