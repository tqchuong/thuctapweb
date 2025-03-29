package fit.hcmuaf.edu.vn.foodmart.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet(name = "CaptchaServlet", value = "/CaptchaServlet")
public class CaptchaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Tạo mã CAPTCHA ngẫu nhiên
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            captcha.append(chars.charAt((int)(Math.random() * chars.length())));
        }

        // Lưu mã CAPTCHA vào session
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captcha.toString());

        // Tạo hình ảnh CAPTCHA
        int width = 150;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // Vẽ nền
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // Vẽ các đường nhiễu ngẫu nhiên
        g.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = (int)(Math.random() * width);
            int y1 = (int)(Math.random() * height);
            int x2 = (int)(Math.random() * width);
            int y2 = (int)(Math.random() * height);
            g.drawLine(x1, y1, x2, y2);
        }

        // Vẽ mã CAPTCHA
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(captcha.toString(), 30, 35);

        // Xuất hình ảnh
        response.setContentType("image/jpeg");
        ImageIO.write(image, "jpeg", response.getOutputStream());
        g.dispose();
    }
}
