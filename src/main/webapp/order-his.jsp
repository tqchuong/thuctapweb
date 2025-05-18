
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Thông tin tài khoản</title>
    <link href="css/products.css" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css"/>
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

            <li class="menu-list-item"><a class="hotpro-link" href="flashsale">
                <i class="fa-solid fa-bolt fa-shake" style="color: #FFD700;"></i> Flashsale
            </a>
            </li>

        </ul>

    </div>
</nav>

<main class="main-wrapper">
    <div class="container" id="order-history">
        <div class="main-account">
            <div class="main-account-header">
                <h3>Quản lý đơn hàng của bạn</h3>
                <p>Xem chi tiết, trạng thái của những đơn hàng đã đặt.</p>
            </div>
            <div class="main-account-body">
                <div class="order-history-section">
                    <c:if test="${empty orders}">
                        <div class="empty-order-section" style="text-align: center;">
                            <img src="image/empty-order.jpg" alt="" class="empty-order-img" style="width: 250px;margin-bottom: 20px;">
                            <p>Chưa có đơn hàng nào</p>
                        </div>
                    </c:if>

                    <c:forEach var="order" items="${orders}">
                        <div class="order-history-group">
                            <div class="modal detail-order open" id="modal-${order.id}" style="display: none;">

                            <div class="modal-container mdl-cnt" style="width: 550px;">
                                    <h3 class="modal-container-title" style="display: inline-block;margin-top: 16px;margin-left: 20px;text-transform: uppercase;">
                                        Thông tin đơn hàng</h3>
                                    <button class="form-close" onclick="closeModal()" ><i class="fa-regular fa-xmark"></i></button>
                                    <div class="detail-order-content" style="padding: 20px;"><ul class="detail-order-group">

                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-calendar-days"></i> Ngày đặt hàng</span>
                                            <span class="detail-order-item-right"><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy"/></span>

                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-truck"></i> Hình thức giao</span>
                                            <span class="detail-order-item-right">${order.shippingMethod}</span>
                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-clock"></i> Ngày nhận hàng</span>
                                            <span class="detail-order-item-right">${order.deliveryDate}</span>
                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-location-dot"></i> Địa điểm nhận</span>
                                            <span class="detail-order-item-right">${order.shippingAddress}</span>
                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-thin fa-person"></i> Người nhận</span>
                                            <span class="detail-order-item-right">${order.receiverName}</span>
                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-phone"></i> Số điện thoại nhận</span>
                                            <span class="detail-order-item-right">${order.receiverPhone}</span>
                                        </li>
                                        <li class="detail-order-item">
                                            <span class="detail-order-item-left"><i class="fa-light fa-credit-card"></i> Thanh toán</span>
                                            <span class="detail-order-item-right">${order.paymentMethod}</span>
                                        </li>

                                    </ul></div>
                                </div>
                            </div>
                            <!-- Lặp qua danh sách sản phẩm chi tiết -->
                            <c:forEach var="detail" items="${order.orderDetails}">
                                <div class="order-history">
                                    <div class="order-history-left">
                                        <img src="${detail.img}" alt="">
                                        <div class="order-history-info">
                                            <h4>${detail.productName}</h4>
                                            <p class="order-history-quantity">x${detail.quantity}</p>
                                        </div>
                                    </div>
                                    <div class="order-history-right">
                                        <div class="order-history-price">
                                            <span class="order-history-current-price"><fmt:formatNumber value="${detail.unitPrice}" type="number" pattern="#,###" />&nbsp;₫</span>
                                            <a href="productDetails?id=${detail.productID}" class="btn-review">
                                                <i class="fa-solid fa-star"></i> Đánh giá
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <p class="order-history-note"><i class="fa-light fa-pen"></i>
                                <c:if test="${empty order.orderNote}">
                                    Không có ghi chú
                                </c:if>
                                    ${order.orderNote}
                            </p>

                            <!-- Hiển thị tổng tiền của đơn hàng -->
                            <div class="order-history-control">
                                <div class="order-history-status">
                                    <span class="order-history-status-sp ${order.orderStatus == 'Chưa xử lý' || order.orderStatus == 'Đã hủy đơn hàng' ? 'no-complete' : 'complete'}">${order.orderStatus}</span>
                                    <c:if test="${order.orderStatus eq 'Chưa xử lý'}">
                                        <form action="cancel-order" method="POST" style="display: inline;"
                                              onsubmit="return confirmCancelOrder(event, this)">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <button type="submit" class="order-history-status-sp no-complete">
                                                <i class="fas fa-trash"></i> Hủy đơn hàng
                                            </button>
                                        </form>
                                    </c:if>

                                    <span class="order-history-status-sp ${order.payments.paymentStatus == 'Chưa thanh toán' ? 'no-complete' : 'complete'}">
                                            ${order.payments.paymentStatus}
                                    </span>

                                    <c:if test="${order.payments.paymentStatus eq 'Chưa thanh toán'}">
                                        <form action="confirm-payment" method="POST" onsubmit="return confirmPayment(event, this)" style="display:inline;">
                                            <input type="hidden" name="orderId" value="${order.id}" />
                                            <button type="submit" class="order-history-status-sp no-complete">
                                                <i class="fa-solid fa-money-bill-wave"></i> Thanh toán
                                            </button>
                                        </form>
                                    </c:if>


                                    <button id="order-history-detail" onclick="openModal('${order.id}')"><i class="fa-regular fa-eye"></i> Xem chi tiết</button>



                                </div>
                                <div class="order-history-total">
                                    <span class="order-history-total-desc">Tổng tiền: </span>
                                    <span class="order-history-toltal-price"><fmt:formatNumber value="${order.totalAmount}" type="number" pattern="#,###" />&nbsp;₫</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>
    </div>



