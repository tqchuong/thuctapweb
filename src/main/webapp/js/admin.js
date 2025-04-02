// Chuyển đổi tab giữa các danh mục
const sidebars = document.querySelectorAll(".sidebar-list-item.tab-content");
const sections = document.querySelectorAll(".section");

for (let i = 0; i < sidebars.length; i++) {
    sidebars[i].onclick = function () {
        document.querySelector(".sidebar-list-item.active").classList.remove("active");
        document.querySelector(".section.active").classList.remove("active");
        sidebars[i].classList.add("active");
        sections[i].classList.add("active");
    };
}

// Tìm kiếm sản phẩm
document.getElementById("form-search-product").addEventListener("input", function () {
    let keyword = this.value.toLowerCase();
    let products = document.querySelectorAll("#show-product .list");
    products.forEach(product => {
        let title = product.querySelector(".list-info h4").textContent.toLowerCase();
        product.style.display = title.includes(keyword) ? "" : "none";
    });
});

// Tìm kiếm khách hàng
document.getElementById("form-search-user").addEventListener("input", function () {
    let keyword = this.value.toLowerCase();
    let users = document.querySelectorAll("#show-user tr");
    users.forEach(user => {
        let name = user.cells[1].textContent.toLowerCase();
        let contact = user.cells[2].textContent.toLowerCase();
        user.style.display = name.includes(keyword) || contact.includes(keyword) ? "" : "none";
    });
});

// Tìm kiếm đơn hàng
document.getElementById("form-search-order").addEventListener("input", function () {
    let keyword = this.value.toLowerCase();
    let orders = document.querySelectorAll("#showOrder tr");
    orders.forEach(order => {
        let orderId = order.cells[0].textContent.toLowerCase();
        let customer = order.cells[1].textContent.toLowerCase();
        order.style.display = orderId.includes(keyword) || customer.includes(keyword) ? "" : "none";
    });
});

// Lọc trạng thái khách hàng
document.getElementById("tinh-trang-user").addEventListener("change", function () {
    let selectedStatus = this.value;
    let users = document.querySelectorAll("#show-user tr");
    users.forEach(user => {
        let statusElement = user.querySelector(".status-complete, .status-no-complete");
        let isMatch =
            selectedStatus == 2 ||
            (selectedStatus == 1 && statusElement.classList.contains("status-complete")) ||
            (selectedStatus == 0 && statusElement.classList.contains("status-no-complete"));
        user.style.display = isMatch ? "" : "none";
    });
});

// Lọc trạng thái đơn hàng
document.getElementById("tinh-trang").addEventListener("change", function () {
    let selectedStatus = this.value;
    let orders = document.querySelectorAll("#showOrder tr");
    orders.forEach(order => {
        let statusElement = order.querySelector(".status-complete, .status-no-complete");
        let isMatch =
            selectedStatus == 2 ||
            (selectedStatus == 1 && statusElement.classList.contains("status-complete")) ||
            (selectedStatus == 0 && statusElement.classList.contains("status-no-complete"));
        order.style.display = isMatch ? "" : "none";
    });
});

// Hiển thị thông báo
function showToast(type, title, message, duration = 3000) {
    toast({title, message, type, duration});
}


function showToast(type, title, message, duration = 3000) {
    toast({title, message, type, duration});
}


function getPathImage(path) {
    let patharr = path.split("/");
    return "./assets/img/products/" + patharr[patharr.length - 1];
}


// On change Image
function uploadImage(el) {
    let path = "./assets/img/products/" + el.value.split("\\")[2];
    document.querySelector(".upload-image-preview").setAttribute("src", path);
}


// Lọc trạng thái sản phẩm
document.getElementById("the-loai").addEventListener("change", function () {
    let selectedCategory = this.value.toLowerCase();
    let products = document.querySelectorAll("#show-product .list");

    products.forEach((product) => {
        let stockCount = parseInt(product.querySelector(".list-slkho").textContent);
        let categoryText = product.querySelector(".list-category").textContent.toLowerCase();

        if (
            selectedCategory === "tất cả" ||
            (selectedCategory === "đã xóa" && stockCount === 0) ||
            (selectedCategory === "sản phẩm gần hết hàng" && stockCount < 5) ||
            categoryText === selectedCategory
        ) {
            product.style.display = "flex";
        } else {
            product.style.display = "none";
        }
    });
});


