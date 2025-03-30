<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Products" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.ProductImages" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Reviews" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FoodMart Store</title>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link
        href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&family=Open+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap"
        rel="stylesheet">

    <link rel="stylesheet" href="css/productdetails.css">
    <link rel="stylesheet" href="css/products.css">
    <link rel="stylesheet" href="css/home.css">



</head>
<body>
    <jsp:include page="header.jsp"/>
    <nav class="header-bottom">
        <div class="container">
            <ul class="menu-list">
                <li class="menu-list-item">
                    <a class="menu-link" href="home.jsp">
                        <i class="fa-sharp fa-regular fa-house" style="color: #B5292F;"></i>
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

                <li class="menu-list-item"><a class="flashsale-link" href="flash-sale">
                    <i class="fa-solid fa-bolt fa-shake" style="color: #FFD700;"></i> Flashsale
                </a>
                </li>

            </ul>

        </div>
    </nav>
    <section id="selling-product" class="single-product mt-0 mt-md-5">
        <div class="container-fluid">
            <div class="product-details">
                <div class="row">
                    <div class="product-images col-md-6 d-flex flex-column align-items-center">
                        <div class="row flex-column-reverse flex-lg-row">
                            <div class="col-md-12 col-lg-2">
                                <div class="product-thumbnails">
                                    <c:choose>
                                        <c:when test="${not empty product.images}">
                                            <c:forEach var="image" items="${product.images}" varStatus="loop">
                                                <img src="${image.imagePath}" alt="Thumbnail ${loop.index + 1}"
                                                     class="thumb-image img-fluid"
                                                     onclick="changeImage('${image.imagePath}')"
                                                     data-large-img="${image.imagePath}">
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="col-md-12 col-lg-10">
                                <div class="product-main-image">
                                    <c:choose>
                                        <c:when test="${not empty product.imageURL}">
                                            <img id="mainImage" src="${product.imageURL}" alt="Main Product" class="img-fluid"
                                                 data-large-img="${product.imageURL}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/images/default-main-image.jpg" alt="No Image Available">
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="zoom-lens"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="product-info col-md-5">
                        <div class="product-info-content">
                            <div class="recommendation pt-3">
                                <c:choose>
                                    <c:when test="${averageRating > 4}">
                                        <span class="tag-likes">Yêu Thích</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="tag-likes" style="visibility: hidden;">Yêu Thích</span> <!-- Ẩn tag nếu sao không lớn hơn 4 -->
                                    </c:otherwise>
                                </c:choose>

                                <div class="recommended-product">
                                    <div class="element-header">
                                        <h2 itemprop="name" class="display-6">${product.productName}</h2>
                                        <div class="rating-container d-flex align-items-center">
                                        <span class="rating-score">
                                            <fmt:formatNumber type="number" maxFractionDigits="1" value="${averageRating}" />
                                        </span>
                                            <div class="rating">
                                                <c:forEach var="i" begin="1" end="5">
                                                    <c:choose>
                                                        <c:when test="${averageRating >= i}">
                                                            <span class="text-primary star-icon">&#9733;</span>
                                                        </c:when>
                                                        <c:when test="${averageRating >= i - 0.5}">
                                                            <span class="text-half-primary star-icon">&#9733;</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted star-icon">&#9734;</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </div>
                                            <div class="separator"></div>
                                            <span class="review-count">${reviewCount} Đánh Giá</span>
                                            <div class="separator"></div>
                                            <span class="sales-count ms-3">${viewCount} Lượt Xem</span>
                                        </div>
                                        <div class="product-price-container">
                                            <div class="product-price">
                                                <c:choose>
                                                    <c:when test="${product.discountPercentage > 0}">
                                                        <del class="old-price">
                                                            <fmt:formatNumber type="number" pattern="#,##0" value="${product.price}" /> đ
                                                        </del>
                                                        <strong class="current-price text-danger">
                                                            <fmt:formatNumber type="number" pattern="#,##0"
                                                                              value="${product.price * (1 - product.discountPercentage / 100)}" /> đ
                                                        </strong>
                                                        <span class="discount">-${product.discountPercentage}%</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <strong class="current-price">
                                                            <fmt:formatNumber type="number" pattern="#,##0" value="${product.price}" /> đ
                                                        </strong>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                        </div>
                                        <p>${product.shortDescription}</p>
                                        <div class="purchase-options">
                                            <div class="quantity-group">
                                                <label for="quantity">Số Lượng:</label>
                                                <div class="quantity-control">
                                                    <button type="button" class="quantity-left-minus btn" onclick="updateQuantity(-1)">-</button>
                                                    <input type="text" id="quantity" name="quantity" class="quantity-input" value="1" min="1" max="${product.warehouse.quantity}" readonly>
                                                    <button type="button" class="quantity-right-plus btn" onclick="updateQuantity(1)">+</button>
                                                </div>
                                                <div class="stock-number">Còn ${product.warehouse.quantity} sản phẩm</div>
                                            </div>
                                            <div class="button-group">
                                                <button type="button" class="btn btn-secondary add-to-cart"
                                                        onclick="addToCart(${product.ID})">
                                                    <i class="fas fa-shopping-cart"></i> Thêm Vào Giỏ Hàng
                                                </button>

                                                <div id="userStatus" data-is-logged-in="${sessionScope.auth != null ? 'true' : 'false'}" style="display: none;"></div>

                                                <button type="button" class="btn btn-primary buy-now" onclick="buyNow(${product.ID})">Mua Ngay</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="product-info-tabs py-5">
        <div class="container-fluid">
            <div class="row">
                <div class="d-flex flex-column flex-md-row align-items-start gap-5">
                    <div class="nav flex-row flex-wrap flex-md-column nav-pills me-3 col-lg-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                        <button class="nav-link text-start active" id="v-pills-description-tab" data-bs-toggle="pill" data-bs-target="#v-pills-description" type="button" role="tab" aria-controls="v-pills-description" aria-selected="true">Mô tả sản phẩm</button>
                        <button class="nav-link text-start" id="v-pills-additional-tab" data-bs-toggle="pill" data-bs-target="#v-pills-additional" type="button" role="tab" aria-controls="v-pills-additional" aria-selected="false">Thông tin bổ sung</button>
                        <button class="nav-link text-start" id="v-pills-reviews-tab" data-bs-toggle="pill" data-bs-target="#v-pills-reviews" type="button" role="tab" aria-controls="v-pills-reviews" aria-selected="false">Đánh giá</button>
                    </div>
                    <div class="tab-content" id="v-pills-tabContent">
                        <div class="tab-pane fade show active" id="v-pills-description" role="tabpanel" aria-labelledby="v-pills-description-tab" tabindex="0">
                            <h5>Chi tiết sản phẩm</h5>
                            <p>${product.productsDetail.detailedDescription}</p>
                        </div>
                        <div class="tab-pane fade" id="v-pills-additional" role="tabpanel" aria-labelledby="v-pills-additional-tab" tabindex="0">
                            <p>Thông tin sản phẩm</p>
                            <ul style="list-style-type:disc;" class="list-unstyled ps-4">
                                <li>Tên sản phẩm: ${product.productName}</li>
                                <li>Danh Mục: ${product.category.categoryName}</li>
                                <li>Trọng lượng: ${product.weight}</li>
                                <li>Số lượng trong kho: ${product.warehouse.quantity}</li>
                                <li>Ngày hết hạn: <fmt:formatDate pattern="dd-MM-yyyy" value="${product.productsDetail.expiryDate}" /></li>
                            </ul>
                        </div>
                        <div class="tab-pane fade" id="v-pills-reviews" role="tabpanel" aria-labelledby="v-pills-reviews-tab" tabindex="0">
                            <div class="review-box d-flex flex-wrap">
                                <c:if test="${not empty product.reviews}">
                                    <c:forEach var="review" items="${product.reviews}">
                                        <div class="col-lg-6 d-flex flex-wrap gap-3">
                                            <div class="col-md-2">

                                            </div>
                                            <div class="col-md-8">
                                                <div class="review-content">
                                                    <div class="rating-container d-flex align-items-center">
                                                        <c:forEach var="i" begin="1" end="5">
                                                            <c:choose>
                                                                <c:when test="${review.rating >= i}">
                                                                    <span class="text-primary" style="font-size: 22px;">&#9733;</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-muted" style="font-size: 22px;">&#9734;</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                        <span class="rating-count">(${review.rating})</span>
                                                    </div>
                                                    <div class="review-header">
                                                        <span class="author-name">${review.user.username}</span>
                                                        <c:if test="${review.createdAt != null}">
                                                            <span class="review-date">– <fmt:formatDate pattern="dd/MM/yyyy" value="${review.createdAt}" /></span>
                                                        </c:if>
                                                    </div>
                                                    <p>${review.reviewText}</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty product.reviews}">
                                    <p>Không có đánh giá nào.</p>
                                </c:if>
                            </div>

                            <div class="add-review mt-5">
                                <h3>Đánh giá của bạn</h3>
                                <p>Vui lòng điền những dòng *</p>
                                <form action="review" method="post" class="form-group" name="form">
                                <input type="hidden" name="productId" value="${product.ID}">
                                    <input type="hidden" name="rating" id="ratingInput" value="0">
                                    <input type="hidden" name="userId" value="${auth.id}">
                                    <div class="stars" id="starRating">
                                        <span data-value="1">&#9733;</span>
                                        <span data-value="2">&#9733;</span>
                                        <span data-value="3">&#9733;</span>
                                        <span data-value="4">&#9733;</span>
                                        <span data-value="5">&#9733;</span>
                                    </div>
                                    <p>Đánh giá: <span id="ratingValue">0</span> sao</p>
                                    <div class="pb-3">
                                        <label>Thêm nhận xét *</label>
                                        <textarea class="form-control" name="reviewText" placeholder="Thêm nhận xét của bạn"></textarea>
                                    </div>
                                    <button type="submit" name="submit" class="btn btn-dark btn-large text-uppercase w-100">Gửi</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="related-products" class="py-5">
        <div class="container">
            <h2 class="section-title text-center mb-4">Sản phẩm tương tự</h2>
            <div class="row">
                <c:forEach var="relatedProduct" items="${randomRelatedProducts}">
                    <div class="col-md-4">
                        <div class="card">
                            <a href="productDetails?id=${relatedProduct.ID}" title="${relatedProduct.productName}">
                                <img src="${relatedProduct.imageURL}" class="card-img-top" alt="${relatedProduct.productName}">
                                <div class="card-body">
                                    <h5 class="card-title">${relatedProduct.productName}</h5>
                                    <span class="price"><fmt:formatNumber value="${relatedProduct.price}" type="currency" currencySymbol="₫" /></span>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/home.js"></script>
    <script src="js/productdetails.js"></script>


</body>

</html>