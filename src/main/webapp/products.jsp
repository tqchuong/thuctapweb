
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>FoodMart Store</title>
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
<main class="main-wrapper">
    <div class="container" id="trangchu">
        <div class="home-slider">
            <div class="slides">
                <img alt="" src="image/banner/Banner.png">
                <img alt="" src="image/banner/baner2.png">
            </div>
            <div class="controls">
                <button class="prev">❮</button>
                <button class="next">❯</button>
            </div>
        </div>
        <div class="home-service" id="home-service">
            <div class="home-service-item">
                <div class="home-service-item-icon">
                    <i class="fa-solid fa-truck-fast"></i>
                </div>
                <div class="home-service-item-content">
                    <h4 class="home-service-item-content-h">GIAO HÀNG NHANH</h4>
                    <p class="home-service-item-content-desc">Cho tất cả đơn hàng</p>
                </div>
            </div>
            <div class="home-service-item">
                <div class="home-service-item-icon">
                    <i class="fa-solid fa-shield-check"></i>
                </div>
                <div class="home-service-item-content">
                    <h4 class="home-service-item-content-h">SẢN PHẨM AN TOÀN</h4>
                    <p class="home-service-item-content-desc">Cam kết chất lượng</p>
                </div>
            </div>
            <div class="home-service-item">
                <div class="home-service-item-icon">
                    <i class="fa-duotone fa-solid fa-user-headset"></i>
                </div>
                <div class="home-service-item-content">
                    <h4 class="home-service-item-content-h">HỖ TRỢ 24/7</h4>
                    <p class="home-service-item-content-desc">Tất cả ngày trong tuần</p>
                </div>
            </div>
            <div class="home-service-item">
                <div class="home-service-item-icon">
                    <i class="fa-solid fa-hand-holding-dollar"></i>
                </div>
                <div class="home-service-item-content">
                    <h4 class="home-service-item-content-h">HOÀN LẠI TIỀN</h4>
                    <p class="home-service-item-content-desc">Nếu không hài lòng</p>
                </div>
            </div>
        </div>
        <div class="home-title-block" id="home-title">
            <h2 class="home-title">Khám phá sản phẩm của chúng tôi</h2>
        </div>

        <div class="home-products" id="home-products">
            <c:forEach var="product" items="${products}">
                <c:if test="${product.discountPercentage == 0.0}">
                    <div class="col-product" data-id="${product.ID}" data-loai="${product.category.categoryID}">
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
                                        <span class="current-price">${product.price}₫</span>
                                    </div>
                                    <div class="product-buy">
                                        <button class="card-button order-item" onclick="window.location.href='productDetails?id=${product.ID}'">
                                            <i class="fa-solid fa-cart-plus"></i> Đặt hàng
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </article>
                    </div>
                </c:if>
            </c:forEach>
        </div>


        <div class="page-nav" id="page-nav">
            <ul class="page-nav-list">
            </ul>
        </div>


    </div>


</main>

<jsp:include page="footer.jsp"/>

<script src="js/home.js"></script>


</body>

</html>