// Số sản phẩm hiển thị mỗi trang
let perPage = 5;
let currentPage = 1; // Trang hiện tại

// Lấy danh sách sản phẩm từ DOM
const products = Array.from(document.querySelectorAll(".list"));

// Hiển thị sản phẩm từng trang
function displayList(productAll, perPage, currentPage) {
    let start = (currentPage - 1) * perPage;
    let end = currentPage * perPage;
    let productShow = productAll.slice(start, end);

    // Ẩn tất cả sản phẩm
    products.forEach(product => (product.style.display = "none"));

    // Hiển thị sản phẩm của trang hiện tại
    productShow.forEach(product => (product.style.display = "flex"));
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
        setupPagination(productAll, perPage);
    });

    return node;
}

// Khởi tạo khi DOM sẵn sàng
document.addEventListener("DOMContentLoaded", () => {
    showHomeProduct(products); // Hiển thị trang đầu tiên
});


// Đóng tất cả modal trước khi mở modal mới
function closeAllModals() {
    document.querySelectorAll(".modal").forEach(modal => {
        modal.classList.remove("open");
    });
}

// Reset các giá trị mặc định cho modal thêm/chỉnh sửa khách hàng
function resetCustomerForm() {
    document.getElementById("customer-fullname").value = "";
    document.getElementById("customer-phone").value = "";
    document.getElementById("customer-password").value = "";
    document.getElementById("customer-status").checked = false;
}

// Mở modal thêm mới khách hàng
document.getElementById("btn-add-user").addEventListener("click", function () {
    closeAllModals(); // Đóng các modal khác
    const modal = document.querySelector("#customer-modal");
    modal.classList.add("open");

    // Hiển thị giao diện "Thêm khách hàng mới"
    document.querySelectorAll(".add-customer-e").forEach(item => item.style.display = "block");
    document.querySelectorAll(".edit-customer-e").forEach(item => item.style.display = "none");

    // Hiển thị nút "Đăng ký"
    document.getElementById("signup-button").style.display = "block";

    // Ẩn nút "Lưu thông tin"
    document.getElementById("update-customer-button").style.display = "none";

    // Reset form về mặc định
    resetCustomerForm();
});

// Mở modal chỉnh sửa khách hàng

document.querySelectorAll(".btn-edit-customer").forEach(button => {
    button.addEventListener("click", function () {
        closeAllModals(); // Đóng các modal khác
        const modal = document.querySelector("#customer-modal");
        modal.classList.add("open");

        // Hiển thị giao diện "Chỉnh sửa thông tin"
        document.querySelectorAll(".add-customer-e").forEach(item => item.style.display = "none");
        document.querySelectorAll(".edit-customer-e").forEach(item => item.style.display = "block");

        // Ẩn nút "Đăng ký"
        document.getElementById("signup-button").style.display = "none";

        // Hiển thị nút "Lưu thông tin"
        document.getElementById("update-customer-button").style.display = "block";

        // Lấy dữ liệu khách hàng từ hàng tương ứng trong bảng
        const customerRow = button.closest("tr");
        const id = customerRow.dataset.id; // Lấy ID khách hàng từ thuộc tính data-id
        const fullname = customerRow.cells[1].textContent.trim(); // Lấy tên đầy đủ
        const phone = customerRow.cells[2].textContent.trim(); // Lấy số điện thoại
        const status = customerRow.querySelector(".status-complete, .status-no-complete").textContent.trim(); // Lấy trạng thái

        // Điền dữ liệu vào form
        document.getElementById("customer-id").value = id; // Gán ID vào input ẩn
        document.getElementById("customer-fullname").value = fullname; // Điền tên
        document.getElementById("customer-phone").value = phone; // Điền số điện thoại
        document.getElementById("customer-password").value = ""; // Để trống mật khẩu
        document.getElementById("customer-status").checked = (status === "Hoạt động"); // Đánh dấu trạng thái

        // Cập nhật giá trị của action thành "edit"
        document.getElementById("form-action").value = "edit";
    });
});


