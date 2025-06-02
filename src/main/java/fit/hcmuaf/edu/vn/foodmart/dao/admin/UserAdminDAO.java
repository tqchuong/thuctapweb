package fit.hcmuaf.edu.vn.foodmart.dao.admin;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class UserAdminDAO {

    private static Jdbi jdbi;

    static {
        jdbi = DBConnect.getJdbi(); // Kết nối với DB thông qua DBConnect
    }

    // 1. Lấy tất cả người dùng
    public List<Users> getAllUsers() {
        String sql = "SELECT * FROM users" +
                " WHERE isDelete = FALSE";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapToBean(Users.class)
                    .list(); // Trả về danh sách tất cả user
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. Thêm người dùng mới
    public boolean addUser(Users user) {
        if (userExists(user.getUsername())) {
            System.out.println("Username đã tồn tại: " + user.getUsername());
            return false; // Trả về false nếu username đã tồn tại
        }

        String sql = "INSERT INTO users (Username, Email, Phone, Password, Role, UserStatus ) VALUES (?, ?, ?, ?, ?, ?)";
        try (Handle handle = jdbi.open()) {
            System.out.println("SQL: INSERT INTO users (username, mail, phone, password, role, user_status)");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Username: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Password: " + user.getPassword());
            System.out.println("Role: " + user.getRole());
            System.out.println("User Status: " + user.getUserStatus());
            handle.createUpdate(sql)
                    .bind(0, user.getUsername())   // Tên đầy đủ
                    .bind(1, user.getEmail())   // Tên đầy đủ
                    .bind(2, user.getPhone())      // Số điện thoại
                    .bind(3, user.getPassword())   // Mật khẩu
                    .bind(4, "User")               // Vai trò mặc định là 'User'
                    .bind(5, "Đang hoạt động")     // Trạng thái mặc định là 'Đang hoạt động'
                    .execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE users SET phone = ?, password = ?, Role =?, UserStatus = ? WHERE id = ?";
        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, user.getPhone())
                    .bind(1, user.getPassword())
                    .bind(2, user.getRole())
                    .bind(3, user.getUserStatus())
                    .bind(4, user.getId())
                    .execute();
            return rowsAffected > 0; // Kiểm tra số dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 4. Xóa người dùng
    public boolean deleteUserById(int userId) {
        String sql = "UPDATE users SET isDelete = TRUE WHERE Id = :userId AND isDelete = FALSE";
        return jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("userId", userId)
                .execute() > 0);
    }



    // 5. Tìm kiếm người dùng theo username
    public Users getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, username)
                    .mapToBean(Users.class)
                    .findOne()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 6. Tìm kiếm tất cả người dùng theo role
    public List<Users> getUsersByRole(String role) {
        String sql = "SELECT * FROM users WHERE role=?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, role)
                    .mapToBean(Users.class)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 7. Kiểm tra sự tồn tại của người dùng
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE Username = :username AND isDelete = FALSE";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("username", username)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0) > 0
        );
    }


    // 8. Tìm kiếm người dùng theo id
    public Users getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, id) // Gắn giá trị id vào câu lệnh SQL
                    .mapToBean(Users.class) // Chuyển đổi kết quả thành đối tượng Users
                    .findOne()
                    .orElse(null); // Trả về null nếu không tìm thấy
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 9. Đếm số lượng user
    public int getUserCount() {
        String sql = "SELECT COUNT(*) FROM users" +
                " Where isDelete = 0";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .mapTo(Integer.class)
                    .findOnly();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu xảy ra lỗi
        }
    }


    // Phương thức main để kiểm thử
    public static void main(String[] args) {
        UserAdminDAO dao = new UserAdminDAO();

        System.out.println("====== LẤY TẤT CẢ NGƯỜI DÙNG ======");
        List<Users> users = dao.getAllUsers();
        users.forEach(System.out::println);

//        System.out.println("\n====== THÊM NGƯỜI DÙNG ======");
//        Users newUser = new Users();
//        newUser.setFullName("Nguyễn Văn A");
//        newUser.setPhone("0987654321");
//        newUser.setPassword("123456");
//        newUser.setUserStatus("Đang hoạt động");
//        System.out.println("Thêm người dùng thành công: " + dao.addUser(newUser));
//
//        System.out.println("\n====== CẬP NHẬT NGƯỜI DÙNG ======");
//        newUser.setUserId(1); // ID của người dùng cần cập nhật
//        newUser.setFullName("Nguyễn Văn B");
//        newUser.setPhone("0123456789");
//        newUser.setPassword("654321");
//        newUser.setUserStatus("Bị khóa");
//        System.out.println("Cập nhật người dùng thành công: " + dao.updateUser(newUser));
//
//        System.out.println("\n====== XÓA NGƯỜI DÙNG ======");
//        System.out.println("Xóa người dùng thành công: " + dao.deleteUser(1));
    }
}
