<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Users" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.ProductDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Products" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header>

  <div class="header-middle">
    <div class="container">
      <div class="header-middle-left">

        <div class="logo">
          <a href="home.jsp">
            <img alt="FoodMart Logo" src="image/shoppingcart/6.png"
                 style="width: 250px; height: auto;">
          </a>

        </div>
      </div>
      <div class="header-middle-center">
        <form action="" class="form-search">
          <span class="search-btn"><i class="fa-solid fa-magnifying-glass"></i></span>
          <input class="form-search-input" oninput="searchProducts()" placeholder="Tìm kiếm sản phẩm..." type="text" >
          <button class="filter-btn"><i class="fa-solid fa-filter"></i><span>Lọc</span></button>
        </form>
      </div>
      <a href="coupon.jsp" class="discount-code-button">
        <div class="icon">
          <span class="vn">VND</span>
        </div>
        <span class="text">Mã Giảm Giá</span>
      </a>


      <div class="header-middle-right">
        <ul class="header-middle-right-list">
          <li class="header-middle-right-item dropdown open">
            <i class="fa-solid fa-user"></i>
            <div class="auth-container">

              <c:if test="${sessionScope.auth != null }">
                <h4 ><c:out value="${sessionScope.auth.username}"/> </h4>
                <ul class="header-middle-right-menu" id="auth-options">
                  <c:if test="${sessionScope.auth.role == 'Admin'
                              || sessionScope.auth.role == 'WarehouseManager'
                              || sessionScope.auth.role == 'OrderConfirmator'}">
                    <li><a href="admin.jsp"><i class="fa-solid fa-gear"></i> Quản lý cửa hàng</a></li>
                  </c:if>
                  <li><a href="changeInfor.jsp"  ><i class="fa-solid fa-user"></i> Tài khoản của tôi</a></li>
                  <li><a href="${pageContext.request.contextPath}/order-his"><i class="fa-solid fa-bag-shopping"></i> Đơn hàng đã mua</a></li>
                  <li class="border" style="display: flex; justify-content: center; align-items: center;">
                    <form action="login?action=logout" method="POST">
                      <button type="submit"
                              style="background: none; border: none; color: #333; font-size: 15.5px; cursor: pointer; padding: 0;">
                        <i class="fa-solid fa-right-from-bracket"></i>Thoát tài khoản
                      </button>
                    </form>
                  </li>
                </ul>
              </c:if>
              <c:if test="${sessionScope.auth == null }">
                <span class="text-dndk">Đăng nhập / Đăng ký</span>
                <span class="text-tk" id="user-fullname">Tài khoản <i
                        class="fa-sharp fa-solid fa-caret-down"></i></span>
                <!-- Menu sẽ được cập nhật bằng JavaScript -->
                <ul class="header-middle-right-menu" id="user-menu" style="display: none;"></ul>

                <!-- Các mục đăng nhập và đăng ký sẽ ẩn đi nếu người dùng đã đăng nhập -->
                <ul class="header-middle-right-menu" id="auth-options">
                  <li><a href="login.jsp?loginForm" id="login"><i
                          class="fa-solid fa-right-to-bracket"></i> Đăng nhập</a></li>
                  <li><a href="login.jsp?registerForm" id="signup"><i class="fa-solid fa-user-plus"></i>
                    Đăng ký</a></li>
                </ul>
              </c:if>
            </div>




          </li>
          <li class="header-middle-right-item open">
            <a href="shoppingcart.jsp">
              <div class="cart-icon-menu">
                <i class="fa-solid fa-basket-shopping"></i>
                <span class="count-product-cart">${sessionScope.cart.list.size()}</span>
              </div>
              <span style="color: #333">Giỏ hàng</span>
            </a>
          </li>
        </ul>
      </div>

    </div>
  </div>
</header>

