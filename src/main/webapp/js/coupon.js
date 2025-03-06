document.addEventListener('DOMContentLoaded', function () {
    const sliderImages = document.querySelector('.slider-images');
    const images = document.querySelectorAll('.slider-image');
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');

    let currentIndex = 0;

    // Hàm để chuyển sang hình ảnh tiếp theo
    function goToNextSlide() {
        currentIndex = (currentIndex + 1) % images.length;
        updateSlider();
    }

    // Hàm để quay lại hình ảnh trước
    function goToPrevSlide() {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        updateSlider();
    }

    // Cập nhật vị trí slider
    function updateSlider() {
        sliderImages.style.transform = `translateX(-${currentIndex * 100}%)`;
    }

    // Xử lý sự kiện khi click vào nút Next hoặc Prev
    nextButton.addEventListener('click', goToNextSlide);
    prevButton.addEventListener('click', goToPrevSlide);

    // Tự động chuyển đổi hình ảnh sau mỗi 5 giây (optional)
    setInterval(goToNextSlide, 5000);
    const voucherContainers = document.querySelectorAll('.coupon-card');

    voucherContainers.forEach(container => {
        const cpnBtn = container.querySelector("#cpnBtn");
        const cpnCode = container.querySelector("#cpnCode");

        cpnBtn.onclick = function() {
            navigator.clipboard.writeText(cpnCode.innerHTML);
            cpnBtn.innerHTML = "ĐÃ LƯU";
            setTimeout(function() {
                cpnBtn.innerHTML = "LƯU MÃ";
            }, 2000);
        };
    });
});
