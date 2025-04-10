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
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int rating = Integer.parseInt(request.getParameter("rating"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String reviewText = request.getParameter("reviewText");

            // 🔍 Kiểm tra đã mua hàng chưa
            if (!reviewDao.hasUserPurchasedProduct(userId, productId)) {
                request.setAttribute("errorMessage", "Bạn chỉ có thể đánh giá sản phẩm đã mua.");
                request.getRequestDispatcher("productDetails?id=" + productId).forward(request, response);
                return;
            }

            Reviews review = new Reviews();
            review.setProductID(productId);
            review.setUsersId(userId);
            review.setRating(rating);
            review.setReviewText(reviewText);

            reviewDao.addReview(review);

            response.sendRedirect("productDetails?id=" + productId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}