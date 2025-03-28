const PHIVANCHUYEN = 30000;
const PHIVANCHUYEN_DOUOI_5 = 50000; // Phí vận chuyển mới khi totalQuantity > 5
let priceFinal = document.getElementById("checkout-cart-price-final");
let phiVanChuyenElement = document.querySelector(".chk-free-ship span");
let cartTotalElement = document.getElementById("checkout-cart-total");
let totalAmountInput = document.getElementById("totalAmountInput");




// Hàm cập nhật tổng tiền
function updateTotal(isGiaoTanNoi) {
    let cartTotal = parseInt(cartTotalElement.textContent.replace(/\D/g, ""));
    let totalQuantityElement = document.querySelector(".count-1");
    let phiVanChuyen = isGiaoTanNoi ? (parseInt(totalQuantityElement.textContent) > 5 ? PHIVANCHUYEN_DOUOI_5 : PHIVANCHUYEN) : 0;
    let finalTotal = cartTotal + phiVanChuyen;

    if (isGiaoTanNoi) {
        phiVanChuyenElement.textContent = `${phiVanChuyen.toLocaleString()} ₫`; // Hiển thị phí vận chuyển
    } else {
        phiVanChuyenElement.textContent = "0 ₫";
    }

    priceFinal.textContent = `${finalTotal.toLocaleString()} ₫`;
    totalAmountInput.value = finalTotal;
    document.getElementById("shippingFeeInput").value = phiVanChuyen;
}

// Hàm chuyển đổi giao diện giữa các tùy chọn giao hàng
function toggleDeliveryOptions() {
    const btnGiaoTanNoi = document.getElementById("giaotannoi");
    const btnTuDenLay = document.getElementById("tudenlay");
    const giaoTanNoiGroup = document.getElementById("giaotannoi-group");
    const tuDenLayGroup = document.getElementById("tudenlay-group");
    const diaChiNhan = document.getElementById("diachinhan");

    // Khi chọn "Giao tận nơi"
    btnGiaoTanNoi.addEventListener("click", () => {
        btnGiaoTanNoi.classList.add("active");
        btnTuDenLay.classList.remove("active");
        giaoTanNoiGroup.style.display = "block";
        tuDenLayGroup.style.display = "none";
        diaChiNhan.style.display = "block";
        updateTotal(true); // Thêm phí vận chuyển


    });

    // Khi chọn "Giao tận nơi"
    btnGiaoTanNoi.addEventListener("click", () => {
        btnGiaoTanNoi.classList.add("active");
        btnTuDenLay.classList.remove("active");
        tuDenLayGroup.style.display = "none";
        diaChiNhan.style.display = "block";
        updateTotal(true);
    });

    // Khi chọn "Tự đến lấy"
    btnTuDenLay.addEventListener("click", () => {
        btnTuDenLay.classList.add("active");
        btnGiaoTanNoi.classList.remove("active");
        tuDenLayGroup.style.display = "block";
        diaChiNhan.style.display = "none";
        updateTotal(false);
    });
}
function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Thiết lập thuộc tính min cho input date
const dateInput = document.getElementById('deliveryDate');
dateInput.min = getCurrentDate();
// Khởi tạo trang thanh toán khi DOM sẵn sàng
document.addEventListener("DOMContentLoaded", function () {
    getCurrentDate();
    toggleDeliveryOptions();

    // Tính toán totalFinal ban đầu
    let cartTotal = parseInt(cartTotalElement.textContent.replace(/\D/g, ""));
    let totalFinal = cartTotal + PHIVANCHUYEN; // Ban đầu là "Giao tận nơi"

    // Cập nhật giá trị cho input hidden sau khi totalFinal đã được tính toán
    totalAmountInput.value = totalFinal;


});