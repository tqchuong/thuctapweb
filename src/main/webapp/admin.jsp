<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.ProductAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.UserAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.OrderAdminDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.CouponDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.*" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.admin.BrandAdminDAO" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.Activity_logDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Activity_log" %>
<%@ page import="fit.hcmuaf.edu.vn.foodmart.dao.Activity_logDAO" %>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/admin.css">
    <link href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="css/admin-responsive.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
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


                <li class="sidebar-list-item tab-content">
                    <a href="#" class="sidebar-link">
                        <div class="sidebar-icon"><i class="fa-solid fas fa-list"></i></div>
                        <div class="hidden-sidebar">Nhật kí hoạt động</div>
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
                     data-product='{"id": <%= product.getID() %>, "productName": "<%= product.getProductName() %>", "price": "<%= product.getPrice() %>", "stockQuantity": "<%= product.getWeight() %>", "shortDescription": "<%= product.getShortDescription() %>", "categoryID": "<%= product.getCategoryID() %>", "imageURL": "<%= product.getImageURL() %>"}'>
                    <div class="list-left">
                        <img src="<%= product.getImageURL() %> " alt="<%= product.getProductName() %>">

                        <div class="list-info">
                            <h4><%= product.getProductName() %>
                            </h4>
                            <p class="list-note"><%= product.getShortDescription() %>
                            </p>

                            <span class="list-category"
                                  data-category-id="<%= product.getCategoryID() %>"><%= product.getCategory().getCategoryName() %></span>
                            <span class="list-slkho"><%= product.getWeight() %></span>
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
        </div>
        <!-- </div> -->

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
                            <span class="<%= order.getOrderStatus().equals("Đã xử lý") ? "status-complete" : "status-no-complete" %>"><%= order.getOrderStatus() %>
                          </span>
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
                        <td><%= product.getWeight() %>
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
                        <input id="form-search-voucher" type="text" class="form-search-input" placeholder="Tìm kiếm mã giảm giá...">
                    </form>
                </div>
                <div class="admin-control-right">
                    <form action="" class="fillter-date">
                        <div>
                            <label for="time-start">Từ</label>
                            <input type="date" class="form-control-date" id="time-start-voucher">
                        </div>
                        <div>
                            <label for="time-end">Đến</label>
                            <input type="date" class="form-control-date" id="time-end-voucher">
                        </div>
                    </form>
                    <button class="btn-reset-order"><i class="fa-light fa-arrow-rotate-right"></i></button>
                    <button id="btn-add-voucher" class="btn-control-large"><i class="fa-light fa-plus"></i><span>Thêm Mã</span></button>
                </div>
            </div>

            <div class="table" style="margin-top: 10px;">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>STT</td>
                        <td>Mã giảm giá</td>
                        <td>Số tiền giảm giá</td>
                        <td>Miêu tả</td>
                        <td>Trạng thái</td>
                        <td>Hạn sử dụng</td>
                        <td></td>
                    </tr>
                    </thead>

                    <tbody id="show-voucher">
                    <%
                        CouponDAO couponDAO = new CouponDAO();
                        List<Coupon> coupons = couponDAO.getActiveCoupons();
                        int indexx = 1; // Khởi tạo biến đếm STT
                        for (Coupon coupon : coupons) {
                    %>
                    <tr data-id="<%= coupon.getId() %>">
                        <td><%= indexx %></td> <!-- Hiển thị STT -->
                        <td><%= coupon.getCouponCode() %></td>
                        <td><%= coupon.getDiscountAmount() %></td>
                        <td><%= coupon.getDescription() %></td>
                        <td>
                        <span class="<%= (coupon.getStatus().equals("Active")) ? "status-complete" : "status-no-complete" %>">
                            <%= coupon.getStatus() %>
                        </span>
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
                            <button class="btn-delete" data-type="voucher" data-id="<%= coupon.getId() %>">
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


        <!--categories-->
        <div class="section">
            <div class="table" style="display: flex; gap: 30px;">
                <!-- Danh mục -->
                <div style="flex: 1;">
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
                                <input id="form-search-categories" type="text" class="form-search-input"
                                       placeholder="Tìm kiếm...">
                            </form>
                        </div>
                        <div class="admin-control-right">
                            <button class="btn-control-large" id="btn-add-categories">
                                <i class="fa-light fa-plus"></i> Thêm danh mục
                            </button>
                        </div>

                    </div>
                    <h3>Danh mục sản phẩm</h3>
                    <table width="100%">
                        <thead>
                        <tr>
                            <td>STT</td>
                            <td>Tên danh mục</td>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Category> categories = productAdminDAO.getAllCategories();
                            int stt1 = 1;
                            for (Category cat : categories) {
                        %>
                        <tr data-id="<%= cat.getCategoryID() %>">
                            <td><%= stt1++ %></td>
                            <td><%= cat.getCategoryName() %></td>
                            <td class="control control-table">
                                <button class="btn-edit-categories" id="edit-categories">
                                    <i class="fa-light fa-pen-to-square"></i>
                                </button>
                                <button class="btn-delete" data-type="categories" data-id="<%= cat.getCategoryID() %>">
                                    <i class="fa-regular fa-trash"></i>
                                </button>
                            </td>

                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>

                <div style="flex: 1;">
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
                                <input id="form-search-brand" type="text" class="form-search-input"
                                       placeholder="Tìm kiếm...">
                            </form>
                        </div>
                        <div class="admin-control-right">
                            <button class="btn-control-large" id="btn-add-brand"><i class="fa-light fa-plus"></i> Thêm thương hiệu</button>

                        </div>
                    </div>
                    <h3>Thương hiệu (Brands)</h3>
                    <table width="100%">
                        <thead>
                        <tr>
                            <td>STT</td>
                            <td>Tên thương hiệu</td>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            BrandAdminDAO brandAdminDAO = new BrandAdminDAO();
                            List<Brands> brands = brandAdminDAO.getAllBrands();
                            int stt2 = 1;
                            for (Brands brand : brands) {
                        %>
                        <tr data-id="<%= brand.getId() %>">
                            <td><%= stt2++ %></td>
                            <td><%= brand.getName() %></td>
                            <td class="control control-table">
                                <button class="btn-edit-brand" id="edit-brand">
                                    <i class="fa-light fa-pen-to-square"></i>
                                </button>
                                <button class="btn-delete" data-type="brand" data-id="<%= brand.getId() %>">
                                    <i class="fa-regular fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>   <!-- Thương hiệu -->
                </div>
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
            <div class="table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>Phương thức giao hàng</td>
                        <td>Gía tiền</td>
                        <td>Trạng thái</td>

                    </tr>
                    </thead>

                </table>
            </div>
        </div>


        <!--Log-->
        <div class="section">
            <h2><i class="fas fa-list"></i> Nhật ký hoạt động</h2>
            <!-- Bảng nhật ký hoạt động -->
            <div class="table" style="margin-top: 10px;">
                <table id="logTable" class="log-table" width="100%">
                    <thead>
                    <tr>
                        <td>STT</td>
                        <td>Người dùng</td>
                        <td>Hành động</td>
                        <td>Mức độ</td>
                        <td>IP</td>
                        <td>Thời gian</td>
                        <td>Trang</td>
                        <td>Tài nguyên</td>
                        <td>Dữ liệu cũ</td>
                        <td>Dữ liệu mới</td>
                    </tr>
                    </thead>

                    <tbody id="log-activity-body">
                    <%
                        List<Activity_log> logs = Activity_logDAO.getAllLogs();
                        int stt = 1;
                        if (logs != null && !logs.isEmpty()) {
                            for (Activity_log log : logs) {
                    %>
                    <tr>
                        <td><%= stt++ %></td>
                        <td><%= log.getUsername() %></td>
                        <td><%= log.getAction() %></td>
                        <td><%= log.getLevel_log() %></td>
                        <td><%= log.getIp_address() %></td>
                        <td><%= log.getTime_log() %></td>
                        <td><%= log.getSource_page() %></td>
                        <td><%= log.getResource() %></td>
                        <td><%= log.getOld_data() %></td>
                        <td><%= log.getNew_data() %></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr><td colspan="10">Không có dữ liệu nhật ký.</td></tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <div class="modal add-product">
        <div class="modal-container">
            <h3 class="modal-container-title add-product-e" style="margin: 0 auto;">THÊM MỚI SẢN PHẨM</h3>
            <h3 class="modal-container-title edit-product-e" style="margin: 0 auto;">CHỈNH SỬA SẢN PHẨM</h3>
            <button class="modal-close product-form"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">

                <form id="product-form" method="post" action="${pageContext.request.contextPath}/addProduct" enctype="multipart/form-data">
                    <!-- Input ẩn chứa ID sản phẩm (dùng cho chỉnh sửa) -->
                    <input type="hidden" name="action" id="action" value="add">
                    <input type="hidden" id="product-id" name="id" value="">

                    <div class="modal-content-left" style="margin: 0 auto;">
                        <img src="image/admin/blank-image.png" alt="" class="upload-image-preview" id="preview-image">
                        <div class="form-group file">
                            <label for="up-hinh-anh" class="form-label-file"><i class="fa-regular fa-cloud-arrow-up"></i>Chọn hình ảnh</label>
                            <input accept="image/jpeg, image/png, image/jpg" id="up-hinh-anh" name="up-hinh-anh" type="file" class="form-control" style="display: none;">
                        </div>
                    </div>

                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="ten-mon" class="form-label">Tên sản phẩm</label>
                            <input id="ten-mon" name="productName" type="text" placeholder="Nhập tên sản phẩm" class="form-control" required>
                            <span class="form-message"></span>
                        </div>

                        <!-- Danh mục -->
                        <div class="form-group">
                            <label for="category" class="form-label">Danh mục</label>
                            <select name="categoryID" id="chon-mon">
                                < value=""></>
                            </select>
                            <span class="form-message"></span>
                        </div>



                        <div class="form-group">
                            <label for="brands" class="form-label">Thương hiệu</label>
                            <select name="brandsID" id="chon-brands">
                                <option value=""></option>
                            </select>
                            <span class="form-message"></span>
                        </div>

                        <!-- Các trường khác -->
                        <div class="form-group">
                            <label for="IsSale" class="form-label">Chọn sale</label>
                            <select name="IsSale" id="chon-sale">
                                <option value="1">Có</option>
                                <option value="0">Không</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="phan-tram-giam" class="form-label">% giảm giá</label>
                            <input id="phan-tram-giam" name="DiscountPercentage" type="number" step="0.01" placeholder="Nhập %" class="form-control" required>
                            <span class="form-message"></span>
                        </div>

                        <div class="form-group">
                            <label for="gia-moi" class="form-label">Giá bán</label>
                            <input id="gia-moi" name="price" type="number" step="0.01" placeholder="Nhập giá bán" class="form-control" required>
                            <span class="form-message"></span>
                        </div>

                        <div class="form-group">
                            <label for="khoi-luong" class="form-label">Khối lượng</label>
                            <input id="khoi-luong" name="weight" type="number" placeholder="Nhập khối lượng lượng" class="form-control" required>
                            <span class="form-message"></span>
                        </div>

                        <div class="form-group">
                            <label for="so-luong" class="form-label">Số lượng</label>
                            <input id="so-luong" name="quantity" type="number" placeholder="Nhập số lượng" class="form-control" required>
                            <span class="form-message"></span>
                        </div>

                        <div class="form-group">
                            <label for="mo-ta" class="form-label">Mô tả</label>
                            <textarea class="product-desc" name="shortDescription" id="mo-ta" placeholder="Nhập mô tả sản phẩm..." required></textarea>
                            <span class="form-message"></span>
                        </div>

                        <!-- Nút Submit -->
                        <button type="submit" class="form-submit btn-add-product-form add-product-e" id="add-product-button">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM SẢN PHẨM</span>
                        </button>
                        <button type="submit" class="form-submit btn-update-product-form edit-product-e" id="update-product-button">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>
                    </div>
                </form>
            </div>
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

    <%
        // Lấy dữ liệu khách hàng được truyền từ Servlet
        Users customer = (Users) request.getAttribute("customer");
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
                    <input type="checkbox" id="customer-status" name="status" value="1" class="switch-input"
                        <%= (customer != null && "Đang hoạt động".equals(customer.getUserStatus())) ? "checked" : "" %> >
                    <label for="customer-status" class="switch"></label>
                </div>

                <div class="form-group edit-customer-e">
                    <label for="customer-role" class="form-label">Vai trò</label>
                    <input type="checkbox" id="customer-role" name="role" value="1" class="switch-input"
                        <%= (customer != null && "Admin".equals(customer.getRole())) ? "checked" : "" %> >
                    <label for="customer-role" class="switch"></label>
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
        <div class="modal-container4">
            <h3 class="modal-container-title add-voucher-e">THÊM MỚI VOUCHER</h3>
            <h3 class="modal-container-title edit-voucher-e">CHỈNH SỬA VOUCHER</h3>
            <button class="modal-close"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/couponController" method="POST" class="add-voucher-form">
                    <input type="hidden" id="actionType" name="actionType" value="add">
                    <input type="hidden" id="coupon-id" name="couponId">

                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="coupon-code">Mã giảm giá</label>
                            <input id="coupon-code" name="couponCode" type="text" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="discount-type">Loại giảm giá</label>
                            <select name="discountType" id="discount-type" class="form-control" required>
                                <option value="Percentage">Giảm theo phần trăm (%)</option>
                                <option value="FixedAmount">Giảm theo số tiền cố định (₫)</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="discount-amount">Giá trị giảm giá</label>
                            <input id="discount-amount" name="discountAmount" type="number" step="0.01" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="max-discount-amount">Số tiền giảm tối đa (nếu giảm theo %)</label>
                            <input id="max-discount-amount" name="maxDiscountAmount" type="number" step="0.01" class="form-control">
                        </div>

                        <div class="form-group">
                            <label for="description">Mô tả</label>
                            <textarea id="description" name="description" class="form-control" required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="start-date">Ngày bắt đầu</label>
                            <input id="start-date" name="startDate" type="date" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="end-date">Ngày hết hạn</label>
                            <input id="end-date" name="endDate" type="date" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="min-order-amount">Số tiền đơn hàng tối thiểu</label>
                            <input id="min-order-amount" name="minOrderAmount" type="number" step="0.01" class="form-control" required>
                        </div>

                        <div class="form-group">
                            <label for="max-usage">Số lần sử dụng tối đa (tổng cộng)</label>
                            <input id="max-usage" name="maxUsage" type="number" class="form-control">
                        </div>

                        <div class="form-group">
                            <label for="max-usage-per-user">Số lần sử dụng tối đa mỗi người</label>
                            <input id="max-usage-per-user" name="maxUsagePerUser" type="number" class="form-control">
                        </div>

                        <div class="form-group">
                            <label for="status">Trạng thái</label>
                            <select name="status" id="status" class="form-control" required>
                                <option value="Active">Đang hoạt động</option>
                                <option value="Inactive">Ngừng hoạt động</option>
                            </select>
                        </div>

                        <button type="submit" class="form-submit btn-add-voucher-form add-voucher-e">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM VOUCHER</span>
                        </button>

                        <button type="submit" class="form-submit btn-update-voucher-form edit-voucher-e">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>




    <div class="modal add-brand">
        <div class="modal-container4">
            <h3 class="modal-container-title add-brand-e">THÊM MỚI BRANDS</h3>
            <h3 class="modal-container-title edit-brand-e">CHỈNH SỬA BRANDS</h3>
            <button class="modal-close"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/brandController" method="POST" id="brand-form">
                    <input type="hidden" id="action1" name="action1" value="add">
                    <input type="hidden" id="brand-id" name="brandId" value="">

                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="brand-code">Tên brand</label>
                            <input id="brand-code" name="brandName" type="text" class="form-control" required>
                        </div>
                        <button type="submit" class="form-submit btn-add-brand-form add-brand-e">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM BRAND</span>
                        </button>
                        <button type="submit" class="form-submit btn-update-brand-form edit-brand-e">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal add-categories">
        <div class="modal-container4">
            <h3 class="modal-container-title add-categories-e">THÊM MỚI CATEGORIES</h3>
            <h3 class="modal-container-title edit-categories-e">CHỈNH SỬA CATEGORIES</h3>
            <button class="modal-close"><i class="fa-regular fa-xmark"></i></button>
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/categoriesController" method="POST" id="categories-form">
                    <input type="hidden" id="action2" name="action2" value="add">
                    <input type="hidden" id="categories-id" name="categoriesId" value="">

                    <div class="modal-content-right">
                        <div class="form-group">
                            <label for="categories-code">Tên categories</label>
                            <input id="categories-code" name="categoriesName" type="text" class="form-control" required>
                        </div>
                        <button type="submit" class="form-submit btn-add-categories-form add-categories-e">
                            <i class="fa-regular fa-plus"></i>
                            <span>THÊM CATEGORIES</span>
                        </button>
                        <button type="submit" class="form-submit btn-update-categories-form edit-categories-e">
                            <i class="fa-light fa-pencil"></i>
                            <span>LƯU THAY ĐỔI</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>

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
<script>
    setInterval(() => {
        fetch('<%=request.getContextPath()%>/checkSession')
            .then(response => response.text())
            .then(result => {
                if(result.trim() !== 'Admin'){
                    alert('Vai trò của bạn đã thay đổi hoặc phiên đã hết hạn. Bạn sẽ bị logout ngay!');
                    window.location.href = '<%=request.getContextPath()%>/login.jsp?message=' + encodeURIComponent('Vai trò thay đổi hoặc phiên hết hạn.');
                }
            })
            .catch(err => {
                console.error(err);
                window.location.href = '<%=request.getContextPath()%>/login.jsp';
            });
    }, 3000); // Kiểm tra mỗi 3 giây
</script>
<script>
    fetch('<%=request.getContextPath()%>/brandController')
        .then(response => response.json())
        .then(brands => {
            const select = document.getElementById('chon-brands');
            brands.forEach(brand => {
                const option = document.createElement('option');
                option.value = brand.id;         // Sửa brandID -> id
                option.textContent = brand.name; // Sửa brandName -> name
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading brands:', error));
</script>
<script>
    fetch('<%=request.getContextPath()%>/categoriesController')
        .then(response => response.json())
        .then(categories => {
            const select = document.getElementById('chon-mon');
            select.innerHTML = '<option value="">Chọn danh mục</option>'; // Clear trước
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.categoryID; // đúng field
                option.textContent = category.categoryName; // đúng field
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error loading categories:', error));
</script>

<%--table showlog--%>
<script>
    $(document).ready(function() {
        $('#logTable').DataTable({
            paging: true,
            searching: true,
            ordering: true,
            pageLength: 10,
            lengthMenu: [5, 10, 25, 50],
            language: {
                search: "Tìm kiếm:",
                paginate: {
                    first: "Đầu",
                    last: "Cuối",
                    next: "Tiếp",
                    previous: "Trước"
                },
                lengthMenu: "Hiển thị _MENU_ bản ghi mỗi trang",
                info: "Hiển thị _START_ đến _END_ của _TOTAL_ bản ghi",
                infoEmpty: "Không có bản ghi nào",
                emptyTable: "Không có dữ liệu trong bảng"
            }
        });
    });
</script>
<script src="js/admin.js"></script>
</body>
</html>