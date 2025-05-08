<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  fit.hcmuaf.edu.vn.foodmart.model.Account acc = (fit.hcmuaf.edu.vn.foodmart.model.Account) session.getAttribute("account");
  if (acc == null) {
    response.sendRedirect("login.jsp"); // Không có dữ liệu -> quay lại login
    return;
  }
%>
<html>
<head>
    <title>Hoàn tất đăng ký</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<!--=============== LOGIN IMAGE ===============-->
<svg class="login__blob" viewBox="0 0 566 840" xmlns="http://www.w3.org/2000/svg">
    <mask id="mask0" mask-type="alpha">
        <path d="M342.407 73.6315C388.53 56.4007 394.378 17.3643 391.538 0H566V840H0C14.5385 834.991 100.266 804.436 77.2046 707.263C49.6393 591.11 115.306 518.927 176.468 488.873C363.385 397.026 156.98 302.824 167.945 179.32C173.46 117.209 284.755 95.1699 342.407 73.6315Z" />
    </mask>
    <g mask="url(#mask0)">
        <path d="M342.407 73.6315C388.53 56.4007 394.378 17.3643 391.538 0H566V840H0C14.5385 834.991 100.266 804.436 77.2046 707.263C49.6393 591.11 115.306 518.927 176.468 488.873C363.385 397.026 156.98 302.824 167.945 179.32C173.46 117.209 284.755 95.1699 342.407 73.6315Z" />
        <!-- Insert your image (recommended size: 1000 x 1200) -->
        <image class="login__img" href="image/banner/luongthuc.png" />
    </g>
</svg>
<div class="login container grid" id="loginAccessRegister">
    <div id="loginForm" class="login__access" >
        <h1 class="login__title">Hoàn tất đăng ký.</h1>
        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty error}">
            <div style="color: red;">${error}</div>
            <br>
        </c:if>
        <div class="login__area">
            <form action="completeRegister" method="post" class="login__form">
                <div class="login__content grid">

                    <%
                        String email = acc.getEmail();
                        if (email != null && !email.isEmpty()) {
                    %>

                    <div class="login__box">
                        <input type="text" id="mail" name="email"  class="login__input" value="<%= email %>" readonly>
                        <label for="login-name" class="login__label">Email</label>
                        <i class="ri-mail-fill login__icon"></i>
                        <span class="form-message namelogin"></span>
                    </div>
                    <%
                    } else {
                    %>
                    <div class="login__box">
                        <input type="text" id="email" name="email" required placeholder=" "  class="login__input">
                        <label for="login-name" class="login__label">Email</label>
                        <i class="ri-mail-fill login__icon"></i>
                        <span class="form-message namelogin"></span>
                    </div>
                    <%
                        }
                    %>

                    <input type="hidden" name="fullName" value="<%= acc.getName() %>">
                    <div class="login__box">
                        <input type="text" id="login-name" name="username" required placeholder=" "  class="login__input" >
                        <label for="login-name" class="login__label">Tên đăng nhập</label>
                        <i class="ri-mail-fill login__icon"></i>
                        <span class="form-message namelogin"></span>
                    </div>
                    <div class="login__box">
                        <input type="text" id="password" name="password" required placeholder=" " class="login__input" >
                        <label for="password" class="login__label">Mật khẩu</label>
                        <i class="ri-eye-off-fill login__icon login__password" id="loginPassword"></i>
                        <span class="form-message-check-login form-message"></span>
                    </div>
                    <div class="login__box">
                        <input type="text" id="passwordConfirm" name="passwordConfirm" required placeholder=" " class="login__input" aria-label="Password">
                        <label for="passwordConfirm" class="login__label">Xác nhận mật khẩu</label>
                        <i class="ri-eye-off-fill login__icon login__password" id="loginpasswordConfirm"></i>
                        <span class="form-message-password form-message"></span>
                    </div>

                    <input type="hidden" name="loginType" value="<%= acc.getLoginType() %>">

                </div>

                <button type="submit" id="login-button" class="login__button" value="login">Hoàn tất</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
