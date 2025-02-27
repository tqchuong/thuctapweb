document.addEventListener("DOMContentLoaded", function () {
    // Cập nhật countdown timer
    function updateTimer() {
        const now = new Date();

        const currentHour = now.getHours();
        const currentMinute = now.getMinutes();
        const currentSecond = now.getSeconds();

        // Các khung giờ Flash Sale
        const slots = [
            { timeRange: [9, 11], id: 'slot9', message: 'Khung giờ 9h-11h' },
            { timeRange: [12, 15], id: 'slot12', message: 'Khung giờ 12h-15h' },
            { timeRange: [16, 22], id: 'slot16', message: 'Khung giờ 16h-22h' },
            { timeRange: [23, 8], id: 'slot23', message: 'Khung giờ 23h-8h' }
        ];

        let ongoingSlot = null;
        let nextSlot = null;

        for (let i = 0; i < slots.length; i++) {
            const slot = slots[i];
            const [startHour, endHour] = slot.timeRange;

            if (
                (startHour <= currentHour && currentHour <= endHour) || // Khung giờ cùng ngày
                (startHour > endHour && (currentHour >= startHour || currentHour <= endHour)) // Khung giờ qua ngày mới
            ) {
                ongoingSlot = slot;
                break;
            }

            if (currentHour < startHour || (startHour > endHour && currentHour <= endHour)) {
                nextSlot = slot;
                break;
            }
        }

        const timerMessage = document.getElementById('timer-message');
        const timerElement = document.getElementById('timer');

        if (ongoingSlot) {
            timerMessage.textContent = "Đang diễn ra: " + ongoingSlot.message;
            timerElement.textContent = `${String(currentHour).padStart(2, '0')}:${String(currentMinute).padStart(2, '0')}:${String(currentSecond).padStart(2, '0')}`;
        } else if (nextSlot) {
            const targetTime = new Date();
            targetTime.setHours(nextSlot.timeRange[0], 0, 0, 0);
            if (currentHour >= 23) targetTime.setDate(targetTime.getDate() + 1);

            const timeDiff = targetTime - now;
            const hoursLeft = Math.floor(timeDiff / (1000 * 60 * 60));
            const minutesLeft = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
            const secondsLeft = Math.floor((timeDiff % (1000 * 60)) / 1000);

            timerMessage.textContent = "Bắt đầu sau: " + nextSlot.message;
            timerElement.textContent = `${String(hoursLeft).padStart(2, '0')}:${String(minutesLeft).padStart(2, '0')}:${String(secondsLeft).padStart(2, '0')}`;
        } else {
            timerMessage.textContent = "Không còn Flash Sale ";
            timerElement.textContent = "--:--:--";
        }
    }

    // Xử lý sự kiện khi bấm "Đặt hàng"
    const orderButtons = document.querySelectorAll('.order-item');
    orderButtons.forEach(button => {
        button.addEventListener('click', function (event) {
        const productElement = this.closest('.col-product');
            const productSlot = this.getAttribute('data-slot'); // Lấy data-slot từ thẻ button
            const productHref = this.getAttribute('data-href');
        const currentSlot = getCurrentSlot();

        if (!currentSlot) {
            alert("Flash Sale đã kết thúc. Hãy chờ đợt tiếp theo!");
            event.preventDefault();
            return;
        }

        if (productSlot !== currentSlot.id) {
            alert(`Chưa đến giờ hoặc đã kết thúc . ${currentSlot.message}`);
            event.preventDefault();
            return;
        }

        alert("Đặt hàng thành công!");
        // Chuyển hướng đến trang chi tiết sản phẩm
        window.location.href = productHref;
    });
});

    // Hàm xác định khung giờ hiện tại
    function getCurrentSlot() {
        const now = new Date();
        const currentHour = now.getHours();

        const slots = [
            { timeRange: [9, 11], id: 'slot9', message: 'Khung giờ 9h-11h' },
            { timeRange: [12, 15], id: 'slot12', message: 'Khung giờ 12h-15h' },
            { timeRange: [16, 22], id: 'slot16', message: 'Khung giờ 16h-22h' },
            { timeRange: [23, 8], id: 'slot23', message: 'Khung giờ 23h-8h' }
        ];

        for (let slot of slots) {
            const [startHour, endHour] = slot.timeRange;

            if (
                (startHour <= currentHour && currentHour <= endHour) ||
                (startHour > endHour && (currentHour >= startHour || currentHour <= endHour))
            ) {
                return slot;
            }
        }
        return null;
    }

    // Cập nhật timer mỗi giây
    setInterval(updateTimer, 1000);

    // Hiển thị sản phẩm mặc định khi tải trang
    const currentSlot = getCurrentSlot();
    if (currentSlot) {
        selectedSlotId = currentSlot.id;
        filterProducts('all', currentSlot.id);
    } else {
        alert("Hiện không có khung giờ Flash Sale nào đang hoạt động.");
    }

});
let perPage = 8; // Số sản phẩm hiển thị mỗi trang
let currentPage = 1; // Trang hiện tại