// Đóng modal khi nhấn nút close
document.querySelectorAll(".modal-close").forEach(closeButton => {
    closeButton.addEventListener("click", function () {
        const modal = closeButton.closest(".modal");
        modal.classList.remove("open");
    });
});

function openEditCustomerModal(customer) {
    document.getElementById("form-action").value = "edit";
    document.getElementById("customer-id").value = customerRow.dataset.id; // Gán id vào input ẩn
    document.getElementById("customer-fullname").value = customer.fullname;
    document.getElementById("customer-phone").value = customer.phone;
    document.getElementById("customer-password").value = ""; // Để trống mật khẩu
    document.getElementById("customer-status").checked ? "1" : "0";

    // Hiển thị modal
    document.getElementById("customer-modal").style.display = "block";
}

document.addEventListener("DOMContentLoaded", function () {
    const action = document.getElementById("form-action").value; // Lấy giá trị action
    const fullnameInput = document.getElementById("customer-fullname");

    // Kiểm tra action (add/edit)
    if (action === "add") {
        fullnameInput.removeAttribute("readonly"); // Cho phép chỉnh sửa khi thêm mới
    } else if (action === "edit") {
        fullnameInput.setAttribute("readonly", true); // Khóa trường khi chỉnh sửa
    }
});


// Đóng tất cả modal trước khi mở modal mới
function closeAllModals() {
    document.querySelectorAll(".modal").forEach(modal => {
        modal.classList.remove("open");
    });
}

// Reset các giá trị mặc định cho modal thêm/chỉnh sửa sản phẩm
function resetProductForm() {
    document.getElementById("product-id").value = "";
    document.getElementById("ten-mon").value = "";
    document.getElementById("mo-ta").value = "";
    document.getElementById("gia-moi").value = "";
    document.getElementById("so-luong").value = "";
    document.getElementById("chon-mon").value = "1"; // Giá trị mặc định
    document.getElementById("chon-sale").value = "0";
    document.getElementById("phan-tram-giam").value = "0";
    document.getElementById("phan-tram-giam").setAttribute("readonly", "readonly");
    document.querySelector(".upload-image-preview").src = "image/admin/blank-image.png";
}

// Mở modal thêm sản phẩm
document.getElementById("btn-add-product").addEventListener("click", function () {
    closeAllModals(); // Đóng tất cả modal khác
    resetProductForm(); // Reset form về mặc định

    const modal = document.querySelector(".modal.add-product");
    modal.classList.add("open");

    // Hiển thị chế độ "Thêm sản phẩm"
    document.querySelectorAll(".add-product-e").forEach(item => item.style.display = "block");
    document.querySelectorAll(".edit-product-e").forEach(item => item.style.display = "none");

    // Hiển thị nút "Thêm sản phẩm"
    document.getElementById("add-product-button").style.display = "block";

    // Ẩn nút "Lưu thay đổi"
    document.getElementById("update-product-button").style.display = "none";

    // Gán giá trị action thành "add"
    document.getElementById("action").value = "add";
});

// Mở modal chỉnh sửa sản phẩm
document.querySelectorAll(".btn-edit-product").forEach(button => {
    button.addEventListener("click", function () {
        closeAllModals();

        const modal = document.querySelector(".modal.add-product");
        modal.classList.add("open");

        // Hiển thị chế độ "Chỉnh sửa sản phẩm"
        document.querySelectorAll(".add-product-e").forEach(item => item.style.display = "none");
        document.querySelectorAll(".edit-product-e").forEach(item => item.style.display = "flex");

        // Đảm bảo gán giá trị action thành "edit"
        document.getElementById("action").value = "edit";

        // Lấy dữ liệu từ hàng sản phẩm
        const productRow = button.closest(".list");
        const id = productRow.dataset.id;
        const productName = productRow.querySelector(".list-info h4").textContent.trim();
        const shortDescription = productRow.querySelector(".list-note").textContent.trim();
        const price = productRow.querySelector(".list-current-price").textContent.trim();
        const stockQuantity = productRow.querySelector(".list-slkho").textContent.trim();
        const category = productRow.querySelector(".list-category").dataset.categoryId;
        const isSale = productRow.querySelector(".list-sale")?.textContent.trim() === "Có" ? "1" : "0";
        const discount = productRow.querySelector(".list-discount")?.textContent.trim();
        const imageSrc = productRow.querySelector("img").getAttribute("src");

        // Điền dữ liệu vào form
        document.getElementById("product-id").value = id;
        document.getElementById("ten-mon").value = productName;
        document.getElementById("mo-ta").value = shortDescription;
        document.getElementById("gia-moi").value = price;
        document.getElementById("so-luong").value = stockQuantity;
        document.getElementById("chon-mon").value = category;
        document.getElementById("chon-sale").value = isSale;
        document.getElementById("phan-tram-giam").value = isSale === "1" ? discount : "0";
        document.querySelector(".upload-image-preview").src = imageSrc;

        if (isSale === "0") {
            document.getElementById("phan-tram-giam").setAttribute("readonly", "readonly");
        } else {
            document.getElementById("phan-tram-giam").removeAttribute("readonly");
        }
    });
});

