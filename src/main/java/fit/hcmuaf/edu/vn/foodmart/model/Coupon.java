package fit.hcmuaf.edu.vn.foodmart.model;

import java.sql.Timestamp;

public class Coupon {
    private int id;
    private String couponCode;
    private double discountAmount;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private double minOrderAmount;
    private int orderId;

    // Các thuộc tính mới
    private String discountType; // "Percentage" hoặc "FixedAmount"
    private Integer maxUsage;        // Tổng số lần tối đa sử dụng mã (null nếu không giới hạn)
    private int usedCount;           // Số lần đã sử dụng mã
    private Integer maxUsagePerUser; // Giới hạn số lần mỗi người dùng được sử dụng
    private Double maxDiscountAmount; // Số tiền giảm tối đa (áp dụng khi DiscountType = Percentage)
    private String status;       // "Active" or "Inactive"

    public Coupon() {
    }

    public Coupon(int id, String couponCode, double discountAmount, String description, Timestamp startDate, Timestamp endDate, double minOrderAmount, int orderId, String discountType, Integer maxUsage, int usedCount, Integer maxUsagePerUser, Double maxDiscountAmount, String status) {
        this.id = id;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minOrderAmount = minOrderAmount;
        this.orderId = orderId;
        this.discountType = discountType;
        this.maxUsage = maxUsage;
        this.usedCount = usedCount;
        this.maxUsagePerUser = maxUsagePerUser;
        this.maxDiscountAmount = maxDiscountAmount;
        this.status = status;
    }

    public void setMaxUsage(Integer maxUsage) {
        this.maxUsage = maxUsage;
    }

    public Integer getMaxUsagePerUser() {
        return maxUsagePerUser;
    }

    public void setMaxUsagePerUser(Integer maxUsagePerUser) {
        this.maxUsagePerUser = maxUsagePerUser;
    }

    public Double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(Double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Các getter và setter cho các thuộc tính mới
    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponCode='" + couponCode + '\'' +
                ", discountAmount=" + discountAmount +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", minOrderAmount=" + minOrderAmount +
                ", discountType='" + discountType + '\'' +
                ", maxUsage=" + maxUsage +
                ", usedCount=" + usedCount +
                ", maxUsagePerUser=" + maxUsagePerUser +
                ", maxDiscountAmount=" + maxDiscountAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
