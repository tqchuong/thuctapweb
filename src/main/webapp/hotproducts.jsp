<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Products" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta content="IE=edge" http-equiv="X-UA-Compatible">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>FoodMart Store</title>
  <link href="css/products.css" rel="stylesheet">
  <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
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

      <li class="menu-list-item"><a class="hotpro-link" href="flash-sale">
        <i class="fa-solid fa-bolt fa-shake" style="color: #FFD700;"></i> Flashsale
      </a>
      </li>

    </ul>

  </div>
</nav>
<jsp:include page="advanced-search.jsp"/>
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
      <h2 class="home-title">Khám phá sản phẩm hot của chúng tôi</h2>
    </div>

    <%
      ProductDAO productDAO = new ProductDAO();
      List<Products> products = productDAO.getHotProducts(30);
    %>
    <div class="home-products" id="home-products">
      <% for (Products product : products) { %>
      <div class="col-product" data-id="<%= product.getID() %>" data-loai="<%= product.getCategoryID() %>">
        <article class="card-product">
          <div class="card-header">
            <a href="productDetails?id=<%= product.getID() %>" class="card-image-link">
              <img class="card-image" src="<%= product.getImageURL() %>" alt="<%= product.getProductName() %>">
            </a>
          </div>
          <div class="food-info">
            <div class="card-content">
              <div class="card-title">
                <a href="productDetails?id=<%= product.getID() %>" class="card-title-link"><%= product.getProductName() %></a>
              </div>
            </div>
            <div class="card-footer">
              <div class="product-price">
                <span class="current-price" style="text-decoration: line-through; color: #999;"><%= product.getPrice() %>₫</span>&nbsp;
                <span class="current-price"><%= Math.round((product.getPrice() * 0.85)/1000.0)*1000   %>₫</span>
              </div>
              <div class="product-buy">
                <button class="card-button order-item">
                  <i class="fa-solid fa-cart-plus"></i> Đặt hàng
                </button>
              </div>
            </div>
          </div>
        </article>
      </div>
      <% } %>
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