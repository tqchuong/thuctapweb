package fit.hcmuaf.edu.vn.foodmart.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("auth") != null) {
            Users user = (Users) session.getAttribute("auth");
            String username = user.getUsername();

            // Kiểm tra session còn tồn tại trong SessionManager hay không
            if (!SessionManager.isSessionValid(username, session)) {

                session.invalidate();  // Session không hợp lệ → logout ngay

                Cookie logoutCookie = new Cookie("roleChanged", "true");
                logoutCookie.setMaxAge(300);
                logoutCookie.setPath("/");
                resp.addCookie(logoutCookie);

                resp.sendRedirect(req.getContextPath() + "/login.jsp?message=" +
                        URLEncoder.encode("Phiên đăng nhập hết hạn hoặc quyền đã thay đổi.", StandardCharsets.UTF_8));
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
