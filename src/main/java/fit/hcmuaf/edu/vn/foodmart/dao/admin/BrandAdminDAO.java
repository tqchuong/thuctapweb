package fit.hcmuaf.edu.vn.foodmart.dao.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Brands;

import fit.hcmuaf.edu.vn.foodmart.model.Products;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

 public class BrandAdminDAO {

    private static Jdbi jdbi;

    static {
        jdbi = DBConnect.getJdbi(); // Kết nối với DB thông qua DBConnect
    }

    // 1. Thêm brand mới
    public boolean addBrand(Brands brand) {
        String sql = "INSERT INTO brands (name) VALUES (?)";
        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, brand.getName())  // Gán giá trị cho cột name
                    .execute();
            return rowsAffected > 0; // Nếu có ảnh hưởng dòng, trả về true
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }

    // 2. Cập nhật thông tin brand
    public boolean updateBrand(Brands brand) {
        String sql = "UPDATE brands SET name = ? WHERE id = ?";
        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, brand.getName())  // Gán giá trị mới cho cột name
                    .bind(1, brand.getId())    // Gán giá trị cho cột id
                    .execute();
            return rowsAffected > 0; // Nếu có ảnh hưởng dòng, trả về true
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu có lỗi xảy ra, trả về false
        }
    }

     public List<Brands> getAllBrands() {
         String sql = "SELECT * FROM brands";
         try (Handle handle = jdbi.open()) {
             return handle.createQuery(sql)
                     .mapToBean(Brands.class)
                     .list();
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

     public List<Products> getProductsByBrand(String brandName) {
         String sql = """
        SELECT p.*, b.id AS brand_id, b.name AS brand_name
        FROM brands b
        JOIN products p ON b.id = p.BrandID
        WHERE b.name = :brandName
    """;

         return jdbi.withHandle(handle ->
                 handle.createQuery(sql)
                         .bind("brandName", brandName)
                         .map((rs, ctx) -> {
                             Products product = new Products();
                             product.setID(rs.getInt("Id"));
                             product.setProductName(rs.getString("ProductName"));
                             product.setCategoryID(rs.getInt("CategoryID"));
                             product.setPrice(rs.getDouble("Price"));
                             product.setImageURL(rs.getString("ImageURL"));
                             product.setShortDescription(rs.getString("ShortDescription"));
                             product.setDiscountPercentage(rs.getDouble("DiscountPercentage"));
                             // Gán đối tượng Brands
                             Brands brand = new Brands();
                             brand.setId(rs.getInt("brand_id"));
                             brand.setName(rs.getString("brand_name"));
                             product.setBrands(brand);

                             return product;
                         })
                         .list()
         );
     }




        public static void main(String[] args) {
            BrandAdminDAO dao = new BrandAdminDAO();

            // Lấy tất cả các brand
            System.out.println("====== LẤY TẤT CẢ CÁC BRAND ======");
            List<Brands> brands = dao.getAllBrands();

                brands.forEach(System.out::println);


    }
}
