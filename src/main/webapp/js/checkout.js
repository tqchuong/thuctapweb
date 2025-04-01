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
        ward.style.display = "none";
        district.style.display = "none";
        province.style.display = "none";
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
document.addEventListener("DOMContentLoaded", function () {
    updateTotal(true);

    const token = "b07a48aa-0c5c-11f0-bdfc-6a0eda42fc14";

    async function fetchData(url, method, body = null) {
        try {
            const response = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json", "Token": token },
                body: body ? JSON.stringify(body) : null
            });
            return await response.json();
        } catch (error) {
            console.error("Lỗi khi lấy dữ liệu:", error);
        }
    }

    async function fetchProvinces() {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", "GET");
        if (data?.code === 200) populateOptions("province", data.data, "ProvinceID", "ProvinceName");
    }

    async function fetchDistricts(provinceId) {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/district", "POST", { province_id: parseInt(provinceId) });
        if (data?.code === 200) populateOptions("district", data.data, "DistrictID", "DistrictName");
    }

    async function fetchWards(districtId) {
        let data = await fetchData("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward", "POST", { district_id: parseInt(districtId) });
        if (data?.code === 200) populateOptions("ward", data.data, "WardCode", "WardName");
    }

    function populateOptions(elementId, data, valueField, textField) {
        const select = document.getElementById(elementId);
        select.innerHTML = "<option value=''>Chọn...</option>";
        data.forEach(item => {
            let option = document.createElement("option");
            option.value = item[valueField];
            option.textContent = item[textField];
            select.appendChild(option);
        });
    }

    function updateAddressInput() {
        const provinceText = document.getElementById("province").selectedOptions[0]?.text || "";
        const districtText = document.getElementById("district").selectedOptions[0]?.text || "";
        const wardText = document.getElementById("ward").selectedOptions[0]?.text || "";
        document.getElementById("diachinhan").value = `${wardText}, ${districtText}, ${provinceText}`;
    }

    document.getElementById("province").addEventListener("change", function () {
        let provinceId = this.value;
        if (provinceId) fetchDistricts(provinceId);
    });

    document.getElementById("district").addEventListener("change", function () {
        let districtId = this.value;
        if (districtId) fetchWards(districtId);
    });

    document.getElementById("ward").addEventListener("change", updateAddressInput);

    fetchProvinces();
});