<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--=============== REMIXICONS ===============-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/remixicon/4.2.0/remixicon.css">
    <!--=============== CSS ===============-->
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
    <title>Login</title>
    <style>
        .login_gg_fb {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 5px;
        }

        .login_gg_fb i {
            color: #c20f0f;
            font-size: 20px;
        }
    </style>
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


<!--=============== LOGIN ===============-->
<div class="login container grid" id="loginAccessRegister">
    <%
        // Kiểm tra các thuộc tính và gán giá trị vào biến showForm
        String formType = "login"; // Mặc định hiển thị form login
        if (request.getAttribute("showRegisterForm") != null && (boolean) request.getAttribute("showRegisterForm")) {
            formType = "register";
        } else if (request.getAttribute("showForgotPasswordForm") != null && (boolean) request.getAttribute("showForgotPasswordForm")) {
            formType = "forgotPassword";
        }
    %>

    <!--===== LOGIN ACCESS =====-->
    <div id="loginForm" class="login__access" style="display: <%= "login".equals(formType) ? "block" : "none" %>;">
        <h1 class="login__title">Đăng nhập vào tài khoản của bạn.</h1>
        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty loginError}">
            <div style="color: red;">${loginError}</div>
            <br>
        </c:if>
        <div class="login__area">
            <form action="login?action=login" method="post" class="login__form">
                <div class="login__content grid">
                    <div class="login__box">
                        <input type="text" id="login-name" name="username" required placeholder=" "  class="login__input" >
                        <label for="login-name" class="login__label">Tên đăng nhập</label>
                        <i class="ri-mail-fill login__icon"></i>
                        <span class="form-message namelogin"></span>
                    </div>
                    <div class="login__box">
                        <input type="password" id="login-password" name="password" required placeholder=" " class="login__input" >
                        <label for="login-password" class="login__label">Password</label>
                        <i class="ri-eye-off-fill login__icon login__password" id="loginPassword"></i>
                        <span class="form-message-check-login form-message"></span>
                    </div>
                    <div class="form-group">
                        <div class="captcha-container">
                            <img src="CaptchaServlet" alt="CAPTCHA" id="captchaImage">
                            <button type="button" class="reload-btn" onclick="reloadCaptcha()"><i class="fa-solid fa-rotate-right"></i></button>
                            <input type="text" name="captcha" required placeholder="Nhập mã captcha" class="login__input" style="background-color: hsl(220, 50%, 97%);">
                        </div>
                    </div>
                </div>

                <button type="submit" id="login-button" class="login__button" value="login">Đăng nhập</button>
                <div class="login_gg_fb">
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/project/loginGoogle&response_type=code&client_id=677800086189-ntqafhoaddalkskih5jot4u56ngsa5ck.apps.googleusercontent.com&approval_prompt=force" class="login_gg" id="googleLogin"><i class="fa-brands fa-google"></i></a>

                    <a href="https://www.facebook.com/v19.0/dialog/oauth?fields=id,name,email&client_id=1136905471526375&redirect_uri=http://localhost:8080/project/loginFacebook" class="login_fb"><i class="fa-brands fa-facebook"></i></a>
                </div>
                <p>
                    <button id="forgotPasswordLink" class="forgotPasswordLink">Quên Mật khẩu?</button>
                </p>
            </form>
            <p class="login__switch">
                Chưa có tài khoản?
                <button id="loginButtonRegister">Tạo tài khoản</button>
            </p>
        </div>
    </div>

    <!--===== LOGIN REGISTER =====-->
    <div id="registerForm" class="login__register" style="display: <%= "register".equals(formType) ? "block" : "none" %>;">
        <h1 class="login__title">Tạo tài khoản mới.</h1>
        <div class="login__area">
            <form action="login?action=res" method="post" class="login__form">
                <c:if test="${not empty error}">
                    <div style="color: red;">${error}</div>
                </c:if>
                <div class="login__box">
                    <input type="text" id="names" name="username" required placeholder=" " class="login__input" aria-label="Names">
                    <label for="names" class="login__label">Tên đăng nhập</label>
                    <i class="ri-id-card-fill login__icon"></i>
                    <span class="form-message-name form-message"></span>
                </div>
                <div class="login__box">
                    <input type="text" id="phone" name="phone" required placeholder=" " class="login__input" aria-label="Phone Number">
                    <label for="phone" class="login__label">Số điện thoại</label>
                    <i class="ri-id-card-fill login__icon"></i>
                    <span class="form-message-phone form-message"></span>
                </div>
                <div class="login__box">
                    <input type="email" id="emailCreate" name="email" required placeholder=" " class="login__input" aria-label="Email">
                    <label for="emailCreate" class="login__label">Email</label>
                    <i class="ri-mail-fill login__icon"></i>
                    <span class="form-message-email form-message"></span>
                </div>
                <div class="login__box">
                    <input type="text" id="passwordCreate" name="password" required placeholder=" " class="login__input" aria-label="Password">
                    <label for="passwordCreate" class="login__label">Mật khẩu</label>
                    <i class="ri-eye-off-fill login__icon login__password" id="loginPasswordCreate"></i>
                    <span class="form-message-password form-message"></span>
                </div>
                <div class="login__box">
                    <input type="text" id="passwordConfirm" name="passwordConfirm" required placeholder=" " class="login__input" aria-label="Password">
                    <label for="passwordConfirm" class="login__label">Xác nhận mật khẩu</label>
                    <i class="ri-eye-off-fill login__icon login__password" id="loginpasswordConfirm"></i>
                    <span class="form-message-password form-message"></span>
                </div>
                <button type="submit" class="login__button" id="signup-button">Tạo tài khoản</button>
            </form>
            <p class="login__switch">
                Đã có tài khoản?
                <button id="loginButtonAccess">Đăng nhập</button>
            </p>
        </div>
    </div>


    <!-- ===== FORGOT PASSWORD ===== -->
    <div id="forgotPasswordForm" class="forgot-password" style="display: <%= "forgotPassword".equals(formType) ? "block" : "none" %>;">
        <h1 class="login__title">Quên Mật khẩu?</h1>
        <div class="login__area">
            <form action="login?action=forgetPass" method="post" class="login__form">
                <c:if test="${not empty errorMessage}">
                    <div style="color: red;">${errorMessage}</div>
                </c:if>
                <div class="login__box">
                    <!-- Username Field -->
                    <input type="text" id="usernameForgot" name="username" required placeholder=" " class="login__input" aria-label="Username">
                    <label for="usernameForgot" class="login__label">Tên đăng nhập</label>
                    <i class="ri-user-fill login__icon"></i>
                    <span class="form-message username"></span>
                </div>
                <div class="login__box">
                    <input type="email" id="emailForgot" name="email" required placeholder=" " class="login__input" aria-label="Email">
                    <label for="emailForgot" class="login__label">Email</label>
                    <i class="ri-mail-fill login__icon"></i>
                    <span class="form-message mail"></span>
                </div>
                <button type="submit" class="login__button">Đặt lại mật khẩu</button>
            </form>
            <p class="login__switch">
                Nhớ mật khẩu của bạn?
                <button id="backToLoginButton">Đăng nhập</button>
            </p>
        </div>
    </div>
</div>
<!--=============== MAIN JS ===============-->
<script src="js/login.js"></script>
<script src="js/home.js"></script>
<script>
    function reloadCaptcha() {
        var captchaImg = document.getElementById('captchaImage');
        // Thêm timestamp để buộc reload hình ảnh mới
        captchaImg.src = 'CaptchaServlet?' + new Date().getTime();
    }
</script>
<script src="https://accounts.google.com/gsi/client" async defer></script>
</body>
</html>