</main>


<jsp:include page="footer.jsp"/>

<script>

    // Mở modal
    function openModal(orderId) {
        document.getElementById("modal-" + orderId).style.display = "flex";
    }

    function closeModal() {
        // Đóng tất cả modal nếu có nhiều
        document.querySelectorAll(".modal.detail-order").forEach(modal => {
            modal.style.display = "none";
        });
    }
    function confirmPayment(event, form) {
        event.preventDefault();

        if (confirm("Xác nhận bạn đã thanh toán đơn hàng này?")) {
            const formData = new FormData(form);

            fetch(form.action, {
                method: form.method,
                body: new URLSearchParams(formData),
            })
                .then(response => {
                    if (response.ok) {
                        alert("Thanh toán thành công!");
                        window.location.reload();
                    } else {
                        alert("Thanh toán thất bại.");
                    }
                })
                .catch(error => {
                    console.error("Lỗi khi thanh toán:", error);
                    alert("Đã xảy ra lỗi.");
                });
        }

        return false;
    }
    function confirmCancelOrder(event, form) {
        // Ngăn chặn hành động mặc định của form
        event.preventDefault();

        // Hiển thị hộp thoại xác nhận
        if (confirm("Bạn có muốn hủy đơn hàng không?")) {
            const formData = new FormData(form);

            // Gửi yêu cầu hủy đơn hàng qua Fetch API
            fetch(form.action, {
                method: form.method,
                body: new URLSearchParams(formData), // Chuyển dữ liệu form thành URL-encoded
            })
                .then(response => {
                    if (response.ok) {
                        // Hiển thị thông báo thành công
                        alert("Hủy thành công!");
                        // Tải lại trang hoặc cập nhật danh sách đơn hàng
                        window.location.reload();
                    } else {
                        // Xử lý lỗi nếu hủy không thành công
                        alert("Hủy thất bại. Vui lòng thử lại.");
                    }
                })
                .catch(error => {
                    console.error("Lỗi khi hủy đơn hàng:", error);
                    alert("Đã xảy ra lỗi. Vui lòng thử lại sau.");
                });
        } else {
            console.log("Người dùng chọn Cancel");
        }

        // Trả về false để không gửi form mặc định
        return false;
    }

</script>




</body>

</html>