// Xử lý trạng thái sale
document.getElementById("chon-sale").addEventListener("change", function () {
    const discountInput = document.getElementById("phan-tram-giam");
    if (this.value === "0") {
        discountInput.value = "0";
        discountInput.setAttribute("readonly", "readonly");
    } else {
        discountInput.removeAttribute("readonly");
    }
});

// Thay đổi ảnh xem trước khi chọn tệp
document.getElementById("up-hinh-anh").addEventListener("change", function (e) {
    const file = e.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (event) {
            document.querySelector(".upload-image-preview").src = event.target.result;
        };
        reader.readAsDataURL(file);
    }
});

// Xử lý gửi form thêm/chỉnh sửa sản phẩm
document.getElementById("product-form").addEventListener("submit", function (e) {
    const action = document.getElementById("action").value;
    const productName = document.getElementById("ten-mon").value.trim();
    const price = document.getElementById("gia-moi").value.trim();
    const stock = document.getElementById("so-luong").value.trim();

    // Kiểm tra dữ liệu hợp lệ
    if (!productName || !price || !stock) {
        alert("Vui lòng điền đầy đủ thông tin!");
        e.preventDefault();
        return;
    }

    console.log(action === "add" ? "Đang thêm sản phẩm..." : "Đang chỉnh sửa sản phẩm...");
});


function showOrderDetails(orderId) {
    fetch(`/getOrderDetails?orderId=${orderId}`)
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert(data.error);
                return;
            }

            // Gán dữ liệu HTML từ response
            document.getElementById("order-details").innerHTML = data.detailsHtml;
            document.getElementById("order-summary").innerHTML = data.summaryHtml;

            // Hiển thị tổng tiền từ response
            console.log("Total Price from API:", data.totalPrice);
            document.getElementById("order-total").textContent = data.totalPrice;

            // Cập nhật trạng thái đơn hàng và thêm sự kiện thay đổi trạng thái
            const statusBtn = document.getElementById("order-status");
            statusBtn.textContent = data.orderStatus === "completed" ? "Đã xử lý" : "Chưa xử lý";
            statusBtn.className = `footer-btn btn-status ${
                data.orderStatus === "completed" ? "completed" : "processing"
            }`;

            // Gắn thông tin orderId và trạng thái hiện tại vào thuộc tính data
            statusBtn.setAttribute("data-order-id", orderId);
            statusBtn.setAttribute("data-status", data.orderStatus);

            // Hiển thị modal chi tiết đơn hàng
            document.querySelector(".modal.detail-order").classList.add("open");
        })
        .catch(error => {
            console.error("Error fetching order details:", error);
            alert("Lỗi khi tải chi tiết đơn hàng!");
        });
}

// Hàm để thay đổi trạng thái đơn hàng
function toggleOrderStatus() {
    const statusBtn = document.getElementById("order-status");
    const orderId = statusBtn.getAttribute("data-order-id");
    const currentStatus = statusBtn.getAttribute("data-status");

    // Chuyển trạng thái
    const newStatus = currentStatus === "completed" ? "pending" : "completed";

    fetch(`/project/updateOrderStatus`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({orderId, status: newStatus}),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Cập nhật giao diện với trạng thái mới
                statusBtn.textContent = newStatus === "completed" ? "Đã xử lý" : "Chưa xử lý";
                statusBtn.className = `footer-btn btn-status ${
                    newStatus === "completed" ? "completed" : "processing"
                }`;
                statusBtn.setAttribute("data-status", newStatus);
                alert("Cập nhật trạng thái thành công!");
            } else {
                alert("Cập nhật trạng thái thất bại!");
            }
        })
        .catch(error => {
            console.error("Error updating order status:", error);
            alert("Lỗi khi cập nhật trạng thái!");
        });
}

