const isLoggedIn = document.getElementById('userStatus').dataset.isLoggedIn;
console.log("isLoggedIn:", isLoggedIn);
function changeImage(imageSrc) {
  document.getElementById('mainImage').src = imageSrc;
}

function updateQuantity(change) {
  const quantityInput = document.getElementById("quantity");
  let currentQuantity = parseInt(quantityInput.value);
  const maxQuantity = parseInt(quantityInput.max);
  if ((currentQuantity + change) >= 1 && (currentQuantity + change) <= maxQuantity) {
    quantityInput.value = currentQuantity + change;
  }
}

function buyNow(productId) {
  // Kiểm tra xem người dùng đã đăng nhập hay chưa
  if (isLoggedIn === "false") {
    alert('Bạn cần đăng nhập để mua hàng.');
    window.location.href = 'login.jsp'; // Chuyển hướng đến trang đăng nhập
    return; // Kết thúc hàm nếu chưa đăng nhập
  }

  // Nếu đã đăng nhập, tiếp tục xử lý thêm sản phẩm vào giỏ hàng
  const quantity = document.getElementById("quantity").value;
  const url = `/project/add-cart?pid=${productId}&quantity=${quantity}`; // URL API giống với addToCart

  fetch(url, { method: 'GET' })
      .then((response) => {
        console.log("Phản hồi từ API:", response);
        return response.json();
      })
      .then((data) => {
        console.log("Dữ liệu JSON:", data);
        if (data.success) {
          alert("Đã thêm vào giỏ hàng");
          // Chuyển hướng đến trang thanh toán
          window.location.href = 'checkout.jsp'; // URL trang thanh toán
        } else {
          alert("Thêm sản phẩm thất bại: " + data.message);
        }
      })
      .catch((error) => console.error("Lỗi khi gọi API:", error));
}
function addToCart(productId) {
  const quantity = document.getElementById("quantity").value;
  const url = `/project/add-cart?pid=${productId}&quantity=${quantity}`;

  fetch(url, { method: 'GET' })
      .then((response) => {
        console.log("Phản hồi từ API:", response);
        return response.json();
      })
      .then((data) => {
        console.log("Dữ liệu JSON:", data);
        if (data.success) {
          alert("Đã thêm vào giỏ hàng");
          updateCartDisplay(data.cart);
        } else {
          alert("Thêm sản phẩm thất bại: " + data.message);
        }
      })
      .catch((error) => console.error("Lỗi khi gọi API:", error));
}

  const mainImage = document.getElementById('mainImage');
  const zoomLens = document.querySelector('.zoom-lens');
  const imageContainer = document.querySelector('.product-main-image');
  const thumbnails = document.querySelectorAll('.thumb-image');

  // Tính toán kích thước khung zoom dựa trên tỉ lệ zoom
  const zoomLevel = 2.5; // Mức độ zoom
  const lensWidth = imageContainer.offsetWidth / zoomLevel;
  const lensHeight = imageContainer.offsetHeight / zoomLevel;
  zoomLens.style.width = lensWidth + 'px';
  zoomLens.style.height = lensHeight + 'px';

  // Khởi tạo biến lưu kích thước ảnh gốc
  let imgWidth, imgHeight;

  // Hàm cập nhật kích thước ảnh gốc
  function updateImageSize() {
    const imgRect = mainImage.getBoundingClientRect();
    imgWidth = imgRect.width;
    imgHeight = imgRect.height;
  }

  // Gọi hàm cập nhật kích thước ảnh ban đầu
  updateImageSize();
  mainImage.addEventListener('mousemove', (e) => {
    // Tính toán vị trí chuột so với ảnh chính
    const { left, top } = imageContainer.getBoundingClientRect();
    let x = e.clientX - left - lensWidth / 2;
    let y = e.clientY - top - lensHeight / 2;

    // Giới hạn vị trí khung zoom trong phạm vi ảnh
    x = Math.max(0, Math.min(x, imgWidth - lensWidth));
    y = Math.max(0, Math.min(y, imgHeight - lensHeight));

    // Cập nhật vị trí khung zoom
    zoomLens.style.left = x + 'px';
    zoomLens.style.top = y + 'px';

    // Tính toán vị trí tương ứng trên ảnh lớn (nếu có)
    const largeImg = mainImage.dataset.largeImg; // Lấy đường dẫn ảnh lớn từ thuộc tính data-large-img
    if (largeImg) {
      // Tính toán tỉ lệ vị trí chuột so với ảnh gốc
      const ratioX = imgWidth / lensWidth;
      const ratioY = imgHeight / lensHeight;
      const bgX = -x * ratioX;
      const bgY = -y * ratioY;

      // Cập nhật background của khung zoom
      zoomLens.style.backgroundImage = `url(${largeImg})`;
      zoomLens.style.backgroundPosition = `${bgX}px ${bgY}px`;
      zoomLens.style.backgroundSize = `${imgWidth * zoomLevel}px ${imgHeight * zoomLevel}px`;
    }
  });
  // Ẩn khung zoom khi chuột ra khỏi ảnh
  imageContainer.addEventListener('mouseleave', () => {
    zoomLens.style.display = 'none';
  });

  // Hiển thị khung zoom khi chuột vào ảnh
  imageContainer.addEventListener('mouseenter', () => {
    zoomLens.style.display = 'block';
  });
  thumbnails.forEach(thumbnail => {
    thumbnail.addEventListener('click', () => {
      mainImage.src = thumbnail.src;
      mainImage.dataset.largeImg = thumbnail.dataset.largeImg; // Lấy đường dẫn ảnh lớn từ thumbnail
      updateImageSize(); // Cập nhật kích thước ảnh gốc
    });
  });

  const stars = document.querySelectorAll("#starRating span");
const ratingInput = document.getElementById('ratingInput'); // Lấy input hidden
const ratingValue = document.getElementById("ratingValue");

stars.forEach((star, index) => {
  star.addEventListener("click", () => {
    const value = star.dataset.value;
    ratingValue.textContent = value;
    ratingInput.value = value; // Cập nhật input hidden

    // Tối ưu hóa việc thêm/xóa class
    for (let i = 0; i < stars.length; i++) {
      if (i <= index) {
        stars[i].classList.add("selected");
      } else {
        stars[i].classList.remove("selected");
      }
    }
  });

  star.addEventListener("mouseover", () => {
    // Tối ưu hóa việc thêm/xóa class
    for (let i = 0; i < stars.length; i++) {
      if (i <= index) {
        stars[i].classList.add("hovered");
      } else {
        stars[i].classList.remove("hovered");
      }
    }
  });

  star.addEventListener("mouseout", () => {
    for (let i = 0; i < stars.length; i++) {
      stars[i].classList.remove("hovered");
    }
  });
});

  function goBack() {
    window.history.back(); // Quay lại trang trước đó
  }
