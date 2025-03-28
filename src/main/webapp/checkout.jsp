<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/checkout.css">
    <link rel="stylesheet" href="font/font-awesome-pro-v6-6.2.0/css/all.min.css" />
    <title>Document</title>
</head>

<body>
<form action="checkout" method="post" class="checkout-form" onsubmit="return handlePayment(event)">
    <div class="checkout-page">
        <div class="checkout-header">
            <div class="checkout-return">
                <button onclick="history.back()"><i class="fa-regular fa-chevron-left"></i></button>
            </div>
            <h2 class="checkout-title">Thanh toán</h2>
        </div>
        <main class="checkout-section container">
            <div class="checkout-col-left">
                <div class="checkout-row">
                    <div class="checkout-col-title">Thông tin đơn hàng</div>
                    <div class="checkout-col-content">
                        <!-- Hình thức giao nhận -->
                        <div class="content-group">
                            <p class="checkout-content-label">Hình thức giao nhận</p>
                            <div class="checkout-type-order">
                                <input type="radio" id="giaotannoi" name="shippingMethod" value="Giao tận nơi">
                                <label for="giaotannoi">
                                    <i class="fa-duotone fa-moped" style="--fa-primary-color: dodgerblue;"></i>
                                    Giao tận nơi
                                </label>
                                <input type="radio" id="tudenlay" name="shippingMethod" value="Tự đến lấy">
                                <label for="tudenlay">
                                    <i class="fa-duotone fa-box-heart" style="--fa-primary-color: pink;"></i>
                                    Tự đến lấy
                                </label>
                            </div>
                        </div>

                        <!-- Ngày giao hàng -->
                        <div class="content-group">
                            <p class="checkout-content-label">Ngày giao hàng</p>
                            <div class="date-order">
                                <!-- Nội dung này sẽ được thêm động bởi setupDeliveryDates() -->
                            </div>
                            <input type="date" name="deliveryDate" id="deliveryDate" style="margin-top: 10px;"required>
                        </div>


                        <div class="content-group" id="delivery-time-group">
                            <p class="checkout-content-label">Thời gian nhận hàng</p>
                            <div class="delivery-time">
                                <input type="radio" name="deliveryTime" id="deliverytime" value="specific" class="radio" required>
                                <label for="deliverytime">Nhận vào giờ</label>
                                <select class="choise-time" name="specificDeliveryTime">
                                    <c:forEach var="hour" begin="8" end="21">
                                        <option value="${hour}:00">${hour}:00 - ${hour + 1}:00</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <!-- Chi nhánh tự đến lấy -->
                        <div class="content-group" id="tudenlay-group">
                            <p class="checkout-content-label">Lấy hàng tại chi nhánh</p>
                            <div class="delivery-time">
                                <input type="radio" name="recipientAddress" id="chinhanh-1" class="radio" value="Khu phố 6, Phường Linh Trung, TP Thủ Đức, TP HCM">
                                <label for="chinhanh-1">Khu phố 6, Phường Linh Trung, TP Thủ Đức, TP HCM</label>
                            </div>
                            <div class="delivery-time">
                                <input type="radio" name="recipientAddress" id="chinhanh-2" class="radio" value="Nhơn Tân, An Nhơn, Bình Định">
                                <label for="chinhanh-2">Nhơn Tân, An Nhơn, Bình Định</label>
                            </div>
                        </div>

                        <!-- Ghi chú đơn hàng -->
                        <div class="content-group">
                            <p class="checkout-content-label">Ghi chú đơn hàng</p>
                            <textarea name="orderNote" class="note-order" placeholder="Nhập ghi chú"></textarea>
                        </div>
                    </div>
                </div>

                <!-- Thông tin người nhận -->
                <div class="checkout-row">
                    <div class="checkout-col-title">Thông tin người nhận</div>
                    <div class="checkout-col-content">
                        <div class="content-group">
                            <div class="form-group">
                                <input id="tennguoinhan" name="recipientName" type="text" placeholder="Tên người nhận" class="form-control" required>
                                <span class="form-message"></span>
                            </div>
                            <div class="form-group">
                                <input id="sdtnhan" name="recipientPhone" type="text" placeholder="Số điện thoại nhận hàng" class="form-control" required>
                                <span class="form-message"></span>
                            </div>
                            <div class="form-group">
                                <input id="diachinhan" name="recipientAddress" type="text" placeholder="Địa chỉ nhận hàng" class="form-control chk-ship" >
                                <span class="form-message"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Chi tiết đơn hàng -->
            <div class="checkout-col-right">
                <p class="checkout-content-label">Đơn hàng</p>
                <div class="bill-total" id="list-order-checkout">
                    <c:forEach var="item" items="${sessionScope.cart.list}">
                        <div class="food-total">
                            <div class="count">${item.quantity}x</div>
                            <div class="info-food">
                                <div class="name-food">${item.productName}</div>
                                <div class="price">${item.price}&nbsp;₫</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="bill-payment">
                    <div class="total-bill-order">
                        <div class="priceFlx">
                            <div class="text">Tiền hàng <span class="count-1">${sessionScope.cart.totalQuantity} món</span></div>
                            <div class="price-detail">
    <span id="checkout-cart-total">
        <c:choose>
            <c:when test="${not empty sessionScope.discountedTotal}">
                <fmt:formatNumber value="${sessionScope.discountedTotal}" type="number" pattern="#,##0" />₫
            </c:when>
            <c:otherwise>
                <fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="number" pattern="#,##0" />₫
            </c:otherwise>
        </c:choose>
    </span>
                            </div>
                        </div>
                        <input type="hidden" name="shippingFee" id="shippingFeeInput" value="0">
                        <div class="priceFlx chk-ship">
                            <div class="text">Phí vận chuyển</div>
                            <div class="price-detail chk-free-ship"><span>${shippingFee}&nbsp;₫</span></div>
                        </div>
                    </div>
                </div>
                <div class="policy-note">
                    Bằng việc bấm vào nút “Đặt hàng”, tôi đồng ý với
                    <a href="#" target="_blank">chính sách hoạt động</a>
                    của chúng tôi.
                </div>


            <div class="payment-options" bis_skin_checked="1">
                <div bis_skin_checked="1">
                    <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff">
                        <input type="radio" id="rdPaymentTypeCod" name="paymentType" value="COD" checked="">&nbsp;&nbsp;
                        <i class="fa-solid fa-hand-holding-usd"></i>
                        <span>&nbsp;&nbsp;Thanh toán khi nhận hàng</span>
                        <input type="hidden" name="paymentStatus" value="Chưa thanh toán">
                    </label>

                    <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff;">
                        <input type="radio" id="rdPaymentTypeMomo" name="paymentType" value="MOMO">&nbsp;&nbsp;
                        <i class="fa-solid fa-wallet"></i>
                        <span>&nbsp;&nbsp;Thanh toán bằng ví điện tử</span>
                        <input type="hidden" name="paymentStatus" value="Đã thanh toán">
                    </label>

                    <label style="border: 1px solid #ced4da;height: calc(1.5em + 0.75rem + 2px);border-radius: 0.25rem;width: 100%; padding: 6px; background: #fff;">
                        <input type="radio" id="rdPaymentTypeVnpay" name="paymentType" value="VNPAY">&nbsp;&nbsp;
                        <i class="fa-solid fa-credit-card"></i>
                        <span>&nbsp;&nbsp;Thanh toán qua VNPay</span>
                        <input type="hidden" name="paymentStatus" value="Chưa thanh toán">
                    </label>

                </div>

                <!-- Tổng tiền -->
                <input type="hidden" name="totalAmount" id="totalAmountInput" value="${totalFinal}">
                <div class="total-checkout">
                    <div class="text">Tổng tiền</div>
                    <div class="price-bill">
                        <div class="price-final" id="checkout-cart-price-final" >${totalFinal}&nbsp;₫</div>
                    </div>
                </div>
                <button type="submit" class="complete-checkout-btn">Đặt hàng</button>
            </div>
            </div>
        </main>
    </div>
</form>
<script>
    function handlePayment(event) {
        event.preventDefault();
        let paymentType = document.querySelector('input[name="paymentType"]:checked').value;

        if (paymentType === "VNPAY") {
            // Tạo một hidden form để submit
            let form = document.createElement("form");
            form.method = "POST";
            form.action = "checkout"; // Gửi đến CheckoutServlet

            // Thêm tất cả các field từ form gốc
            let originalForm = document.querySelector('.checkout-form');
            let inputs = originalForm.querySelectorAll('input, select, textarea');

            inputs.forEach(input => {
                if (!input.name) return;
                let clone = input.cloneNode();
                clone.value = input.value;
                form.appendChild(clone);
            });

            document.body.appendChild(form);
            form.submit();
        } else {
            // COD hoặc phương thức khác
            document.querySelector('form.checkout-form').submit();
        }
    }

</script>

    <script src="js/checkout.js"></script>

</body>

</html>