package fit.hcmuaf.edu.vn.foodmart.Cart;

import fit.hcmuaf.edu.vn.foodmart.model.ProductImages;

import java.util.Date;
import java.util.List;

public class CartProduct {
        private int id;              // ID của giỏ hàng
        private int userId;          // ID của người dùng
        private int productId;       // ID của sản phẩm
        private int quantity;        // Số lượng sản phẩm
        private double price;    // Giá sản phẩm
        private Date createdAt; // Thời gian tạo
        private Date updatedAt; // Thời gian cập nhật
    private String productName;
    private String imageURL;



        // Constructor không tham số
        public CartProduct() {}
        // Constructor có tham số
        public CartProduct(int id, int userId, int productId, int quantity, double price, Date createdAt, Date updatedAt, String productName, String imageURL) {
            this.id = id;
            this.userId = userId;
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;

            this.productName = productName;
            this.imageURL = imageURL;

        }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter và Setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }

        @Override
        public String toString() {
            return "ShoppingCart{" +
                    "id=" + id +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ",imageURL='" + imageURL + '\'' +
                    '}';
        }


}


