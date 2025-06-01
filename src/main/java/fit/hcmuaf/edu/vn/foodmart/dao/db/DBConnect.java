package fit.hcmuaf.edu.vn.foodmart.dao.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.Handle;
import fit.hcmuaf.edu.vn.foodmart.model.Products;
import fit.hcmuaf.edu.vn.foodmart.model.Users;

import java.util.List;

public class DBConnect {

    private static Jdbi jdbi;

    static {
        try {
            // Cấu hình thông tin kết nối MySQL trực tiếp trong lớp DBConnect

            String host = "caboose.proxy.rlwy.net";  // Địa chỉ host MySQL Railway
            int port = 45525;                        // Cổng MySQL Railway
            String dbname = "railway";               // Tên database Railway
            String username = "root";                // Username Railway
            String password = "fjPupexAzPvctaolQiQgfQNTCasoXtQO";  // Password Railway
            String options = "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            // Tạo URL kết nối
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?" + options;
            Class.forName("com.mysql.cj.jdbc.Driver");  // Nạp driver MySQL
            jdbi = Jdbi.create(url, username, password);
            System.out.println("Kết nối đến CSDL Railway thành công!");

        } catch (Exception e) {
            System.err.println("Không thể kết nối đến CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Trả về đối tượng Jdbi
    public static Jdbi getJdbi() {
        return jdbi;
    }

    // Lấy thông tin sản phẩm và hiển thị ra màn hình
    public static void displayProducts() {
        String sql = "SELECT * FROM products";  // Truy vấn toàn bộ bảng Products
//
        try (Handle handle = jdbi.open()) {
            // Truy vấn dữ liệu và ánh xạ kết quả thành đối tượng Products
            List<Products> products = handle.select(sql)
                    .mapToBean(Products.class)
                    .list();  // Lấy danh sách các sản phẩm

            // In danh sách sản phẩm ra màn hình
            if (products != null) {
                for (Products product : products) {
                    System.out.println(product);  // In thông tin sản phẩm
                }
            } else {
                System.out.println("Không có sản phẩm nào.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy thông tin người dùng và hiển thị ra màn hình
    public static void displayUsers() {
        String sql = "SELECT * FROM users";  // Truy vấn toàn bộ bảng Users

        try (Handle handle = jdbi.open()) {
            // Truy vấn dữ liệu và ánh xạ kết quả thành đối tượng Users
            List<Users> users = handle.select(sql)
                    .mapToBean(Users.class)
                    .list();  // Lấy danh sách các người dùng

            // In danh sách người dùng ra màn hình
            if (users != null) {
                for (Users user : users) {
                    System.out.println(user);  // In thông tin người dùng
                }
            } else {
                System.out.println("Không có người dùng nào.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Hiển thị các sản phẩm và người dùng khi chạy chương trình
        displayProducts();

    }
}