// Lấy danh sách sản phẩm từ DOM
let products = Array.from(document.querySelectorAll(".col-product"));

// Hiển thị sản phẩm từng trang
function displayList(productAll, perPage, currentPage) {
    let start = (currentPage - 1) * perPage;
    let end = currentPage * perPage;
    let productShow = productAll.slice(start, end);

    // Ẩn tất cả sản phẩm
    products.forEach(product => (product.style.display = "none"));

    // Hiển thị sản phẩm của trang hiện tại
    productShow.forEach(product => (product.style.display = "block"));
}

// Hiển thị sản phẩm và thiết lập phân trang
function showHomeProduct(products) {
    displayList(products, perPage, currentPage);
    setupPagination(products, perPage);
}

// Gắn trang
function setupPagination(productAll, perPage) {
    document.querySelector(".page-nav-list").innerHTML = ""; // Xóa phân trang cũ
    let pageCount = Math.ceil(productAll.length / perPage);

    // Giới hạn chỉ hiển thị tối đa 5 trang
    let pageLimit = 5;
    let startPage = currentPage > 3 ? currentPage - 2 : 1;
    let endPage = Math.min(startPage + pageLimit - 1, pageCount);

    // Thêm nút "<" để chuyển sang trang trước
    let prevButton = document.createElement("li");
    prevButton.classList.add("page-nav-item");
    prevButton.innerHTML = `<a href="javascript:;"><i class="fa-solid fa-left" style="color: #B5292F;"></i></a>`;
    prevButton.addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            setupPagination(productAll, perPage);
            displayList(productAll, perPage, currentPage);
            window.scrollTo(0, 600);
        }
    });
    document.querySelector(".page-nav-list").appendChild(prevButton);

    // Hiển thị các trang hiện tại
    for (let i = startPage; i <= endPage; i++) {
        let li = paginationChange(i, productAll);
        document.querySelector(".page-nav-list").appendChild(li);
    }

    // Thêm nút ">" để chuyển sang trang sau
    let nextButton = document.createElement("li");
    nextButton.classList.add("page-nav-item");
    nextButton.innerHTML = `<a href="javascript:;"><i class="fa-solid fa-right" style="color: #B5292F;"></i></a>`;
    nextButton.addEventListener("click", function () {
        if (currentPage < pageCount) {
            currentPage++;
            setupPagination(productAll, perPage);
            displayList(productAll, perPage, currentPage);
            window.scrollTo(0, 600);
        }
    });
    document.querySelector(".page-nav-list").appendChild(nextButton);
}

// Tạo nút trang
function paginationChange(page, productAll) {
    let node = document.createElement("li");
    node.classList.add("page-nav-item");
    node.innerHTML = `<a href="javascript:;">${page}</a>`;

    // Đặt trang hiện tại là active
    if (currentPage === page) node.classList.add("active");

    node.addEventListener("click", function () {
        currentPage = page;
        displayList(productAll, perPage, currentPage);

        // Xóa class active khỏi các nút khác
        document.querySelectorAll(".page-nav-item.active").forEach(item => {
            item.classList.remove("active");
        });

        node.classList.add("active");

        // Cuộn về đầu phần sản phẩm
        window.scrollTo(0, 600);
    });

    return node;
}

// Hiển thị sản phẩm theo khung giờ và danh mục
function filterProducts(category, currentSlotId) {
    const allProducts = Array.from(document.querySelectorAll('.col-product'));
    const filteredProducts = allProducts.filter(product => {
        const productCategory = product.getAttribute('data-loai');
        const productSlot = product.getAttribute('data-slot');
        return (category === 'all' || productCategory === category) && productSlot === currentSlotId;
    });

    showHomeProduct(filteredProducts); // Hiển thị các sản phẩm đã lọc và phân trang
}

let selectedSlotId = null; // Lưu khung giờ được chọn

// Xử lý sự kiện khi chọn khung giờ
const slotButtons = document.querySelectorAll('.slot-button');
slotButtons.forEach(button => {
    button.addEventListener('click', function () {
        selectedSlotId = button.getAttribute('data-slot');
        const activeCategory = document.querySelector('.category-button.active')?.getAttribute('data-loai') || 'all';

        filterProducts(activeCategory, selectedSlotId);

        // Đánh dấu nút khung giờ hiện tại
        slotButtons.forEach(btn => btn.classList.remove('active'));
        this.classList.add('active');
    });
});

// Xử lý sự kiện khi chọn danh mục
const categoryButtons = document.querySelectorAll('.category-button');
categoryButtons.forEach(button => {
    button.addEventListener('click', function () {
        if (!selectedSlotId) {
            alert("Vui lòng chọn khung giờ trước!");
            return;
        }

        const category = button.getAttribute('data-loai');
        filterProducts(category, selectedSlotId);

        // Đánh dấu nút danh mục hiện tại
        categoryButtons.forEach(btn => btn.classList.remove('active'));
        this.classList.add('active');
    });
});