<%@ page import="java.sql.*, javax.naming.*, javax.sql.DataSource" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.UserDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            <a href="${pageContext.request.contextPath}/resendVerification?email=${param.email}" class="verify__link">
                gửi lại email xác thực.
            </a>

        </p>

    </div>

    <jsp:include page="footer.jsp"/>
</div>

<script src="js/login.js"></script>
<script src="js/home.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        function checkVerification() {
            var email = "${param.email}";

            fetch("checkVerification?email=" + encodeURIComponent(email))
                .then(response => response.json())
                .then(data => {
                    if (data.isVerified) {
                        document.getElementById("verifyAccountForm").innerHTML = `
                        <h1 class="verify__title">Tài khoản đã được xác thực <i class="fa-solid fa-check" style="color: #d60505;"></i></h1>
                        <p class="verify__message">Bạn có thể đăng nhập ngay bây giờ.</p>
                    `;
                    }
                })
                .catch(error => console.error("Lỗi khi kiểm tra xác thực:", error));
        }

        // Kiểm tra xác thực mỗi 3 giây
        setInterval(checkVerification, 3000);
    });
</script>
<script>
    <% if (request.getAttribute("message") != null) { %>
    alert("<%= request.getAttribute("message") %>");
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    alert("<%= request.getAttribute("error") %>");
    <% } %>
</script>


</body>
</html>
