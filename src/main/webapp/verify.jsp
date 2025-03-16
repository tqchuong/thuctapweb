<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--=============== CSS ===============-->
    <link rel="stylesheet" href="css/login.css">
    <title>Verify</title>
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
    <link rel="stylesheet" href="css/home.css">
    <link href="css/products.css" rel="stylesheet">
</head>
<body>

<jsp:include page="header.jsp"/>

<!--=============== LOGIN ===============-->
<div class="login container grid" id="loginAccessRegister">

    <!-- ===== CONFIRMATION FORM ===== -->
    <div id="verifyAccountForm" class="verify-form">
        <h1 class="verify__title">Xác thực tài khoản</h1>
        <p class="verify__message">
            Một email xác thực đã được gửi đến <strong>${param.email}</strong>.
            Vui lòng kiểm tra hộp thư và nhấp vào liên kết để hoàn tất quá trình xác thực tài khoản.
        </p>

        <!-- Hiệu ứng loading -->
        <div class="loading-container">
            <div class="loading-spinner"></div>
            <p>Đang chờ xác thực...</p>
        </div>

        <p class="verify__note">
            Nếu bạn không nhận được email, hãy kiểm tra thư mục spam hoặc
            <a href="resendVerification?email=${param.email}" class="verify__link">gửi lại email xác thực</a>.
        </p>
    </div>


    <jsp:include page="footer.jsp"/>
</div>
<!--=============== MAIN JS ===============-->
<script src="js/login.js"></script>
<script src="js/home.js"></script>
</body>
</html>