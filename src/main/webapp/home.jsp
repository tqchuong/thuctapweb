<%@ page import="fit.hcmuaf.edu.vn.foodmart.model.Users" %>
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

        <!-- CTA + Thương hiệu -->
        <div class="brand-highlight-section">
            <a href="products" class="cta-button">THƯƠNG HIỆU</a>
            <div class="brand-logos">
                <a href="products-by-brand.jsp?brand=ABC" class="brand-logo-item">
                    <img src="image/brands/hoabanfood.jpg" alt="ABC">
                </a>

                <a href="products?brand=Acecook" class="brand-logo-item">
                    <img src="image/brands/acecook.png" alt="Acecook">
                </a>
                <a href="products?brand=Vedan" class="brand-logo-item">
                    <img src="image/brands/vedan.png" alt="Vedan">
                </a>
                <a href="products?brand=TH" class="brand-logo-item">
                    <img src="image/brands/thtrue.png" alt="TH True Milk">
                </a>
                <a href="products?brand=HaFarm" class="brand-logo-item">
                    <img src="image/brands/hafarm.png" alt="HaFarm">
                </a>
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
        <div class="container2">
            <h3>Về chúng tôi</h3>
            <div class="content">
                <p>Chào mừng bạn đến với <strong>FoodMart</strong> – nơi cung cấp những sản phẩm lương thực chất lượng
                    cao, giàu dinh dưỡng và an toàn cho mọi gia đình!
                    Với nguồn gốc rõ ràng và quy trình kiểm soát nghiêm ngặt, chúng tôi cam kết mang đến sự an tâm cho
                    từng bữa ăn.</p>

                <h4>Sứ mệnh của chúng tôi</h4>
                <p>FoodMart hướng tới việc cung cấp các loại lương thực đảm bảo tiêu chí sạch, bền vững và tốt cho sức
                    khỏe, góp phần xây dựng một cộng đồng sống xanh và lành mạnh.</p>

                <h4>Tại sao nên chọn FoodMart?</h4>
                <ul>
                    <li><strong>Chất lượng hàng đầu:</strong> Các sản phẩm lương thực được chọn lọc kỹ càng từ những
                        vùng trồng trọt đạt chuẩn.</li>
                    <li><strong>An toàn thực phẩm:</strong> Đảm bảo không sử dụng chất bảo quản hay hóa chất độc hại.
                    </li>
                    <li><strong>Giàu dinh dưỡng:</strong> Sản phẩm giữ nguyên giá trị dinh dưỡng tự nhiên, phù hợp với
                        mọi đối tượng.</li>
                    <li><strong>Dịch vụ tin cậy:</strong> Đội ngũ hỗ trợ tận tâm và giao hàng nhanh chóng.</li>
                </ul>

                <h4>Hành trình của chúng tôi</h4>
                <p>Khởi đầu với mong muốn nâng cao chất lượng lương thực trong từng bữa ăn, FoodMart đã không ngừng phát
                    triển để trở thành đối tác tin cậy của hàng ngàn gia đình và tổ chức trên khắp cả nước.</p>
            </div>

        </div>


        <%
            ProductDAO productDAO = new ProductDAO();
            List<Products> latestProducts = productDAO.getLatestProducts(6);
        %>
        <div class="carousel-container">
            <h2>SẢN PHẨM MỚI</h2>
            <div class="carousel">
                <% for (Products product : latestProducts) { %>

                <div class="carousel-item">
                    <img src="<%= product.getImageURL() %>" alt="<%= product.getProductName() %>">
                    <p><%= product.getProductName() %></p>
                    <span style="color: #B5292F; font-weight: bold;"><%= product.getPrice() %>₫</span>
                </div>
                <% } %>
            </div>
        </div>

        <div class="new-container">
            <div class="news-section">

                <div class="date">
                    <span>09</span>
                    <span>Th11</span>
                </div>
                <h2>TIN MỚI NHẤT</h2>
                <div class="main-news">

                    <div class="news-content">
                        <img src="image/new/Ngày-Pháp-luật-Việt-Nam.jpg" alt="Tin tức">
                        <h3>
                            FoodMart nhiệt liệt hưởng ứng Ngày Pháp luật nước Cộng hòa xã hội chủ nghĩa Việt Nam
                        </h3>
                        <p>1. Mục đích của Ngày Pháp luật Việt Nam 09/11 hàng năm nhằm tôn vinh...</p>
                    </div>
                </div>
                <ul class="sub-news">
                    <li>
                        <a href="#">Giá lúa gạo hôm nay 19/11/2024: Giá lúa tăng 100 – 200 đồng/kg; giá gạo giảm 50-100
                            đồng/kg</a>
                        <span class="label">MỚI</span>
                    </li>
                    <li>
                        <a href="#">Nguồn cung gạo căng thẳng do ảnh hưởng thời tiết...</a>
                        <span class="label">MỚI</span>
                    </li>
                    <li>
                        <a href="#">Xuất khẩu ngũ cốc Việt Nam đạt kỷ lục mới</a>
                        <span class="label">MỚI</span>
                    </li>

                </ul>
            </div>
            <div class="notification-section">
                <div class="date-time">
                    <p>Thứ Ba, ngày 19 tháng 11 năm 2024</p>
                </div>
                <div class="notifications">
                    <h3>THÔNG BÁO</h3>
                    <img src="image/new/thông-báo-giả-mạo.jpg" alt="Thông báo">
                    <ul>
                        <li><a href="#">Thông báo: Giả mạo website FoodMart</a></li>
                        <li><a href="#">Thông báo: về việc lựa chọn Tổ chức đấu giá tài sản</a></li>
                        <li><a href="#">QĐ phê duyệt kế hoạch lựa chọn nhà thầu tư vấn dự án...</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>




</main>

<section class="google-map">
    <div class="mapouter">
        <div class="gmap_canvas">
            <iframe width="100%" height="500" id="gmap_canvas"
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.2257143843904!2d106.78732442480627!3d10.870429689284114!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3175276398969f7b%3A0x9672b7efd0893fc4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBOw7RuZyBMw6JtIFRQLiBI4buTIENow60gTWluaA!5e0!3m2!1svi!2s!4v1698550669366!5m2!1svi!2s"
                    frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
            <a href="https://getasearch.com/fmovies"></a>
            <br>
            <style>
                .mapouter {
                    position: relative;
                    text-align: right;
                    height: 500px;
                    width: 100%;
                }
            </style>
            <a href="https://www.embedgooglemap.net">embedgooglemap.net</a>
            <style>
                .gmap_canvas {
                    overflow: hidden;
                    background: none !important;
                    height: 500px;
                    width: 100%;
                }
            </style>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>
<script src="js/home.js"></script>


</body>

</html>