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
  <style>
    /* CSS cho lazy loading */
    img.lazy {
      opacity: 0;
      transition: opacity 0.3s ease;
      background-color: #f5f5f5;
    }

    img.lazy.loaded {
      opacity: 1;
    }

    /* Đảm bảo ảnh có kích thước cố định trước khi load */
    .card-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
    }
  </style>
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
    <!-- Banner - không lazy load vì quan trọng -->
    <div class="home-slider">
      <div class="slides">
        <img alt="" src="image/banner/Banner.png" loading="eager">
        <img alt="" src="image/banner/baner2.png" loading="eager">
      </div>
      <div class="controls">
        <button class="prev">❮</button>
        <button class="next">❯</button>
      </div>
    </div>

    <div class="home-service" id="home-service">
      <!-- Các service items không thay đổi -->
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
              <!-- Thêm lazy loading cho ảnh sản phẩm -->
              <img class="card-image lazy"
                   src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 300 200'%3E%3C/svg%3E"
                   data-src="<%= product.getImageURL() %>"
                   alt="<%= product.getProductName() %>"
                   loading="lazy"
                   width="300"
                   height="200">
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

<!-- Thêm script xử lý lazy loading -->
<script>
  document.addEventListener("DOMContentLoaded", function() {
    // Lazy loading với Intersection Observer
    if ('IntersectionObserver' in window) {
      const lazyImages = document.querySelectorAll('img.lazy');
      const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            const img = entry.target;
            // Thay thế placeholder bằng ảnh thật
            img.src = img.dataset.src;
            img.classList.add('loaded');
            // Ngừng quan sát sau khi đã tải
            observer.unobserve(img);
          }
        });
      }, {
        rootMargin: '200px 0px' // Tải trước khi vào viewport 200px
      });

      lazyImages.forEach(img => {
        imageObserver.observe(img);
      });
    } else {
      // Fallback cho trình duyệt cũ
      const lazyImages = document.querySelectorAll('img.lazy');
      lazyImages.forEach(img => {
        img.src = img.dataset.src;
        img.classList.add('loaded');
      });
    }
  });
</script>
<jsp:include page="footer.jsp"/>

<script src="js/home.js"></script>
</body>
</html>




