<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.BrandAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Products" %>
<%@ page import="java.util.*" %>
<%
    String brandParam = request.getParameter("brand");
    String brandName = "";

    if (brandParam != null) {
        switch (brandParam.toLowerCase()) {
            case "abc": brandName = "ABC"; break;
            case "acecook": brandName = "Acecook"; break;
            case "vedan": brandName = "Vedan"; break;
            case "th": brandName = "TH True Milk"; break;
            case "hafarm": brandName = "HaFarm"; break;
            default: brandName = brandParam;
        }
    }

    BrandAdminDAO dao = new BrandAdminDAO();
    List<Products> allProducts = dao.getProductsByBrand(brandName);

    int pageSize = 8;
    int currentPage = 1;
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    int start = (currentPage - 1) * pageSize;
    int end = Math.min(start + pageSize, allProducts.size());
    List<Products> products = allProducts.subList(start, end);
    int totalPages = (int) Math.ceil((double) allProducts.size() / pageSize);

    request.setAttribute("products", products);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", currentPage);
    request.setAttribute("brandParam", brandParam);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sản phẩm theo thương hiệu - ${brandParam}</title>
    <link href="css/products.css" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <h2 style="color: #B5292F; margin-top: 20px">Thương hiệu: ${brandParam}</h2>
    <div class="home-products">
        <c:forEach var="product" items="${products}">
            <div class="col-product">
                <article class="card-product">
                    <div class="card-header">
                        <a href="productDetails?id=${product.ID}" class="card-image-link">
                            <img class="card-image" src="${product.imageURL}" alt="${product.productName}">
                        </a>
                    </div>
                    <div class="food-info">
                        <div class="card-content">
                            <div class="card-title">
                                <a href="productDetails?id=${product.ID}" class="card-title-link">
                                        ${product.productName}
                                </a>
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
        </c:forEach>

        <c:if test="${empty products}">
            <p>Không tìm thấy sản phẩm nào thuộc thương hiệu này.</p>
        </c:if>
    </divori

    <!-- Pagination -->
    <div class="page-nav">
        <ul class="page-nav-list">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="products-by-brand.jsp?brand=${brandParam}&page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<jsp:include page="footer.jsp"/>
<script src="js/home.js"></script>
</body>
</html>
