package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.*;
import fit.hcmuaf.edu.vn.foodmart.service.ProductService;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.Collections;
import java.util.List;

public class ProductDAO {

    private static Jdbi jdbi = DBConnect.getJdbi();

    // Lấy toàn bộ danh sách sản phẩm từ CSDL
    public List<Products> getAllProducts() {
        String sql = """
        SELECT 
            p.ID AS id, 
            p.ProductName AS productName, 
            p.CategoryID AS categoryId, 
            p.Price AS price, 
            p.ImageURL AS imageUrl,
            p.ShortDescription AS shortDescription,
            p.Weight as weight, 
            c.CategoryName AS categoryName, 
            p.IsSale AS isSale,
            p.DiscountPercentage AS discountPercentage ,
        s.DataSaleSlot AS dataSaleSlot
        FROM products p
        INNER JOIN categories c ON p.CategoryID = c.CategoryID
LEFT JOIN sales s ON p.ID = s.ProductID 
        """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .map((rs, ctx) -> {
                        Category category = new Category();
                        category.setCategoryID(rs.getInt("categoryId"));
                        category.setCategoryName(rs.getString("categoryName"));

                        Products product = new Products();
                        product.setID(rs.getInt("id"));
                        product.setProductName(rs.getString("productName"));
                        product.setCategoryID(rs.getInt("categoryId"));
                        product.setPrice(rs.getDouble("price"));
                        product.setImageURL(rs.getString("imageUrl"));
                        product.setShortDescription(rs.getString("shortDescription"));
                        product.setWeight(rs.getInt("weight"));
                        product.setIsSale(rs.getInt("isSale"));

                        product.setDiscountPercentage(rs.getDouble("discountPercentage")); // Ánh xạ discountPercentage
                        product.setCategory(category);

                        // Ánh xạ thông tin giảm giá (nếu có)

                        if (product.getIsSale()==1) {
                            Sale sale = new Sale();
                            sale.setDataSaleSlot(rs.getString("dataSaleSlot"));
// Gán đối tượng Sale vào sản phẩm
                            product.setSales(sale);

                        }

                        return product;
                    })
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    // Lấy danh sách sản phẩm theo danh mục
    public List<Products> getProductsByCategory(String categoryName) {
        String sql = """
        SELECT p.ID AS id, p.ProductName AS productName, 
               p.CategoryID AS categoryId, p.Price AS price, 
               p.ImageURL AS imageUrl, c.CategoryName AS categoryName
        FROM products p
        INNER JOIN categories c ON p.CategoryID = c.CategoryID
        WHERE c.CategoryName = :categoryName
    """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("categoryName", categoryName) // Gán tham số cho câu truy vấn
                    .map((rs, ctx) -> {
                        Category category = new Category();
                        category.setCategoryID(rs.getInt("categoryId"));
                        category.setCategoryName(rs.getString("categoryName"));

                        Products product = new Products();
                        product.setID(rs.getInt("id"));
                        product.setProductName(rs.getString("productName"));
                        product.setCategoryID(rs.getInt("categoryId"));
                        product.setPrice(rs.getDouble("price"));
                        product.setImageURL(rs.getString("imageUrl"));
                        product.setCategory(category);

                        return product;
                    })
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu theo danh mục: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    // Lấy chi tiết sản phẩm theo ID
    public Products getProductDetailsById(int productId) {
        String productSql = """
        SELECT 
            p.ID AS id,
            p.ProductName AS productName,
            p.CategoryID AS categoryId,
            p.Price AS price,
            p.ImageURL AS imageUrl,
            p.ShortDescription AS shortDescription,
            p.Weight as weight,
            c.CategoryName AS categoryName,
            pd.DetailedDescription AS detailedDescription,
            pd.ProductStatus AS productStatus,
            pd.ExpiryDate AS expiryDate,
            pv.View AS productViews,
            p.DiscountPercentage AS discountPercentage, 
            p.IsSale AS isSale,
            COALESCE(w.Quantity, 0) AS stockQuantity
        FROM 
            products p
        INNER JOIN 
            categories c ON p.CategoryID = c.CategoryID
        LEFT JOIN 
            productsdetail pd ON p.ID = pd.ProductID
        LEFT JOIN 
            products_view pv ON p.ID = pv.ProductID
                    LEFT JOIN
                         warehouse w ON p.ID = w.product_id
        WHERE 
            p.ID = :productId
        """;

        String reviewsSql = """
        SELECT 
            r.Id AS reviewId,
            r.Rating AS rating,
            r.ReviewText AS reviewText,
            r.Created_At AS createdAt,
            u.Username AS username
            
        FROM 
            reviews r
        INNER JOIN 
            users u ON r.UsersID = u.Id
        WHERE 
            r.ProductID = :productId
        """;

        try (Handle handle = jdbi.open()) {
            // Truy vấn thông tin sản phẩm
            Products product = handle.createQuery(productSql)
                    .bind("productId", productId)
                    .map((rs, ctx) -> {
                        // Ánh xạ thông tin danh mục
                        Category category = new Category();
                        category.setCategoryID(rs.getInt("categoryId"));
                        category.setCategoryName(rs.getString("categoryName"));

                        // Ánh xạ thông tin sản phẩm
                        Products prod = new Products();
                        prod.setID(rs.getInt("id"));
                        prod.setProductName(rs.getString("productName"));
                        prod.setCategoryID(rs.getInt("categoryId"));
                        prod.setPrice(rs.getDouble("price"));
                        prod.setImageURL(rs.getString("imageUrl"));
                        prod.setShortDescription(rs.getString("shortDescription"));
                        prod.setWeight(rs.getInt("weight"));
                        prod.setCategory(category);
                        prod.setProductViews(rs.getInt("productViews"));
                        prod.setDiscountPercentage(rs.getDouble("discountPercentage"));
                        prod.setIsSale(rs.getInt("isSale"));
                        // Gán thông tin từ bảng warehouse vào đối tượng Warehouse
                        Warehouse warehouse = new Warehouse();
                        warehouse.setProductId(prod.getID());
                        warehouse.setQuantity(rs.getInt("stockQuantity"));
                        prod.setWarehouse(warehouse);
                        // Ánh xạ thông tin chi tiết sản phẩm
                        ProductsDetail detail = new ProductsDetail();
                        detail.setProductID(prod.getID());
                        detail.setDetailedDescription(rs.getString("detailedDescription"));
                        detail.setProductStatus(rs.getString("productStatus"));
                        detail.setExpiryDate(rs.getDate("expiryDate"));

                        prod.setProductsDetail(detail);

                        return prod;
                    })
                    .first();

            // Truy vấn các đánh giá liên quan đến sản phẩm
            List<Reviews> reviews = handle.createQuery(reviewsSql)
                    .bind("productId", productId)
                    .map((rs, ctx) -> {
                        Reviews review = new Reviews();
                        review.setId(rs.getInt("reviewId"));
                        review.setRating(rs.getInt("rating"));
                        review.setReviewText(rs.getString("reviewText"));
                        review.setCreatedAt(rs.getTimestamp("createdAt"));

                        // Thông tin người dùng đánh giá
                        Users user = new Users();
                        user.setUsername(rs.getString("username"));

                        review.setUser(user); // Gắn thông tin người dùng vào review
                        return review;
                    })
                    .list();

            product.setReviews(reviews); // Gắn danh sách đánh giá vào sản phẩm

            // Truy vấn các ảnh liên quan đến sản phẩm
            String imageSql = "SELECT id, productID, imagePath FROM productimages WHERE productID = :productId";
            List<ProductImages> images = handle.createQuery(imageSql)
                    .bind("productId", productId)
                    .map((rs, ctx) -> new ProductImages(
                            rs.getInt("id"),
                            rs.getInt("productID"),
                            rs.getString("imagePath")
                    ))
                    .list();

            product.setImages(images); // Gắn danh sách ảnh vào sản phẩm

            return product;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy chi tiết sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public double getAverageRating(int productId) {
        String sql = "SELECT AVG(Rating) AS AverageRating FROM reviews WHERE ProductID = :productId";

        try (Handle handle = jdbi.open()) {
            Double averageRating = handle.createQuery(sql)
                    .bind("productId", productId)
                    .mapTo(Double.class)
                    .one();

            return averageRating != null ? averageRating : 0.0;
        } catch (Exception e) {
            System.out.println("Lỗi khi tính trung bình rating: " + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }

    }

    // Lấy danh sách sản phẩm theo danh mục
    public List<Products> getProductsByCategory(int categoryId) {
        String sql = """
            SELECT p.ID AS id, p.ProductName AS productName, 
                   p.CategoryID AS categoryId, p.Price AS price, 
                   p.ImageURL AS imageUrl,p.ShortDescription AS shortDescription,p.Weight as weight, c.CategoryName AS categoryName
            FROM products p
            INNER JOIN categories c ON p.CategoryID = c.CategoryID
            WHERE c.CategoryID = :categoryId
            """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("categoryId", categoryId)
                    .map((rs, ctx) -> {
                        Category category = new Category();
                        category.setCategoryID(rs.getInt("categoryId"));
                        category.setCategoryName(rs.getString("categoryName"));

                        Products product = new Products();
                        product.setID(rs.getInt("id"));
                        product.setProductName(rs.getString("productName"));
                        product.setCategoryID(rs.getInt("categoryId"));
                        product.setPrice(rs.getDouble("price"));
                        product.setImageURL(rs.getString("imageUrl"));
                        product.setShortDescription(rs.getString("shortDescription"));
                        product.setWeight(rs.getInt("weight"));
                        product.setCategory(category);

                        return product;
                    })
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu theo danh mục: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public void addReview(Reviews review) {
        String sql = """
            INSERT INTO Reviews (ProductID, UsersID, Rating, ReviewText, Created_At) 
            VALUES (:productId, :usersId, :rating, :reviewText, NOW())
            """;
        jdbi.useHandle(handle -> handle.createUpdate(sql)
                .bindBean(review)
                .execute());
    }
    public List<Products> getLatestProducts(int limit) {
        String sql = "SELECT p.Id AS ProductID, p.ProductName, p.CategoryID, p.Price, p.UploadDate, " +
                "p.ImageURL, p.ShortDescription, p.Weight " +
                "FROM Products p " +
                "ORDER BY p.UploadDate DESC " + // Sắp xếp theo thời gian tải lên mới nhất
                "LIMIT :limit";  // Lấy số sản phẩm theo tham số limit

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("limit", limit)  // Gán giá trị cho tham số limit
                    .mapToBean(Products.class)  // Ánh xạ kết quả vào đối tượng Products
                    .list();  // Trả về danh sách sản phẩm mới nhất
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();  // Trả về danh sách rỗng thay vì null
        }
    }

    public List<Products> getHotProducts(int limit) {
        String sql = """
    SELECT 
        p.ID AS id, 
        p.ProductName, 
        p.CategoryID, 
        p.Price, 
        p.ImageURL, 
        p.UploadDate, 
        p.ShortDescription, 
        p.Weight, 
        SUM(pv.View) AS TotalViews 
    FROM Products p 
    JOIN Products_View pv 
        ON p.ID = pv.ProductID 
    GROUP BY 
        p.ID, p.ProductName, p.CategoryID, p.Price, 
        p.ImageURL, p.UploadDate, p.ShortDescription, p.Weight 
    ORDER BY TotalViews DESC 
    LIMIT :limit
    """;

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("limit",limit)
                    .mapToBean(Products.class)
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();  // Trả về danh sách rỗng thay vì null
        }
    }
    public Warehouse getProductStock(int productId) {
        String sql = "SELECT ProductID, Quantity FROM warehouse WHERE ProductID = :productId";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("productId", productId)
                    .mapToBean(Warehouse.class)
                    .findOne()
                    .orElse(new Warehouse(productId, 0)); // Mặc định nếu không có thì trả về 0
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin kho: " + e.getMessage());
            e.printStackTrace();
            return new Warehouse(productId, 0);
        }
    }


    //update view_product mỗi khi vào detail
    public boolean updateView(int productID) {
        String sql = """
            UPDATE Products_view
            SET View = View + 1 
            WHERE ProductID = :productID
            """;
        try (Handle handle = jdbi.open()) {
            // Thực thi câu lệnh cập nhật và trả về số dòng bị ảnh hưởng
            int rowsAffected = handle.createUpdate(sql)
                    .bind("productID", productID)
                    .execute();

            // Trả về true nếu ít nhất một dòng bị ảnh hưởng (lượt xem đã được tăng)
            return rowsAffected > 0;
        } catch (Exception e) {
            // In ra thông báo lỗi chi tiết
            System.out.println("Lỗi khi cập nhật lượt xem sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi
        }


    }
    public boolean hasUserPurchasedProduct(int userId, int productId) {
        String sql = """
        SELECT COUNT(*) 
        FROM orderdetails od
        JOIN orders o ON od.OrderID = o.Id
        JOIN shipping s ON o.Id = s.OrderID
        WHERE o.UserID = :userId 
          AND od.ProductID = :productId 
          AND s.ShippingStatus = 'Thành công'
    """;

        try (Handle handle = jdbi.open()) {
            int count = handle.createQuery(sql)
                    .bind("userId", userId)
                    .bind("productId", productId)
                    .mapTo(Integer.class)
                    .one();

            return count > 0;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra đã mua hàng theo ShippingStatus: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Test phương thức getAllProducts() và getProductDetailsById()
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        ProductService productService = new ProductService();
        List<Products> flashSaleProducts = productService.getFlashSaleProducts();
        List<Products> hotProducts = dao.getAllProducts();

        for (Products p : hotProducts) {
            System.out.println(p);
        }
//
//        // Test getAllProducts()
//        List<Products> products = dao.getAllProducts();
//        if (products != null) {
//            for (Products product : products) {
//                System.out.println(product);
//            }
//        } else {
//            System.out.println("Không có sản phẩm hoặc có lỗi khi truy vấn.");
//        }

//        // Test hàm getProductDetailsById
//        int productId = 32; // Thay ID bằng sản phẩm thực tế có trong DB
//        Products productDetails = dao.getProductDetailsById(productId);
//        double averageRating = dao.getAverageRating(productId);
//        if (productDetails != null) {
//            System.out.println("===== Chi tiết sản phẩm =====");
//            System.out.println("ID: " + productDetails.getID());
//            System.out.println("Tên sản phẩm: " + productDetails.getProductName());
//            System.out.println("Giá: " + productDetails.getPrice());
//            System.out.println("Mô tả ngắn: " + productDetails.getShortDescription());
//            System.out.println("Danh mục: " + (productDetails.getCategory() != null
//                    ? productDetails.getCategory().getCategoryName() : "Không có"));
//
//            System.out.println("\n===== Danh sách ảnh =====");
//            if (productDetails.getImages() != null && !productDetails.getImages().isEmpty()) {
//                for (ProductImages image : productDetails.getImages()) {
//                    System.out.println("- " + image.getImagePath());
//                }
//            } else {
//                System.out.println("Không có ảnh nào.");
//            }
//
//            System.out.println("\n===== Đánh giá =====");
//            if (productDetails.getReviews() != null && !productDetails.getReviews().isEmpty()) {
//                for (Reviews review : productDetails.getReviews()) {
//                   System.out.println("- Người dùng: " + review.getUser().getUsername()
//                           + " (Ảnh: " + review.getUser() + ")");
//                    System.out.println("  Xếp hạng: " + review.getRating());
//                    System.out.println("  Nội dung: " + review.getReviewText());
//                }
//            } else {
//                System.out.println("Không có đánh giá nào.");
//            }
//            System.out.println("\n===== Trung bình Rating =====");
//            System.out.println("Trung bình rating: " + averageRating);
//
//            System.out.println("\n===== Lượt xem =====");
//            System.out.println("Số lượt xem: " + productDetails.getProductViews());
//            System.out.println("\n===== Giảm giá =====");
//            if (productDetails.isSale()) {
//                System.out.println("Sản phẩm đang giảm giá!");
//                Sale saleInfo = productDetails.getSales(); // Giả sử bạn có phương thức getSaleInfo() trong lớp Products
//                System.out.println("Phần trăm giảm giá: " + saleInfo.getDiscountPercentage());
//                System.out.println("Khung giờ giảm giá: " + saleInfo.getDataSaleSlot());
//                // ... (In thêm thông tin giảm giá nếu cần) ...
//            } else {
//                System.out.println("Sản phẩm không giảm giá.");
//            }
//        } else {
//            System.out.println("Không tìm thấy chi tiết sản phẩm hoặc có lỗi.");
//        }

}
}

