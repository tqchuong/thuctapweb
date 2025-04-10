package fit.hcmuaf.edu.vn.foodmart.dao.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.model.Category;
import fit.hcmuaf.edu.vn.foodmart.model.Warehouse;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ProductAdminDAO {
    private static Jdbi jdbi;

    static {
        jdbi = DBConnect.getJdbi();
    }

    // 1. Lấy tất cả sản phẩm (kèm CategoryName)
    public List<Products> getAllProducts() {
        String sql = "SELECT p.*, c.CategoryName FROM Products p " +
                "JOIN Categories c ON p.CategoryID = c.CategoryID";
        List<Products> products = jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .map((rs, ctx) -> {
                            Products product = new Products();
                            product.setID(rs.getInt("Id"));
                            product.setProductName(rs.getString("ProductName"));
                            product.setCategoryID(rs.getInt("CategoryID"));
                            product.setPrice(rs.getDouble("Price"));
                            product.setImageURL(rs.getString("ImageURL"));
                            product.setShortDescription(rs.getString("ShortDescription"));
                            product.setWeight(rs.getInt("Weight"));

                            // Tạo đối tượng Category và gán categoryName
                            Category category = new Category();
                            category.setCategoryID(rs.getInt("CategoryID"));
                            category.setCategoryName(rs.getString("CategoryName"));
                            product.setCategory(category);

                            return product;
                        })
                        .list()
        );

        // Trả về danh sách sản phẩm
        return products;
    }


    // 2. Thêm sản phẩm mới
    public boolean addProduct(Products product) {
        String insertProductSql = "INSERT INTO Products " +
                "(ProductName, CategoryID, IsSale, DiscountPercentage, Price, ImageURL, ShortDescription, Weight) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String insertWarehouseSql = "INSERT INTO warehouse (product_id, quantity, import_date) " +
                "VALUES (?, ?, CURDATE())";

        try (Handle handle = jdbi.open()) {

            handle.begin();

            int productId = handle.createUpdate(insertProductSql)

                    .bind(0, product.getProductName())
                    .bind(1, product.getCategoryID())
                    .bind(2, product.getIsSale())
                    .bind(3, product.getDiscountPercentage())
                    .bind(4, product.getPrice())
                    .bind(5, product.getImageURL())
                    .bind(6, product.getShortDescription())
                    .bind(7, product.getWeight())

                    .executeAndReturnGeneratedKeys("Id")
                    .mapTo(int.class)
                    .one();

            int quantity = (product.getWarehouse() != null) ? product.getWarehouse().getQuantity() : 0;

            handle.createUpdate(insertWarehouseSql)
                    .bind(0, productId)
                    .bind(1, quantity)

                    .execute();

            handle.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addToWarehouse(Warehouse warehouse) {
        String sql = "INSERT INTO warehouse (product_id, quantity, import_date) VALUES (?, ?, CURDATE())";
        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind(0, warehouse.getProductId())
                    .bind(1, warehouse.getQuantity())
                    .execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }







    // 3. Cập nhật sản phẩm
    public boolean updateProduct(Products product) {
        if (product.getCategoryID() <= 0) {
            System.out.println("Lỗi: CategoryID không hợp lệ.");
            return false;
        }

        String updateProductSql = "UPDATE Products SET ProductName = ?, CategoryID = ?, IsSale = ?, " +
                "DiscountPercentage = ?, Price = ?, ImageURL = ?, ShortDescription = ?, Weight = ? " +
                "WHERE Id = ?";

        String updateWarehouseSql = "UPDATE warehouse SET quantity = ?, import_date = CURDATE() WHERE product_id = ?";


        try (Handle handle = jdbi.open()) {
            handle.begin();

            // 1. Cập nhật thông tin sản phẩm
            handle.createUpdate(updateProductSql)
                    .bind(0, product.getProductName())
                    .bind(1, product.getCategoryID())
                    .bind(2, product.getIsSale())
                    .bind(3, product.getDiscountPercentage())
                    .bind(4, product.getPrice())
                    .bind(5, product.getImageURL())
                    .bind(6, product.getShortDescription())
                    .bind(7, product.getWeight())
                    .bind(8, product.getID())
                    .execute();

            // 2. Nếu có thông tin warehouse và quantity > 0 → xử lý tồn kho
            if (product.getWarehouse() != null && product.getWarehouse().getQuantity() > 0) {
                int rowsAffected = handle.createUpdate(updateWarehouseSql)
                        .bind(0, product.getWarehouse().getQuantity())
                        .bind(1, product.getID())
                        .execute();


            }

            handle.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




    // 4. Xóa sản phẩm
    public boolean deleteProductById(int productId) {
        String sql = "DELETE FROM products WHERE Id = :productId";
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("productId", productId)
                .execute() > 0);
    }


    // 5. Lấy thông tin sản phẩm theo ID
    public Products getProductById(int id) {
        String sql = "SELECT p.Id AS id, p.ProductName AS productName, " +
                "p.CategoryID AS categoryId, p.Price AS price, " +
                "p.ImageURL AS imageUrl, c.CategoryName AS categoryName, " +
                "p.Weight AS Weight, p.ShortDescription AS shortDescription " +
                "FROM products p " +
                "LEFT JOIN categories c ON p.CategoryID = c.CategoryID " +
                "WHERE p.Id = ?";

        try (Handle handle = jdbi.open()) {
            System.out.println("Thực thi truy vấn với ID: " + id);

            // Sử dụng ánh xạ thủ công để đảm bảo không lỗi
            Products product = handle.createQuery(sql)
                    .bind(0, id)
                    .map((rs, ctx) -> {
                        Products p = new Products();
                        p.setID(rs.getInt("id"));
                        p.setProductName(rs.getString("productName"));
                        p.setCategoryID(rs.getInt("categoryId"));
                        p.setPrice(rs.getDouble("price"));
                        p.setImageURL(rs.getString("imageUrl"));
                        p.setShortDescription(rs.getString("shortDescription"));

                        p.setWeight(rs.getInt("Weight"));

                        // Kiểm tra và set categoryName nếu tồn tại
                        String categoryName = rs.getString("categoryName");
                        if (categoryName != null) {
                            Category category = new Category();
                            category.setCategoryName(categoryName);
                            p.setCategory(category);
                        }
                        return p;
                    })
                    .findOne()
                    .orElse(null);

            // Debug thông tin sản phẩm trả về
            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm với ID: " + id);
            } else {
                System.out.println("Sản phẩm lấy được: " + product.toString());
            }

            return product;
        } catch (Exception e) {
            System.out.println("Lỗi khi thực thi truy vấn với ID: " + id);
            e.printStackTrace();
            return null;
        }
    }

    // 6. Đếm số lượng producs
    public int getProductCount() {
        String sql = "SELECT COUNT(*) FROM products";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }


    public int getSumRevenue(){
        String sql = "SELECT SUM(TotalAmount) FROM Orders where OrderStatus = 'Đã xử lý'";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }


    public static void main(String[] args) {

        ProductAdminDAO dao = new ProductAdminDAO();

        System.out.println("====== LẤY TẤT CẢ NGƯỜI DÙNG ======");
        List<Products> product = dao.getAllProducts();
        product.forEach(System.out::println);
    }
}