// Hàm để đóng modal
function closeModal() {
    document.querySelector(".modal.detail-order").classList.remove("open");
}


document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".order-status").forEach(select => {
        select.addEventListener("change", function () {
            const orderId = this.dataset.orderId;
            const newStatus = this.value;

            console.log(`Cập nhật đơn hàng ${orderId} thành trạng thái: ${newStatus}`);

            fetch('/project//updateOrderStatus', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({orderId, newStatus}),
            })
                .then(response => response.json())
                .then(data => {
                    console.log("Phản hồi từ server:", data);
                    if (data.success) {
                        alert("Cập nhật trạng thái thành công!");
                    } else {
                        alert("Cập nhật trạng thái thất bại!");
                    }
                })
                .catch(error => {
                    console.error("Lỗi cập nhật trạng thái:", error);
                    alert("Có lỗi xảy ra, vui lòng thử lại!");
                });
        });
    });
});


document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', function (event) {
        if (event.target && event.target.classList.contains('btn-delete')) {
            console.log('Nút xóa được nhấn');
            const id = event.target.getAttribute('data-id');
            const type = event.target.getAttribute('data-type');
            console.log('ID:', id, 'Type:', type); // Debug

            if (confirm('Bạn có chắc chắn muốn xóa không?')) {
                deleteItem(type, id);
            }
        }
    });
});

function deleteItem(type, id) {
    fetch(`/project/delete-${type}`, { // URL động
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({id}), // Gửi ID dưới dạng JSON
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Xóa thành công!');
                document.querySelector(`[data-id="${id}"]`).remove(); // Xóa phần tử HTML
            } else {
                alert('Xóa không thành công!');
            }
        })
        .catch(error => console.error('Lỗi:', error));
}

