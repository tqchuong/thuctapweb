package fit.hcmuaf.edu.vn.foodmart.controller;

import fit.hcmuaf.edu.vn.foodmart.model.Users;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebFilter(urlPatterns = { "/admin.jsp"}) // Áp dụng cho tất cả URL trong thư mục /admin
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        if (session != null) {
            Object currentUser = session.getAttribute("auth");
            if (currentUser != null && currentUser instanceof Users) {
                Users user = (Users) currentUser;

                // Kiểm tra quyền truy cập
                if ("Admin".equals(user.getRole())) {
                    chain.doFilter(request, response); // Cho phép truy cập
                    return;
                }
            }
        }

        // Nếu không phải admin, chuyển hướng về trang lỗi hoặc đăng nhập
        res.sendRedirect( "login.jsp");
    }
}
