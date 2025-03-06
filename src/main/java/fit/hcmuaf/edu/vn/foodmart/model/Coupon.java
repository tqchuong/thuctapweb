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

    public Coupon() {
    }

    public Coupon(int id, String couponCode, double discountAmount, String description, Timestamp startDate, Timestamp endDate, double minOrderAmount, int orderId) {
        this.id = id;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minOrderAmount = minOrderAmount;
        this.orderId = orderId;
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
                '}';
    }
}