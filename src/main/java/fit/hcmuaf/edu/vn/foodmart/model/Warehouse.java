package fit.hcmuaf.edu.vn.foodmart.model;

public class Warehouse {
    private int productId;
    private int quantity;


    public Warehouse() {
    }

    public Warehouse(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter và Setter
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

    @Override
    public String toString() {
        return "Warehouse{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
