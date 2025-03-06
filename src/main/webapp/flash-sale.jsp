<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>FoodMart Store</title>
    <link href="css/products.css" rel="stylesheet">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
    <link rel="stylesheet" href="css/flash-sale.css">
    <link rel="stylesheet" href="css/home.css">


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
                <ul class="dropdown-menu">
                    <li class="menu-list-item" onclick="showCategory('Tất cả')"><a class="menu-link" href="javascript:">Tất cả</a></li>
                    <li class="menu-list-item" onclick="showCategory('Gạo')"><a class="menu-link" href="javascript:">Gạo</a></li>
                    <li class="menu-list-item" onclick="showCategory('Khoai')"><a class="menu-link" href="javascript:">Khoai</a></li>
                    <li class="menu-list-item" onclick="showCategory('Bắp')"><a class="menu-link" href="javascript:">Bắp</a></li>
                    <li class="menu-list-item" onclick="showCategory('Khác')"><a class="menu-link" href="javascript:">Khác</a></li>

                </ul>
            </li>
            <li class="menu-list-item"><a class="hotpro-link" href="hotproducts.jsp">
                <i class="fa-solid fa-fire fa-shake" style="color: #f00505;"></i>
                hot</a>
            </li>

            <li class="menu-list-item"><a class="flashsale-link" href="flash-sale">
                <i class="fa-solid fa-bolt fa-shake" style="color: #FFD700;"></i> Flashsale
            </a>
            </li>

        </ul>

    </div>
</nav>
<div class="advanced-search">
    <div class="container">
        <div class="advanced-search-category">
            <span>Phân loại </span>
            <select id="advanced-search-category-select" name="" onchange="searchProducts()">
                <option>Tất cả</option>
                <option>Gạo</option>
                <option>Khoai</option>
                <option>Bắp</option>
                <option>Khác</option>
            </select>
        </div>
        <div class="advanced-search-price">
            <span>Giá từ</span>
            <input id="min-price" onchange="searchProducts()" placeholder="tối thiểu" type="number">
            <span>đến</span>
            <input id="max-price" onchange="searchProducts()" placeholder="tối đa" type="number">
            <button id="advanced-search-price-btn"><i class="fa-solid fa-magnifying-glass-dollar"></i></button>
        </div>
        <div class="advanced-search-control">
            <button id="sort-ascending" onclick="searchProducts(1)"><i class="fa-solid fa-arrow-up-short-wide"></i>
            </button>
            <button id="sort-descending" onclick="searchProducts(2)"><i
                    class="fa-solid fa-arrow-down-wide-short"></i>
            </button>
            <button id="reset-search" onclick="searchProducts(0)"><i class="fa-solid fa-arrow-rotate-right"></i>
            </button>
            <button onclick="closeSearchAdvanced()"><i class="fa-solid fa-xmark"></i></button>
        </div>
    </div>
</div>

