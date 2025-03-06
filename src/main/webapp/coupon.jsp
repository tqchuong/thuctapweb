<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Coupon" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách mã giảm giá</title>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/coupon.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css"/>





</head>
<body>
<!-- Header -->
<header class="header">
    <div class="logo">
        <a href="home.jsp" aria-label="Homepage">
            <img src="image/shoppingcart/6.png" alt="Store Logo"/>
        </a>
    </div>
    <div class="basket">
        <a href="shoppingcart.jsp" aria-label="Cart">
            <img src="image/shoppingcart/shoppingcart.png" alt="Basket Icon"/>
        </a>
    </div>
</header>
<!-- Banner Slider -->
<!-- Banner -->
<main class="main-wrapper">
    <div class="container" id="trangchu">
        <div class="home-slider">
            <div class="slider-images">
                <!-- Các hình ảnh banner -->
                <img alt="Banner 1" src="image/banner/Banner3.PNG" class="slider-image">
                <img alt="Banner 2" src="image/banner/Banner4.png" class="slider-image"
                     style="width: 40%; height: 100%;">
                <img alt="Banner 3" src="image/banner/Banner5.png" class="slider-image">
            </div>

            <!-- Điều khiển chuyển đổi banner -->
            <div class="slider-controls">
                <button class="prev">&lt;</button>
                <button class="next">&gt;</button>
            </div>
        </div>
    </div>
</main>
<div class="voucher-banner">
    <h2>VOUCHER HÔM NAY</h2>
</div>

<%
    CouponDAO couponDAO = new CouponDAO();
    List<Coupon> coupons = couponDAO.getActiveCoupons();
%>
<div class="voucher-container">
    <!-- Voucher mẫu 1 -->
    <% for (Coupon coupon : coupons) { %>
    <div class="coupon-card">
        <img src="image/shoppingcart/6.png" class="logo-voucher" alt="Voucher Logo"/>
        <h3><%= coupon.getDescription() %></h3>
        <div class="COUPON-ROW">
            <span id="cpnCode"><%= coupon.getCouponCode() %></span>
            <span id="cpnBtn">LƯU MÃ</span>
        </div>
        <p>Hết hạn: <%= coupon.getEndDate().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></p>
        <div class="circle1"></div>
        <div class="circle2"></div>
    </div>
    <% } %>
</div>
<jsp:include page="footer.jsp"/>


<script src="js/coupon.js"></script>
</body>
</html>