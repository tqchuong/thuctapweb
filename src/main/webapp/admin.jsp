<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.ProductAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/admin.css">
    <link href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/admin-responsive.css">
    <title>Quản lý cửa hàng</title>
</head>

<body>
<header class="header">
    <button class="menu-icon-btn">
        <div class="menu-icon">
            <i class="fa-regular fa-bars"></i>
        </div>
    </button>
</header>
<div class="container">
    <aside class="sidebar open">
        <div class="top-sidebar">
            <a href="#" class="channel-logo">
                <img src="image/shoppingcart/7.png" alt="Channel Logo">
            </a>
            <div class="hidden-sidebar your-channel">
                <img src="image/shoppingcart/8.png" alt="Your Channel">
            </div>
        </div>

        <div class="middle-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item tab-content active">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-house"></i></div>
                        <div class="hidden-sidebar">Trang tổng quan</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-pot-food"></i></div>
                        <div class="hidden-sidebar">Sản phẩm</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-users"></i></div>
                        <div class="hidden-sidebar">Khách hàng</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-basket-shopping"></i></div>
                        <div class="hidden-sidebar">Đơn hàng</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-chart-simple"></i></div>
                        <div class="hidden-sidebar">Thống kê</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-ticket"></i></div>
                        <div class="hidden-sidebar">Voucher</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fa-building"></i></div>
                        <div class="hidden-sidebar">Nhà cung cấp</div>
                    </a>
                </li>
                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fa-truck-fast"></i></div>
                        <div class="hidden-sidebar">Vận chuyển</div>
                    </a>
                </li>


            </ul>
        </div>
        <div class="bottom-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item user-logout">
                    <a href="home.jsp" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-thin fa-circle-chevron-left"></i></div>
                        <div class="hidden-sidebar">Trang chủ</div>
                    </a>
                </li>
                <li class="sidebar-list-item user-logout">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-light fa-circle-user"></i></div>
                        <div class="hidden-sidebar" id="name-acc"></div>
                    </a>
                </li>
                <li class="sidebar-list-item user-logout">
                    <a href="login.jsp" class="sidebar-link" id="logout-acc">
                        <div class="sidebar-icon"><i class="fa-light fa-arrow-right-from-bracket"></i></div>
                        <div class="hidden-sidebar">Đăng xuất</div>
                    </a>
                </li>
            </ul>
        </div>
    </aside>
    <main class="content">
        <div class="section active">
            <h1 class="page-title">Trang tổng quát của cửa hàng FOODMART</h1>
            <div class="cards">
                <div class="card-single">
                    <%
                        UserAdminDAO userAdminDAO = new UserAdminDAO();
                        int userCount = userAdminDAO.getUserCount();
                    %>
                    <div class="box">
                        <h2 id="amount-user"><%= userCount %>
                        </h2>
                        <div class="on-box">
                            <img src="image/admin/s1.png" alt="" style=" width: 200px;">
                            <h3>Khách hàng</h3>
                            <p>Sản phẩm là bất cứ cái gì có thể đưa vào thị trường để tạo sự chú ý, mua sắm, sử dụng
                                hay tiêu dùng nhằm thỏa mãn một nhu cầu hay ước muốn. Nó có thể là những vật thể,
                                dịch vụ, con người, địa điểm, tổ chức hoặc một ý tưởng.</p>
                        </div>

                    </div>
                </div>
                <%
                    ProductAdminDAO productAdminDAO = new ProductAdminDAO();
                    int productCount = productAdminDAO.getProductCount();
                    int sumRevenue = productAdminDAO.getSumRevenue();
                %>
                <div class="card-single">
                    <div class="box">
                        <div class="on-box">
                            <img src="image/admin/s2.png" alt="" style=" width: 200px;">
                            <h2 id="amount-product"><%= productCount %>
                            </h2>
                            <h3>Sản phẩm</h3>
                            <p>Khách hàng mục tiêu là một nhóm đối tượng khách hàng trong phân khúc thị trường mục
                                tiêu mà doanh nghiệp bạn đang hướng tới. </p>
                        </div>
                    </div>
                </div>
                <div class="card-single">
                    <div class="box">
                        <h2 id="doanh-thu"><fmt:formatNumber value="<%= sumRevenue %>" type="number" pattern="#,###"/>&nbsp;₫</h2>
                        <div class="on-box">
                            <img src="image/admin/s3.png" alt="" style=" width: 200px;">
                            <h3>Doanh thu</h3>
                            <p>Doanh thu của doanh nghiệp là toàn bộ số tiền sẽ thu được do tiêu thụ sản phẩm, cung
                                cấp dịch vụ với sản lượng.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Product  -->
        <div class="section product-all">

            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="the-loai" id="the-loai">
                        <option>Tất cả</option>
                        <option>Gạo</option>
                        <option>Bắp</option>
                        <option>Lương khô</option>
                        <option>Ngũ cốc</option>
                        <option>Khoai</option>
                        <option>Đã xóa</option>
                        <option>Sản phẩm gần hết hàng</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-product" type="text" class="form-search-input"
                               placeholder="Tìm kiếm tên sản phẩm...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <button class="btn-control-large" id="btn-cancel-product"><i
                            class="fa-light fa-rotate-right"></i> Làm mới
                    </button>
                    <button class="btn-control-large" id="btn-add-product"><i class="fa-light fa-plus"></i> Thêm sản
                        phẩm
                    </button>
                </div>
            </div>
            <%
                List<Products> products = productAdminDAO.getAllProducts();
            %>

            <div id="show-product">
                <% for (Products product : products) { %>
                <div class="list" data-id="<%= product.getID() %>"
                     data-product='{"id": <%= product.getID() %>, "productName": "<%= product.getProductName() %>", "price": "<%= product.getPrice() %>", "stockQuantity": "<%= product.getStockQuantity() %>", "shortDescription": "<%= product.getShortDescription() %>", "categoryID": "<%= product.getCategoryID() %>", "imageURL": "<%= product.getImageURL() %>"}'>
                    <div class="list-left">
                        <img src="<%= product.getImageURL() %> " alt="<%= product.getProductName() %>">

                        <div class="list-info">
                            <h4><%= product.getProductName() %>
                            </h4>
                            <p class="list-note"><%= product.getShortDescription() %>
                            </p>

                            <span class="list-category"
                                  data-category-id="<%= product.getCategoryID() %>"><%= product.getCategory().getCategoryName() %></span>
                            <span class="list-slkho"><%= product.getStockQuantity() %></span>
                        </div>
                    </div>
                    <div class="list-right">
                        <div class="list-price">
                            <span class="list-current-price"><%= product.getPrice() %></span>
                        </div>
                        <div class="list-control">
                            <div class="list-tool">

                                <button class="btn-edit-product"><i class="fa-light fa-pen-to-square"></i></button>
                                <button class="btn-delete" type="button" data-type="product"
                                        data-id="<%= product.getID() %>">
                                    <i class="fa-regular fa-trash"></i>
                                </button>


                            </div>
                        </div>
                    </div>
                </div>
                <% } %>

            </div>
            <div class="page-nav">
                <ul class="page-nav-list">
                    <li class="page-nav-item active"><a href="#">1</a></li>
                    <li class="page-nav-item active"><a href="#">2</a></li>
                </ul>
            </div>
        </div>
        <%
            List<Users> users = userAdminDAO.getAllUsers();
        %>
        <!-- Account  -->
        <div class="section">
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="tinh-trang-user" id="tinh-trang-user">
                        <option value="2">Tất cả</option>
                        <option value="1">Hoạt động</option>
                        <option value="0">Bị khóa</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-user" type="text" class="form-search-input"
                               placeholder="Tìm kiếm khách hàng...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <form action="" class="fillter-date">
                        <div>
                            <label for="time-start">Từ</label>
                            <input type="date" class="form-control-date" id="time-start-user">
                        </div>
                        <div>
                            <label for="time-end">Đến</label>
                            <input type="date" class="form-control-date" id="time-end-user">
                        </div>
                    </form>
                    <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>
                    <button id="btn-add-user" class="btn-control-large"><i class="fa-light fa-plus"></i> <span>Thêm
                                khách hàng</span></button>
                </div>
            </div>
            <div class="table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>STT</td>
                        <td>Họ và tên</td>
                        <td>Liên hệ</td>
                        <td>Ngày tham gia</td>
                        <td>Tình trạng</td>
                        <td></td>
                    </tr>
                    </thead>

                    <tbody id="show-user">
                    <%
                        int index = 1; // Khởi tạo biến đếm STT
                        for (Users user : users) {
                    %>
                    <tr data-id="<%= user.getId() %>">
                        <td><%= index %>
                        </td> <!-- Hiển thị STT -->
                        <td><%= user.getUsername() %>
                        </td>
                        <td><%= user.getPhone() %>
                        </td>
                        <td><%= user.getCreated_at() %>
                        </td>
                        <td>
            <span class="<%= user.getUserStatus().equals("Đang hoạt động") ? "status-complete" : "status-no-complete" %>">
                <%= user.getUserStatus() %>
            </span>
                        </td>
                        <td class="control control-table">
                            <button class="btn-edit-customer" id="edit-account">
                                <i class="fa-light fa-pen-to-square"></i>
                            </button>
                            <button class="btn-delete" data-type="user" data-id="<%= user.getId() %>">
                                <i class="fa-regular fa-trash"></i>
                            </button>

                        </td>
                    </tr>
                    <%
                            index++;
                        }
                    %>
                    </tbody>

                </table>
            </div>

            <!-- </div> -->
        </div>
        <!-- Order  -->
        <div class="section">
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="tinh-trang" id="tinh-trang">
                        <option value="2">Tất cả</option>
                        <option value="1">Đã xử lý</option>
                        <option value="0">Chưa xử lý</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-order" type="text" class="form-search-input"
                               placeholder="Tìm kiếm mã đơn, khách hàng...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <form action="" class="fillter-date">
                        <div>
                            <label for="time-start">Từ</label>
                            <input type="date" class="form-control-date" id="time-start">
                        </div>
                        <div>
                            <label for="time-end">Đến</label>
                            <input type="date" class="form-control-date" id="time-end">
                        </div>
                    </form>
                    <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>
                </div>
            </div>
            <div class="table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>Mã đơn</td>
                        <td>Khách hàng</td>
                        <td>Ngày đặt</td>
                        <td>Tổng tiền</td>
                        <td>Trạng thái</td>
                        <td>Thao tác</td>
                    </tr>
                    </thead>
                    <%
                        OrderAdminDAO orderAdminDAO = new OrderAdminDAO();
                        List<Order> orders = orderAdminDAO.getAllOrders(); // Lấy danh sách đơn hàng từ database
                    %>
                    <tbody id="showOrder">
                    <% for (Order order : orders) { %>
                    <tr>
                        <td>DH<%= order.getId() %>
                        </td>
                        <td><%= order.getReceiverPhone() %>
                        </td>
                        <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(order.getOrderDate()) %>
                        </td>
                        <td><%= String.format("%,.0f", order.getTotalAmount()) %>&nbsp;₫</td> <!-- Tổng tiền -->
                        <td>
                            <select class="order-status" data-order-id="<%= order.getId() %>">
                                <option value="Chưa xử lý" <%= order.getOrderStatus().equals("Chưa xử lý") ? "selected" : "" %>>Chưa xử lý</option>
                                <option value="Đã xử lý" <%= order.getOrderStatus().equals("Đã xử lý") ? "selected" : "" %>>Đã xử lý</option>
                                <option value="Đã vận chuyển" <%= order.getOrderStatus().equals("Đã vận chuyển") ? "selected" : "" %>>Đã vận chuyển</option>
                                <option value="Đã hủy đơn hàng" <%= order.getOrderStatus().equals("Đã hủy đơn hàng") ? "selected" : "" %>>Đã hủy đơn hàng</option>
                            </select>
                        </td>
                        <td class="control">
                            <button class="btn-detail" id=""><i class="fa-regular fa-eye"></i> Chi tiết</button>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="section">
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="the-loai-tk" id="the-loai-tk">
                        <option>Tất cả</option>
                        <option>Gạo</option>
                        <option>Lương khô</option>
                        <option>text3</option>
                        <option>text4</option>
                        <option>text5</option>
                        <option>text6</option>
                        <option>Món khác</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-tk" type="text" class="form-search-input"
                               placeholder="Tìm kiếm tên sản phẩm...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <form action="" class="fillter-date">
                        <div>
                            <label for="time-start">Từ</label>
                            <input type="date" class="form-control-date" id="time-start-tk">
                        </div>
                        <div>
                            <label for="time-end">Đến</label>
                            <input type="date" class="form-control-date" id="time-end-tk">
                        </div>
                    </form>
                    <button class="btn-reset-order"><i class="fa-regular fa-arrow-up-short-wide"></i></button>
                    <button class="btn-reset-order"><i class="fa-regular fa-arrow-down-wide-short"></i></button>
                    <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>
                </div>
            </div>
            <div class="order-statistical" id="order-statistical">
                <div class="order-statistical-item">
                    <%

                        int orderCount1 = orderAdminDAO.getSoldProducts();
                    %>
                    <div class="order-statistical-item-content">
                        <p class="order-statistical-item-content-desc">Sản phẩm được bán ra</p>
                        <h4 class="order-statistical-item-content-h" id="quantity-product"><%= orderCount1 %>
                        </h4>
                    </div>
                    <div class="order-statistical-item-icon">
                        <i class="fa-light fa-wheat-awn"></i>
                    </div>
                </div>
                <div class="order-statistical-item">
                    <%

                        int orderCount2 = orderAdminDAO.getSoldQuantity();
                    %>
                    <div class="order-statistical-item-content">
                        <p class="order-statistical-item-content-desc">Số lượng bán ra</p>
                        <h4 class="order-statistical-item-content-h" id="quantity-order"><%= orderCount2 %>
                        </h4>
                    </div>
                    <div class="order-statistical-item-icon">
                        <i class="fa-light fa-file-lines"></i>
                    </div>
                </div>
                <div class="order-statistical-item">
                    <div class="order-statistical-item-content">
                        <p class="order-statistical-item-content-desc">Doanh thu</p>
                        <h4 class="order-statistical-item-content-h" id="quantity-sale"><fmt:formatNumber
                                value="<%= sumRevenue %>" type="number" pattern="#,###"/>&nbsp;₫</h4>

                    </div>
                    <div class="order-statistical-item-icon">
                        <i class="fa-light fa-dollar-sign"></i>
                    </div>
                </div>
            </div>
            <div class="table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>STT</td>
                        <td>Tên món</td>
                        <td>Số lượng bán</td>
                        <td>Doanh thu</td>
                        <td></td>
                    </tr>
                    </thead>

                    <%
                        OrderAdminDAO orderAdminDAO2 = new OrderAdminDAO();
                        List<Products> productsList = orderAdminDAO2.getSoldListProducts();
                    %>

                    <tbody id="showTk">
                    <% for (Products product : productsList) { %>
                    <tr>
                        <td><%= product.getID() %>
                        </td>
                        <td>
                            <div class="prod-img-title">
                                <img class="prd-img-tbl" src="<%= product.getImageURL() %>"
                                     alt="<%= product.getProductName() %>">
                                <p><%= product.getProductName() %>
                                </p>
                            </div>
                        </td>
                        <td><%= product.getStockQuantity() %>
                        </td>
                        <td><%= product.getPrice() %>
                        </td>
                        <td>
                            <button class="btn-detail product-order-detail" data-id="<%= product.getID() %>">
                                <i class="fa-regular fa-eye"></i> Chi tiết
                            </button>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <!--Voucher-->
        <div class="section">
            <!-- Bộ lọc và tìm kiếm -->
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="tinh-trang-voucher" id="tinh-trang-voucher">
                        <option value="2">Tất cả</option>
                        <option value="1">Còn thời hạn</option>
                        <option value="0">Hết thời hạn</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-user" type="text" class="form-search-input"
                               placeholder="Tìm kiếm mã giảm giá...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <form action="" class="fillter-date">
                        <div>
                            <label for="time-start">Từ</label>
                            <input type="date" class="form-control-date" id="time-start-user">
                        </div>
                        <div>
                            <label for="time-end">Đến</label>
                            <input type="date" class="form-control-date" id="time-end-user">
                        </div>
                    </form>
                    <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>
                    <button id="btn-add-user" class="btn-control-large"><i class="fa-light fa-plus"></i>
                        <span>Thêm Mã</span></button>
                </div>
                <div class="table" style="margin-top: 10px;">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td>STT</td>
                            <td>Mã giảm giá</td>
                            <td>Số tiền giảm giá</td>
                            <td>Miêu tả</td>
                            <td>Hạn sử dụng</td>
                            <td></td>
                        </tr>
                        </thead>

                        <tbody id="show-user">
                        <%
                            CouponDAO couponDAO = new CouponDAO();
                            List<Coupon> coupons = couponDAO.getActiveCoupons();
                            int indexx = 1; // Khởi tạo biến đếm STT
                            for (Coupon coupon : coupons) {
                        %>
                        <tr data-id="<%= coupon.getId() %>">
                            <td><%= indexx %>
                            </td> <!-- Hiển thị STT -->

                            <td><%= coupon.getCouponCode() %>
                            </td>
                            <td><%= coupon.getDiscountAmount() %>
                            </td>
                            <td><%= coupon.getDescription() %>
                            </td>
                            <td>
                                <%
                                    // Lấy ngày hiện tại
                                    java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());

                                    // Tính toán số ngày còn lại
                                    long diffInMillies = coupon.getEndDate().getTime() - currentTimestamp.getTime();
                                    long daysRemaining = java.util.concurrent.TimeUnit.DAYS.convert(diffInMillies, java.util.concurrent.TimeUnit.MILLISECONDS);
                                %>
                                <span class="<%= (diffInMillies > 0) ? "status-complete" : "status-no-complete" %>">
                                    <% if (diffInMillies > 0) { %>
                                        Còn <%= daysRemaining %> ngày
                                    <% } else { %>
                                        Hết hạn
                                    <% } %>
                                </span>
                            </td>
                            <td class="control control-table">
                                <button class="btn-edit-coupon" id="edit-coupon">
                                    <i class="fa-light fa-pen-to-square"></i>
                                </button>
                                <button class="btn-deletee" data-type="user" data-id="<%= coupon.getId() %>">
                                    <i class="fa-regular fa-trash"></i>
                                </button>

                            </td>
                        </tr>
                        <%
                                indexx++;
                            }
                        %>
                        </tbody>

                    </table>
                </div>
            </div>
        </div>
        <!--ncc-->
        <div class="section ncc-all">
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="the-loai" id="the-loai">
                        <option>Tất cả</option>
                        <option>Sản phẩm gần hết hàng</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-product" type="text" class="form-search-input"
                               placeholder="Tìm kiếm tên sản phẩm...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <button class="btn-control-large" id="btn-cancel-product"><i
                            class="fa-light fa-rotate-right"></i> Làm mới
                    </button>
                    <button class="btn-control-large" id="btn-add-product"><i class="fa-light fa-plus"></i> Thêm sản
                        phẩm
                    </button>
                </div>
            </div>
            <div id="show-ship">


            </div>
            <div class="page-nav" id="page-nav">
                <ul class="page-nav-list">
                </ul>
            </div>
        </div>


        <!--Ship-->
        <div class="section ship-all">
            <div class="admin-control">
                <div class="admin-control-left">
                    <select name="the-loai" id="the-loai">
                        <option>Tất cả</option>
                        <option>Sản phẩm gần hết hàng</option>
                    </select>
                </div>
                <div class="admin-control-center">
                    <form action="" class="form-search">
                        <span class="search-btn"><i class="fa-light fa-magnifying-glass"></i></span>
                        <input id="form-search-product" type="text" class="form-search-input"
                               placeholder="Tìm kiếm tên sản phẩm...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <button class="btn-control-large" id="btn-cancel-product"><i
                            class="fa-light fa-rotate-right"></i> Làm mới
                    </button>
                    <button class="btn-control-large" id="btn-add-product"><i class="fa-light fa-plus"></i> Thêm sản
                        phẩm
                    </button>
                </div>
            </div>
            <div id="show-ship">


            </div>
            <div class="page-nav" id="page-nav">
                <ul class="page-nav-list">
                </ul>
            </div>
        </div>

    </main>

    <div class="modal add-product">
        <div class="modal-container">
            <h3 class="modal-container-title add-product-e" style="margin: 0 auto;">THÊM MỚI SẢN PHẨM</h3>
            <h3 class="modal-container-title edit-product-e" style="margin: 0 auto;">CHỈNH SỬA SẢN PHẨM</h3>
            <button class="modal-close product-form"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">

                <form id="product-form" method="post" action="${pageContext.request.contextPath}/addProduct"
                      enctype="multipart/form-data">
                    <!-- Input ẩn chứa ID sản phẩm (dùng cho chỉnh sửa) -->
                    <input type="hidden" name="action" id="action" value="add">
                    <input type="hidden" id="product-id" name="id" value="">


                    <div class="modal-content-left" style="margin: 0 auto;">
                        <img src="image/admin/blank-image.png" alt="" class="upload-image-preview"
                             id="preview-image">
                        <div class="form-group file">
                            <label for="up-hinh-anh" class="form-label-file"><i
                                    class="fa-regular fa-cloud-arrow-up"></i>Chọn hình ảnh</label>
                            <input accept="image/jpeg, image/png, image/jpg" id="up-hinh-anh" name="up-hinh-anh"
                                   type="file" class="form-control" style="display: none !important;">
                        </div>
                    </div>
                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="ten-mon" class="form-label">Tên sản phẩm</label>
                            <input id="ten-mon" name="productName" type="text" placeholder="Nhập tên sản phẩm"
                                   class="form-control" required>
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="category" class="form-label">Danh mục</label>
                            <select name="categoryID" id="chon-mon">
                                <option value="1"> Gạo</option>
                                <option value="2">Lương khô</option>
                                <option value="3"> text3</option>
                                <option value="4">text4</option>
                                <option value="5">text5</option>
                                <option value="6">text6</option>
                            </select>
                            <span class="form-message"></span>
                        </div>

                        <div class="form-group">
                            <label for="IsSale" class="form-label">Chọn sale</label>
                            <select name="IsSale" id="chon-sale">
                                <option value="1">Có</option>
                                <option value="0">Không</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="phan-tram-giam" class="form-label">% giảm giá</label>
                            <input id="phan-tram-giam" name="DiscountPercentage" type="number" step="0.01"
                                   placeholder="Nhập %"
                                   class="form-control" required>
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="gia-moi" class="form-label">Giá bán</label>
                            <input id="gia-moi" name="price" type="number" step="0.01" placeholder="Nhập giá bán"
                                   class="form-control" required>
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="so-luong" class="form-label">Số lượng</label>
                            <input id="so-luong" name="stockQuantity" type="number" placeholder="Nhập số lượng"
                                   class="form-control" required>
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="mo-ta" class="form-label">Mô tả</label>
                            <textarea class="product-desc" name="shortDescription" id="mo-ta"
                                      placeholder="Nhập mô tả sản phẩm..." required></textarea>
                            <span class="form-message"></span>
                        </div>
                        <button type="submit" class="form-submit btn-add-product-form add-product-e"
                                id="add-product-button">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM SẢN PHẨM</span>
                        </button>
                        <button type="submit" class="form-submit btn-update-product-form edit-product-e"
                                id="update-product-button">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>

                    </div>
                </form>
            </div>
            </form>
        </div>
    </div>
    <div class="modal detail-order">
        <div class="modal-container3">
            <h3 class="modal-container-title">CHI TIẾT ĐƠN HÀNG</h3>
            <button class="modal-close" onclick="closeModal()"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-detail-content">
                <div class="modal-detail-left" id="order-details"></div>
                <div class="modal-detail-right" id="order-summary"></div>
            </div>
            <div class="modal-detail-footer">
                <div class="footer-left">
                    <span class="footer-label">Thành tiền:</span>
                    <span class="footer-price" id="order-total">0 ₫</span>
                </div>
                <div class="footer-right">
                    <button class="footer-btn btn-status" id="order-status" onclick="toggleOrderStatus(this, 1)">
                        Chưa xử lý
                    </button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal detail-order-product">
        <div class="modal-container3">
            <button class="modal-close"><i class="fa-regular fa-xmark"></i></button>
            <div class="table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>Mã đơn</td>
                        <td>Số lượng</td>
                        <td>Đơn giá</td>
                        <td>Ngày đặt</td>
                    </tr>
                    </thead>
                    <tbody id="show-product-order-detail">
                    </tbody>
                </table>
            </div>

        </div>
    </div>


    <%
        UserAdminDAO userDao = new UserAdminDAO();
        List<Users> userList = userDao.getAllUsers();
        request.setAttribute("userList", userList);
    %>

    <div class="modal" id="customer-modal">
        <div class="modal-container2 ">
            <!-- Nút đóng -->
            <button class="modal-close product-form"><i class="fa-regular fa-xmark"></i></button>
            <!-- Tiêu đề Modal -->
            <h3 class="modal-container-title add-customer-e">THÊM KHÁCH HÀNG MỚI</h3>
            <h3 class="modal-container-title edit-customer-e">CHỈNH SỬA THÔNG TIN</h3>
            <!-- Nội dung Form -->
            <form action="${pageContext.request.contextPath}/addCustomer" method="post" id="customer-form">


                <input type="hidden" name="action" id="form-action" value="add">
                <input type="hidden" name="id" id="customer-id" value="">

                <div class="form-group">
                    <label for="customer-fullname" class="form-label">Tên đầy đủ</label>
                    <input id="customer-fullname" name="fullname" type="text" placeholder="VD: Tuquangchuong"
                           class="form-control" required
                           readonly>
                </div>
                <div class="form-group">
                    <label for="customer-phone" class="form-label">Số điện thoại</label>
                    <input id="customer-phone" name="phone" type="text" placeholder="Nhập số điện thoại"
                           class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="customer-password" class="form-label">Mật khẩu</label>
                    <input id="customer-password" name="password" type="password" placeholder="Nhập mật khẩu"
                           class="form-control" required>
                </div>
                <!-- Trạng thái (Chỉ dành cho chỉnh sửa) -->
                <div class="form-group edit-customer-e">
                    <label for="customer-status" class="form-label">Trạng thái</label>
                    <input type="hidden" name="status" value="0"> <!-- Giá trị mặc định là 0 -->
                    <input type="checkbox" id="customer-status" name="status" value="1" class="switch-input">
                    <label for="customer-status" class="switch"></label>
                </div>
                <!-- Nút Hành Động -->
                <button type="submit" class="form-submit add-account-e" id="signup-button">Đăng ký</button>
                <button type="submit" class="form-submit edit-customer-e" id="update-customer-button">
                    <i class="fa-regular fa-floppy-disk"></i> Lưu thông tin
                </button>
            </form>
        </div>
    </div>


    <div class="modal add-voucher">
        <div class="modal-container">
            <h3 class="modal-container-title add-voucher-e">THÊM MỚI SẢN PHẨM</h3>
            <h3 class="modal-container-title edit-voucher-e">CHỈNH SỬA SẢN PHẨM</h3>
            <button class="modal-close"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">
                <form action="" class="add-voucher-form">
                    <div class="modal-content-left">
                        <img src="image/admin/blank-image.png" alt="" class="upload-image-preview">
                        <div class="form-group file">
                            <label for="up-hinh-anh" class="form-label-file"><i
                                    class="fa-regular fa-cloud-arrow-up"></i>Chọn hình ảnh</label>
                            <input accept="image/jpeg, image/png, image/jpg" id="up-hinh-anh" name="up-hinh-anh"
                                   type="file" class="form-control">
                        </div>
                    </div>
                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="ten-mon" class="form-label">Tên sản phẩm</label>
                            <input id="ten-mon" name="ten-mon" type="text" placeholder="Nhập tên sản phẩm"
                                   class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="category" class="form-label">Danh mục</label>
                            <select name="category" id="chon-mon">
                                <option>Gạo</option>
                                <option>Lương khô</option>
                                <option>text3</option>
                                <option>text4</option>
                                <option>text5</option>
                                <option>text6</option>
                            </select>
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="gia-moi" class="form-label">Giá bán</label>
                            <input id="gia-moi" name="gia-moi" type="text" placeholder="Nhập giá bán"
                                   class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="so-luong" class="form-label">Số lượng</label>
                            <input id="so-luong" name="so-luong" type="text" placeholder="Nhập số lượng"
                                   class="form-control">
                            <span class="form-message"></span>
                        </div>
                        <div class="form-group">
                            <label for="mo-ta" class="form-label">Mô tả</label>
                            <textarea class="voucher-desc" id="mo-ta"
                                      placeholder="Nhập mô tả sản phẩm..."></textarea>
                            <span class="form-message"></span>
                        </div>
                        <button class="form-submit btn-add-voucher-form add-voucher-e" id="add-voucher-button">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM SẢN PHẨM</span>
                        </button>
                        <button class="form-submit btn-update-voucher-form edit-voucher-e"
                                id="update-voucher-button">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>
                    </div>
                </form>
            </div>
            </form>
        </div>
    </div>
</div>
<div id="toast"></div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(".btn-detail").click(function () {
        const orderId = $(this).closest("tr").find("td:first").text().replace("DH", "").trim();

        $.ajax({
            url: "/project/getOrderDetails",
            type: "GET",
            data: {orderId: orderId},
            success: function (response) {
                document.getElementById("order-total").innerText = response.totalPrice;
                if (response.detailsHtml.trim() === "" || response.summaryHtml.trim() === "") {
                    $("#order-details").html("<p>Không có chi tiết đơn hàng.</p>");
                    $("#order-summary").html("<p>Không có thông tin tóm tắt.</p>");
                } else {
                    $("#order-details").html(response.detailsHtml);
                    $("#order-summary").html(response.summaryHtml);
                }
                $(".modal.detail-order").show();
            },
            error: function () {
                alert("Có lỗi xảy ra khi lấy dữ liệu chi tiết đơn hàng.");
            },
        });
    });

    $(".modal-close").click(function () {
        $(".modal.detail-order").hide();
    });


</script>

<script src="js/admin.js"></script>
</body>

</html>