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
    <title>PasswordRecorvery</title>
</head>
<body>
<!--=============== LOGIN IMAGE ===============-->  <svg class="login__blob" viewBox="0 0 566 840" xmlns="http://www.w3.org/2000/svg">
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

    <div id="loginForm" class="login__access">
        <h1 class="login__title">Lấy lại Mật khẩu?</h1>
        <div class="login__area">
            <input type="hidden" name="username" value="<%= session.getAttribute("verifiedUser") %>">
            <form action="/updatePassword" method="post" class="login__form">
                <div class="login__box">
                    <input type="password" id="login-password" name="passwordNew" required placeholder=" " class="login__input" aria-label="New Password">
                    <label for="login-password" class="login__label">Mật khẩu mới </label>
                    <i class="ri-eye-off-fill login__icon login__password" ></i>
                    <span class="form-message-check-login form-message"></span>
                </div>
                <div class="login__box">
                    <input type="password" id="emailForgot" name="passwordConfirm" required placeholder=" " class="login__input" aria-label="Confirm Password">
                    <label for="emailForgot" class="login__label">Nhập lại mật khẩu</label>
                    <i class="ri-eye-off-fill login__icon login__password" ></i>
                    <span class="form-message-check-login form-message"></span>
                </div>
                <button type="submit" class="login__button">Đặt lại mật khẩu</button>
            </form>
        </div>
    </div>
</div>

<script>
    document.querySelector('.login__form').onsubmit = function(e) {
        const pass = document.querySelector('#login-password').value;
        const confirm = document.querySelector('#emailForgot').value;
        if (pass !== confirm) {
            e.preventDefault();
            alert('Mật khẩu không khớp!');
        }
    };
</script>
<script>
    document.querySelectorAll('.login__password').forEach(icon => {
        icon.addEventListener('click', () => {
            const input = icon.previousElementSibling;
            input.type = input.type === 'password' ? 'text' : 'password';
            icon.classList.toggle('ri-eye-off-fill');
            icon.classList.toggle('ri-eye-fill');
        });
    });
</script>
</body>
</html>