<main>

    <!-- Flash Sale Section -->
    <div class="flash-sale-container">
        <!-- Countdown Timer -->
        <div class="countdown-timer">
            <h1><i class="lightning-icon"></i> Flash Sale <i class="lightning-icon"></i></h1>
            <p id="timer-message">Đang cập nhật...</p>
            <div id="timer">00:00:00</div>
        </div>

        <!-- Banner -->
        <div class="banner">
            <img src="image/banner/banner7.png" alt="Flash Sale Banner">
        </div>

        <!-- Time Slots -->
        <div class="flash-sale-timer">
            <div class="time-slots">
                <button class="slot-button" data-slot="slot9">
                    9:00<br><span class="slot-description">Đã kết thúc</span>
                </button>
                <button class="slot-button" data-slot="slot12">
                    12:00<br><span class="slot-description">Đã kết thúc</span>
                </button>
                <button class="slot-button" data-slot="slot16">
                    16:00<br><span class="slot-description">Đang diễn ra</span>
                </button>
                <button class="slot-button" data-slot="slot23">
                    23:00<br><span class="slot-description">Sắp diễn ra</span>
                </button>
            </div>
        </div>

        <!-- Category Filter -->
        <div class="categories">
            <button class="category-button" data-loai="all">Tất cả</button>
            <button class="category-button" data-loai="Gạo">Gạo</button>
            <button class="category-button" data-loai="Bắp">Bắp</button>
            <button class="category-button" data-loai="Khoai">Khoai</button>
            <button class="category-button" data-loai="Khác">Khác</button>
        </div>

        <div class="product-list">
            <c:forEach var="product" items="${flashSaleProducts}">
                <div class="col-product"
                     data-id="${product.ID}"
                     data-loai="${product.category.categoryName}"
                     data-slot="${product.sales.dataSaleSlot}">
                    <article class="card-product">
                        <div class="card-header">
                            <a href="productDetails?id=${product.ID}" class="card-image-link">
                                <img class="card-image" src="${product.imageURL}" alt="${product.productName}">
                            </a>
                        </div>
                        <div class="food-info">
                            <div class="card-content">
                                <div class="card-title">
                                    <a href="productDetails?id=${product.ID}" class="card-title-link">${product.productName}</a>
                                </div>
                            </div>
                            <div class="card-footer">
                                <div class="product-price">
                                    <c:choose>
                                        <c:when test="${product.discountPercentage > 0}">
                                            <div class="price-container">
                                    <span class="original-price">
                                        <fmt:formatNumber value="${product.price}" pattern="#,##0 ₫" />
                                    </span>
                                                <span class="discount-price">
                                        <fmt:formatNumber value="${product.price * (1 - product.discountPercentage / 100)}" pattern="#,##0 ₫"/>
                                    </span>
                                                <span class="discount-message">-${product.discountPercentage}%</span>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                <span class="original-price">
                                    <fmt:formatNumber value="${product.price}" pattern="#,##0 ₫" />
                                </span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product-buy">
                                    <button class="card-button order-item" data-slot="${product.sales.dataSaleSlot}" data-href="productDetails?id=${product.ID}">
                                        <i class="fa-solid fa-cart-plus"></i> Đặt hàng
                                    </button>
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            </c:forEach>

        </div>
    </div>




    <div class="page-nav" id="page-nav">
        <ul class="page-nav-list">
        </ul>
    </div>
</main>

    <footer class="footer">

    <div class="widget-area">
        <div class="container">
            <div class="widget-row">
                <div class="widget-row-col">
                    <div class="logo">
                        <a href="">
                            <img alt="FoodMart Logo" src="../image/shoppingcart/6.png"
                                 style="width: 250px; height: auto;">
                        </a>
                    </div>
                    <h4 style="padding: 5px 0 5px 10px">Kết nối với chúng tôi</h4>
                    <div class="widget-social">
                        <div class="widget-social-item">
                            <a href="">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                        </div>
                        <div class="widget-social-item">
                            <a href="">
                                <i class="fab fa-twitter"></i>
                            </a>
                        </div>
                        <div class="widget-social-item">
                            <a href="">
                                <i class="fab fa-linkedin-in"></i>
                            </a>
                        </div>
                        <div class="widget-social-item">
                            <a href="">
                                <i class="fab fa-whatsapp"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="widget-row-col">
                    <h3 class="widget-title">Liên hệ</h3>
                    <p><b>Trụ sở chính:</b> VQCR+GP6, khu phố 6, Thủ Đức, Hồ Chí Minh, Việt Nam</p>
                    <p><b>Điện thoại:</b> 0123 456 789 </p>
                    <p><b>Fax:</b>  1234 567 890</p>
                    <p><b>Email:</b> abc@domain.com</p>
                </div>
                <div class="widget-row-col">
                    <h3 class="widget-title">Chính sách</h3>
                    <ul class="widget-contact">
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Chính sách thanh toán</span>
                            </a>
                        </li>
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Chính sách giao hàng</span>
                            </a>
                        </li>
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Chính sách đổi trả</span>
                            </a>
                        </li>
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Chính sách xuất hoá đơn GTGT</span>
                            </a>
                        </li>

                    </ul>
                </div>

                <div class="widget-row-col">
                    <h3 class="widget-title">Chăm sóc khách hàng</h3>
                    <ul class="widget-contact">
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Điều khoản sử dụng</span>
                            </a>
                        </li>
                        <li class="widget-contact-item">
                            <a href="">
                                <i class="fa-solid fa-arrow-right"></i>
                                <span>Hướng dẫn mua hàng</span>
                            </a>
                        </li>

                    </ul>
                </div>


            </div>
        </div>
    </div>
</footer>
<div class="back-to-top">
    <a href="#"><i class="fa-solid fa-arrow-up"></i></a>
</div>

<div class="chat-box">
    <a href="#">💬</a>
</div>
    <script src="js/flash-sale.js"></script>
<!--<script src="../js/home.js"></script>-->




</body>

</html>