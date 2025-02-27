package fit.hcmuaf.edu.vn.foodmart.model;

import java.util.Date;

public class ProductsDetail {
    private int id;
    private int productID;
    private String detailedDescription; // Mô tả chi tiết
    private String productStatus;       // Trạng thái sản phẩm: "Còn hàng" hoặc "Hết hàng"
    private Date expiryDate;            // Ngày hết hạn

    // Constructor mặc định
    public ProductsDetail() {}

    // Constructor đầy đủ
    public ProductsDetail(int id, int productID, String detailedDescription, String productStatus, Date expiryDate) {
        this.id = id;
        this.productID = productID;
        this.detailedDescription = detailedDescription;
        this.productStatus = productStatus;
        this.expiryDate = expiryDate;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "ProductsDetail{" +
                "id=" + id +
                ", productID=" + productID +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", productStatus='" + productStatus + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
