package fit.hcmuaf.edu.vn.foodmart.model;

public class ProductImages {
    private int id;
    private int productID;
    private String imagePath;
public ProductImages() {}
    public ProductImages(int id, int productID, String imagePath) {
        this.id = id;
        this.productID = productID;
        this.imagePath = imagePath;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public int getProductID() {
        return productID;
    }

    public String getImagePath() {
        return imagePath;
    }
    @Override
    public String toString() {
        return this.imagePath;  // Hoặc có thể thay đổi tùy theo yêu cầu của bạn
    }
}
