# 🍱 FoodMart Web App (Java - Servlet/JSP)

## 🧱 Công nghệ sử dụng
- Ngôn ngữ: Java (Servlets & JSP)
- Cấu trúc: Maven Project
- Database: MySQL
- Công cụ build: Apache Maven
- IDE: IntelliJ IDEA / Eclipse

## 🗂️ Cấu trúc project
src/ └── main/ ├── java/ │ └── fit/hcmuaf/edu/vn/foodmart/ │ ├── controller/ # Servlet điều khiển luồng request │ ├── dao/ # Tầng truy cập dữ liệu │ ├── model/ # Các class đại diện dữ liệu (Product, User, ...) │ ├── service/ # Xử lý logic ứng dụng │ └── utils/ # Tiện ích (DB connection, validate, ...) ├── resources/ # (Nếu có) file config, tài nguyên └── webapp/ ├── views/ # Các trang JSP (giao diện) ├── assets/ # CSS, JS, hình ảnh └── WEB-INF/ └── web.xml # Cấu hình Servlet


## ⚙️ Chức năng chính
- Đăng nhập / Đăng ký người dùng
- Hiển thị sản phẩm
- Thêm vào giỏ hàng
- Đặt hàng, thanh toán
- Quản lý đơn hàng (Admin)

---

### 🧭 Mô hình kiến trúc: MVC (Model - View - Controller)

- `Controller` nhận request từ người dùng
- `Service` xử lý nghiệp vụ
- `DAO` tương tác với DB
- `Model` chứa dữ liệu
- `View (JSP)` hiển thị kết quả

---

## 📦 Cách chạy dự án
1. Import vào IDE như Maven Project
2. Cấu hình DB trong file `DBContext.java`
3. Chạy trên server: Apache Tomcat
