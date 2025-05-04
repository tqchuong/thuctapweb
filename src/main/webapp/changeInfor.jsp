<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Thông tin tài khoản</title>
    <link href="css/products.css" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
</head>

<body>
<jsp:include page="header.jsp"/>
<nav class="header-bottom">
    <div class="container">
        <ul class="menu-list">
            <li class="menu-list-item">
                <a class="menu-link" href="home.jsp">
                    <i class="fa-sharp fa-regular fa-house " style="color: #B5292F;"></i>
                    Trang chủ
                </a>
            </li>


            <li class="menu-list-item">
                <a class="menu-Category" href="products">
                    <i class="fa-solid fa-shop" style="padding-right: 5px;color: #B5292F;"></i>
                    Sản phẩm
                    <i class="fa-solid fa-caret-down"></i>
                </a>
            </li>
            <li class="menu-list-item"><a class="hotpro-link" href="hotproducts.jsp">
                <i class="fa-solid fa-fire fa-shake" style="color: #f00505;"></i>
                hot</a>
            </li>

            <li class="menu-list-item"><a class="hotpro-link" href="flashsale">
                <i class="fa-solid fa-bolt fa-shake" style="color: #FFD700;"></i> Flashsale
            </a>
            </li>

        </ul>

    </div>
</nav>

<main class="main-wrapper">


    <div class="container" id="account-user">
        <div class="main-account">
            <div class="main-account-header">
                <h3>Thông tin tài khoản của bạn</h3>
                <p>Quản lý thông tin để bảo mật tài khoản</p>
            </div>
            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <div style="color: red;  text-align: center; margin-top: 20px">${error}</div>
                <br>
            </c:if>
            <div class="main-account-body">

                <div class="main-account-body-col">

                    <form action="change?action=infor" method="post" class="info-user">

                        <!-- Các trường thông tin -->
                        <div class="form-group">
                            <label for="infoname" class="form-label">Họ và tên</label>
                            <input class="form-control" type="text" name="fullName" id="infoname"
                                   placeholder="Thêm họ và tên"
                                   value="${not empty sessionScope.auth.fullName ? sessionScope.auth.fullName : ''}">
                        </div>
                        <div class="form-group">
                            <label for="infophone" class="form-label">Số điện thoại</label>
                            <input class="form-control" type="text" name="phone" id="infophone"
                                   placeholder="Thêm số điện thoại"
                                   value="${not empty sessionScope.auth.phone ? sessionScope.auth.phone : ''}">
                        </div>
                        <div class="form-group">
                            <label for="infoemail" class="form-label">Email</label>
                            <input class="form-control" type="email" name="email" id="infoemail"
                                   placeholder="Thêm địa chỉ email của bạn"
                                   value="${not empty sessionScope.auth.email ? sessionScope.auth.email : ''}">
                        </div>
                        <div class="form-group">
                            <label for="infoaddress" class="form-label">Địa chỉ</label>
                            <input class="form-control" type="text" name="address" id="infoaddress"
                                   placeholder="Thêm địa chỉ giao hàng của bạn"
                                   value="${not empty sessionScope.auth.address ? sessionScope.auth.address : ''}">
                        </div>
                        <button id="save-info-user" type="submit"><i
                                class="fa-regular fa-floppy-disk"></i> Lưu thay đổi
                        </button>
                    </form>
                </div>

                <div class="main-account-body-col">
                    <form action="change?action=password" method="post" class="change-password">

                        <!-- Các trường mật khẩu -->
                        <div class="form-group">
                            <label for="username-info" class="form-label">Tên đăng nhập</label>
                            <input class="form-control" type="text" name="username" id="username-info"
                                   placeholder="Nhập tên đăng nhập">
                        </div>
                        <div class="form-group">
                            <label for="password-cur-info" class="form-label">Mật khẩu hiện tại</label>
                            <input class="form-control" type="password" name="passwordCur" id="password-cur-info"
                                   placeholder="Nhập mật khẩu hiện tại">
                        </div>
                        <div class="form-group">
                            <label for="password-after-info" class="form-label">Mật khẩu mới</label>
                            <input class="form-control" type="password" name="passwordNew" id="password-after-info"
                                   placeholder="Nhập mật khẩu mới">
                        </div>
                        <div class="form-group">
                            <label for="password-comfirm-info" class="form-label">Xác nhận mật khẩu mới</label>
                            <input class="form-control" type="password" name="passwordConfirm"
                                   id="password-comfirm-info"
                                   placeholder="Nhập lại mật khẩu mới">
                        </div>
                        <button id="save-password" type="submit"><i class="fa-regular fa-key"></i> Đổi
                            mật khẩu
                        </button>
                    </form>
                </div>
            </div>

        </div>
    </div>


</main>

<jsp:include page="footer.jsp"/>

</body>

</html>