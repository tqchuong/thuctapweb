
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="footer">

    <div class="widget-area">
        <div class="container">
            <div class="widget-row">
                <div class="widget-row-col">
                    <div class="logo">
                        <a href="">
                            <img alt="FoodMart Logo" src="image/shoppingcart/6.png"
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
                    <p><b>Fax:</b> 1234 567 890</p>
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