document.querySelectorAll(".product-order-detail").forEach(button => {
    button.addEventListener("click", function () {
        const productId = this.dataset.id; // Lấy ID sản phẩm từ nút
        if (!productId) {
            alert("Không tìm thấy ID sản phẩm.");
            return;
        }

        // Gửi AJAX request để lấy thông tin chi tiết
        fetch(`/project/getOrderDetailsByProductId?productId=${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gọi API.");
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    // Xóa nội dung cũ và thêm dữ liệu mới vào bảng
                    const detailTableBody = document.getElementById("show-product-order-detail");
                    detailTableBody.innerHTML = ""; // Reset bảng
                    data.details.forEach(order => {
                        detailTableBody.innerHTML += `
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.quantity}</td>
                                <td>${order.unitPrice.toLocaleString()} đ</td>
                                <td>${new Date(order.created_at).toLocaleDateString()}</td>
                            </tr>
                        `;
                    });

                    // Hiển thị modal
                    document.querySelector(".modal.detail-order-product").classList.add("open");
                } else {
                    alert(data.message || "Không tìm thấy thông tin chi tiết đơn hàng.");
                }
            })
            .catch(error => {
                console.error("Lỗi khi lấy thông tin chi tiết đơn hàng:", error);
                alert("Đã xảy ra lỗi. Vui lòng thử lại sau.");
            });
    });
});

// Đóng modal khi nhấn nút đóng
document.querySelector(".modal-close").addEventListener("click", function () {
    document.querySelector(".modal.detail-order-product").classList.remove("open");
});









// Mở modal thêm mới voucher
document.getElementById("btn-add-voucher").addEventListener("click", function () {
    closeAllModals();
    const modal = document.querySelector(".modal.add-voucher");
    modal.classList.add("show");

    // Hiển thị giao diện thêm mới voucher
    document.querySelector(".add-voucher-e").style.display = "block";
    document.querySelector(".edit-voucher-e").style.display = "none";

    resetVoucherForm();

    // Chuyển action form sang Add
    document.querySelector(".add-voucher-form").action = `${window.location.origin}/project/couponController`;
    document.getElementById("actionType").value = "add";
});

// Reset form voucher (khi thêm mới)
function resetVoucherForm() {
    document.getElementById("coupon-id").value = '';
    document.getElementById("coupon-code").value = '';
    document.getElementById("discount-amount").value = '';
    document.getElementById("max-discount-amount").value = '';
    document.getElementById("description").value = '';
    document.getElementById("start-date").value = '';
    document.getElementById("end-date").value = '';
    document.getElementById("min-order-amount").value = '';
    document.getElementById("max-usage").value = '';
    document.getElementById("max-usage-per-user").value = '';
    document.getElementById("discount-type").value = 'Percentage';
    document.getElementById("status").value = 'Active';
}

// Đóng tất cả modal đang mở
function closeAllModals() {
    document.querySelectorAll('.modal').forEach(modal => {
        modal.classList.remove('show');
    });
}

// Đóng modal khi nhấn nút đóng
document.querySelectorAll(".modal-close").forEach(button => {
    button.addEventListener("click", function () {
        button.closest(".modal").classList.remove("show");
    });
});

// Xử lý sự kiện khi nhấn nút chỉnh sửa voucher
document.querySelectorAll(".btn-edit-coupon").forEach(button => {
    button.addEventListener("click", function () {
        closeAllModals();
        const modal = document.querySelector(".modal.add-voucher");
        modal.classList.add("show");

        // Hiển thị giao diện chỉnh sửa voucher
        document.querySelector(".add-voucher-e").style.display = "none";
        document.querySelector(".edit-voucher-e").style.display = "block";

        // Lấy dữ liệu voucher từ hàng tương ứng trong bảng
        const voucherRow = button.closest("tr");
        const id = voucherRow.dataset.id;
        const couponCode = voucherRow.cells[1].textContent.trim();
        const discountAmount = parseFloat(voucherRow.cells[2].textContent.replace(/[^0-9.-]+/g, ""));
        const description = voucherRow.cells[3].textContent.trim();
        const status = voucherRow.cells[4].textContent.trim();

        // Điền dữ liệu vào form
        document.getElementById("coupon-id").value = id;
        document.getElementById("coupon-code").value = couponCode;
        document.getElementById("discount-amount").value = discountAmount;
        document.getElementById("description").value = description;
        document.getElementById("status").value = (status === "Active" || status === "Đang hoạt động") ? "Active" : "Inactive";

        // Chuyển action form sang Edit
        document.querySelector(".add-voucher-form").action = `${window.location.origin}/project/couponController`;
        document.getElementById("actionType").value = "edit";

        // Lấy các thông tin bổ sung qua AJAX
        fetch(`${window.location.origin}/project/getVoucherDetails?id=${id}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById("start-date").value = data.startDate.split('T')[0]; // format yyyy-mm-dd
                document.getElementById("end-date").value = data.endDate.split('T')[0];
                document.getElementById("min-order-amount").value = data.minOrderAmount;
                document.getElementById("discount-type").value = data.discountType;
                document.getElementById("max-usage").value = data.maxUsage || '';
                document.getElementById("max-usage-per-user").value = data.maxUsagePerUser || '';
                document.getElementById("max-discount-amount").value = data.maxDiscountAmount || '';
            })
            .catch(err => console.error("Không thể lấy dữ liệu voucher: ", err));
    });
});


// Xử lý sự kiện khi nhấn nút xóa voucher
document.querySelectorAll(".btn-delete").forEach(button => {
    button.addEventListener("click", function () {
        const voucherRow = button.closest("tr");
        const id = voucherRow.dataset.id;  // Lấy id voucher từ thuộc tính data-id

        // Hiển thị hộp thoại xác nhận
        if (confirm("Bạn có chắc chắn muốn xóa không?")) {
            // Gửi yêu cầu AJAX để xóa voucher
            fetch(`${window.location.origin}/project/delete-voucher`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ id: id }) // Gửi id voucher dưới dạng JSON
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Voucher đã được xóa thành công.");
                        voucherRow.remove();  // Xóa dòng voucher khỏi bảng
                    } else {
                        alert("Xóa voucher không thành công.");
                    }
                })
                .catch(err => {
                    console.error("Không thể xóa voucher:", err);
                    alert("Có lỗi xảy ra khi xóa voucher.");
                });
        }
    });
});










