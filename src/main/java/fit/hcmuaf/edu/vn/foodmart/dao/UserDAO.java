package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Users;
import fit.hcmuaf.edu.vn.foodmart.utils.PasswordUtils;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class UserDAO implements ObjectDAO {

    private static Jdbi jdbi;
    public static Map<String, Users> userList ;

    static {
        // Khởi tạo đối tượng Jdbi từ DBConnect
        jdbi = DBConnect.getJdbi();
        userList = loadData();
    }

    // Lấy danh sách người dùng từ cơ sở dữ liệu và lưu vào bộ nhớ
    private static Map<String, Users> loadData() {
        Map<String, Users> userListTemp = new HashMap<>();
        String sql = "SELECT * FROM users";

        try (Handle handle = jdbi.open()) {
            // Sử dụng JDBI để thực hiện truy vấn và ánh xạ kết quả vào danh sách người dùng
            handle.select(sql)
                    .mapToBean(Users.class)  // Ánh xạ kết quả vào đối tượng Users
                    .forEach(user -> {
                        // Đảm bảo rằng tất cả các người dùng được thêm vào Map, kể cả những người đã bị đánh dấu xóa
                        userListTemp.put(user.getUsername(), user);
                    });  // Thêm vào Map với key là username
            
            System.out.println("Đã nạp " + userListTemp.size() + " người dùng vào bộ nhớ");
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }

        return userListTemp;
    }

    //kiểm tra đăng nhập
    public String checkLogin(String username, String password) {
        // Truy vấn chỉ để lấy thông tin người dùng dựa trên username
        String sql = "SELECT * FROM users WHERE username = :username";

        try (Handle handle = jdbi.open()) {
            // Lấy thông tin người dùng
            Users user = handle.createQuery(sql)
                    .bind("username", username) // Gán giá trị tham số cho SQL
                    .mapToBean(Users.class) // Ánh xạ kết quả sang đối tượng Users
                    .findOne() // Lấy một kết quả
                    .orElse(null); // Nếu không tìm thấy, trả về null

            if (user == null) {
                System.out.println("Người dùng không tồn tại: " + username);
                return "INCORRECT_PASSWORD";
            }

            // Lấy thời gian hiện tại theo múi giờ Việt Nam
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (user.getLockTime() != null && currentTime.before(getLockTimeByUser(username))) {
                System.out.println("Tài khoản bị khóa tạm thời: " + username);
                return "ACCOUNT_TEMP_LOCKED";
            }
            if (user.getIsDelete()){
                System.out.println("Tài khoản không thể đăng nhập do đã bị xóa ");
                return "ACCOUNT_DELETED";
            }

            // Kiểm tra trạng thái người dùng
            if (!user.getUserStatus().equals("Đang hoạt động")) {
                System.out.println("Tài khoản không thể đăng nhập do trạng thái: " + user.getUserStatus());
                return "ACCOUNT_LOCKED"; // Tài khoản bị khóa
            }

            if (!isVerified(getEmailByUsername(username))) {
                System.out.println("Tài khoản chưa được xác thực: " + username);
                return "VERIFY_ACCOUNT"; // Chuyển hướng đến trang xác thực
            }

            // Kiểm tra mật khẩu
            if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                System.out.println("Đăng nhập thành công: " + username);
                updateLoginAttempts(handle,username,0,null);
                return "LOGIN_SUCCESS";
            } else {
                // Sai mật khẩu, tăng loginAttempts
                int attempts = user.getLoginAttempts() + 1;
                Timestamp lockTime = null;
                if (attempts >= 5) {
                    lockTime = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000); // Khóa 5 phút
                    System.out.println(lockTime);
                    System.out.println("Khóa tài khoản tạm thời do sai quá 5 lần: " + username);
                }
                updateLoginAttempts(handle, username, attempts,lockTime);
                System.out.println("Sai mật khẩu cho người dùng: " + username + " (Lần " + attempts + ")");
                return attempts >= 5 ? "ACCOUNT_TEMP_LOCKED" : "INCORRECT_PASSWORD";
            }


        } catch (Exception e) {
            // Xử lý lỗi kết nối hoặc truy vấn
            System.out.println("Lỗi khi kiểm tra đăng nhập: " + e.getMessage());
            e.printStackTrace();
            return "ERROR"; // Trả về false nếu có lỗi
        }
    }

    // Phương thức hỗ trợ cập nhật loginAttempts và lockTime
    private void updateLoginAttempts(Handle handle, String username, int attempts, Timestamp lockTime) {
        String updateSql = "UPDATE users SET loginAttempts = :attempts, lockTime = :lockTime WHERE username = :username";
        try {
            handle.createUpdate(updateSql)
                    .bind("attempts", attempts)
                    .bind("lockTime", lockTime)
                    .bind("username", username)
                    .execute();
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật login attempts hoặc lock time cho " + username + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể cập nhật thông tin đăng nhập", e);
        }
    }

    public Timestamp getLockTimeByUser(String username) {
        String sql = "SELECT lockTime FROM users WHERE username = :username";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("username", username)
                    .mapTo(Timestamp.class)
                    .findOne()
                    .orElse(null);
        }
    }

    // Lấy thông tin người dùng theo tên người dùng
    public Users getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Handle handle = jdbi.open()) {
            Users user = handle.createQuery(sql)
                    .bind(0, username)
                    .mapToBean(Users.class)
                    .findOne().orElse(null);  // Trả về đối tượng người dùng nếu tìm thấy
            
            if (user == null) {
                System.out.println("Không tìm thấy user với username: " + username);
            }
            
            return user;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    // Lấy thông tin người dùng theo id
    public Users getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind(0, id)
                    .mapToBean(Users.class)
                    .findOne().orElse(null);  // Trả về đối tượng người dùng nếu tìm thấy
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }



    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Handle handle = jdbi.open()) {
            int count = handle.createQuery(sql)
                    .bind(0, email)
                    .mapTo(Integer.class)
                    .one();
            return count > 0;
        }
    }


    @Override
    public boolean add(Object obj) {
        Users user = (Users) obj;

        // Thêm vào danh sách trong bộ nhớ
        userList.put(user.getUsername(), user);

        // Câu lệnh SQL để thêm người dùng vào cơ sở dữ liệu
        String sql = "INSERT INTO users (Username, Password, Email, Phone, verification_token, token_expiry, is_verified) " +
                "VALUES (?, ?, ?, ?, ?, ?, FALSE)";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind(0, user.getUsername())
                    .bind(1, user.getPassword())
                    .bind(2, user.getEmail())
                    .bind(3, user.getPhone())
                    .bind(4, user.getVerification_token())
                    .bind(5, user.getToken_expiry())
                    .execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean del(String id) {
        if (!userList.containsKey(id)) {
            System.out.println("Không tìm thấy user với id: " + id);
            return false;
        }
        
        // Thay vì xóa hoàn toàn, cập nhật trạng thái isDelete = TRUE
        String sql = "UPDATE users SET isDelete = TRUE WHERE username = ?";
        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, id)
                    .execute();
                    
            if (rowsAffected > 0) {
                // Cập nhật trạng thái trong bộ nhớ
                Users user = userList.get(id);
                user.setIsDelete(true);
                userList.put(id, user);
                
                System.out.println("Đánh dấu xóa thành công user với id: " + id);
                return true;
            } else {
                System.out.println("Không đánh dấu xóa được user với id: " + id);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(String id, Object obj) {
        Users user = (Users) obj;
        userList.replace(id, (Users) obj);
        String sql ="UPDATE users SET password = ? WHERE username = ?";
        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind(0, user.getPassword())
                    .bind(1, user.getUsername())
                    .execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //thay đổi mật khẩu
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // Lấy thông tin user từ userList
        Users user = userList.get(username);

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword);
        userList.replace(user.getUsername(), user);

        // Cập nhật vào cơ sở dữ liệu
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, newPassword)  // Gắn mật khẩu mới
                    .bind(1, username)     // Gắn tên người dùng
                    .execute();

            if (rowsAffected > 0) {
                System.out.println("Đổi mật khẩu thành công cho user: " + username);
                return true;
            } else {
                System.out.println("Không đổi được mật khẩu trong cơ sở dữ liệu cho user: " + username);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để làm mới danh sách người dùng từ cơ sở dữ liệu
    public static void refreshUserList() {
        userList = loadData();
    }

    //thay đổi thông tin người dùng
    public boolean changeInfor(String username, String fullName, String phone, String email, String address) {
        // Làm mới danh sách người dùng trước khi thực hiện thao tác
        refreshUserList();
        
        Users user = userList.get(username);

        // Kiểm tra nếu user là null trong userList, thử lấy từ database trực tiếp
        if (user == null) {
            System.out.println("Không tìm thấy user trong userList, thử lấy từ database: " + username);
            user = getUserByUsername(username);
            // Nếu vẫn không tìm thấy user sau khi truy vấn database
            if (user == null) {
                System.out.println("Không tìm thấy user trong database: " + username);
                return false;
            }
            // Nếu tìm thấy, cập nhật lại userList
            userList.put(username, user);
        }

        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);

        userList.replace(user.getUsername(), user);

        // Cập nhật vào cơ sở dữ liệu
        StringBuilder sql = new StringBuilder("UPDATE users ");
        sql.append("SET fullName = ?, phone = ?, email = ?, address = ?");
        sql.append(" WHERE username = ?");

        try (Handle handle = jdbi.open()) {
            int rowsAffected = handle.createUpdate(sql)
                    .bind(0, fullName)
                    .bind(1, phone)
                    .bind(2, email)
                    .bind(3, address)
                    .bind(4, username)
                    .execute();

            if (rowsAffected > 0) {
                System.out.println("Đổi thông tin thành công cho user: " + username);
                return true;
            } else {
                System.out.println("Không đổi được thông tin trong cơ sở dữ liệu cho user: " + username);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //kiểm tra username có tồn tại không
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND isDelete = FALSE";
        try (Handle handle = jdbi.open()) {
            int count = handle.createQuery(sql)
                    .bind(0, username)
                    .mapTo(int.class)
                    .first();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu không có dòng dữ liệu, trả về false
        }
    }

    // Kiểm tra xem ID có tồn tại trong bảng users không
    public boolean idExists(int id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = :id";
        try (Handle handle = jdbi.open()) {
            int count = handle.createQuery(sql)
                    .bind("id", id)
                    .mapTo(int.class)
                    .one();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //kiểm tra username, email có tồn tại không
    public boolean isUserExist(String username, String email) {
        // Câu lệnh SQL kiểm tra sự tồn tại của người dùng
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND email = ? AND isDelete = FALSE";
        try (Handle handle = jdbi.open()) {
            int count = handle.createQuery(sql)
                    .bind(0, username)
                    .bind(1,email)
                    .mapTo(int.class)
                    .first();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Nếu không có dòng dữ liệu, trả về false
        }
    }

    //gửi email
    public static boolean sendMail(String to, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pl748664@gmail.com", "esov qzwb iohk uzsl");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress("pl748664@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Hàm tạo mật khẩu ngẫu nhiên 6 chữ số
    private String generateRandomPassword() {
        String chars = "0123456789";  // Chỉ chứa số
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    //gửi email cho người dùng quên mật khẩu
    public boolean passwordRecorvery(String username, String email) {

        Users user = userList.get(username);
        if (user != null) {
            // Tạo mật khẩu ngẫu nhiên mới
            String newPassword = generateRandomPassword();

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            String newPasswordHash = PasswordUtils.hashPassword(newPassword);
            user.setPassword(newPasswordHash);
            userList.replace(user.getUsername(), user);

            // Cập nhật mật khẩu vào cơ sở dữ liệu
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (Handle handle = jdbi.open()) {
                int rowsAffected = handle.createUpdate(sql)
                        .bind(0, newPasswordHash)  // Gắn mật khẩu đã mã hóa
                        .bind(1, username)         // Gắn tên người dùng
                        .execute();

                if (rowsAffected > 0) {
                    // Gửi mật khẩu mới cho người dùng sau khi cập nhật thành công
                    sendMail(email, "Khôi phục mật khẩu", "Mật khẩu mới của bạn là: " + newPassword);
                    System.out.println("Đổi mật khẩu thành công cho user: " + username);
                    return true;
                } else {
                    System.out.println("Không đổi được mật khẩu trong cơ sở dữ liệu cho user: " + username);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Không tìm thấy tài khoản");
            return false;
        }
    }

    //thêm acc admin
    public boolean addAdmin(Object obj) {
        Users user = (Users) obj;

        // Thêm vào danh sách trong bộ nhớ
        userList.put(user.getUsername(), user);

        // Câu lệnh SQL để thêm người dùng vào cơ sở dữ liệu
        String sql = "INSERT INTO `users` (`Username`, `Password`, `Email`, `Phone`, `Role`) \n" +
                "VALUES (?, ?, ?, ?,?)";

        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind(0, user.getUsername())
                    .bind(1, user.getPassword())
                    .bind(2, user.getEmail())
                    .bind(3, user.getPhone())
                    .bind(4, user.getRole())
                    .execute();
            return true;
        } catch (Exception e) {
            // Ghi lỗi vào log thay vì in ra console
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVerified(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT Is_verified FROM users WHERE email = :email")
                        .bind("email", email)
                        .mapTo(Boolean.class)
                        .findOne()
                        .orElse(false)
        );
    }


    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email ";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("email", email)
                    .mapToBean(Users.class)
                    .findOne()
                    .orElse(null);
        }
    }

    //update token
    public void updateVerificationToken(String email, String newToken ,Timestamp expiry) {
        String sql = "UPDATE users SET verification_token = :token,token_expiry = :expiry WHERE email = :email";
        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("token", newToken)
                    .bind("email", email)
                    .bind("expiry", expiry)
                    .execute();
        }
    }

    public String getEmailByUsername(String username) {
        String sql = "SELECT email FROM users WHERE username = :username";
        try (Handle handle = jdbi.open()) {
            return handle.createQuery(sql)
                    .bind("username", username)
                    .mapTo(String.class)
                    .findOne()
                    .orElse(null);
        }
    }
    // Lấy thông tin user theo token
    public Users getUserByToken(String token) {
        String sql = "SELECT username, email, verification_token, token_expiry FROM users WHERE verification_token = :token";
        try (Handle handle = jdbi.open()) {
            Optional<Users> user = handle.createQuery(sql)
                    .bind("token", token)
                    .map((rs, ctx) -> {
                        Users u = new Users();
                        u.setUsername(rs.getString("username"));
                        u.setEmail(rs.getString("email"));
                        u.setVerification_token(rs.getString("verification_token"));
                        u.setToken_expiry(rs.getTimestamp("token_expiry"));
                        return u;
                    })
                    .findOne();
            return user.orElse(null);
        }
    }

    // Cập nhật mật khẩu
    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = :password WHERE username = :username";
        try (Handle handle = jdbi.open()) {
            handle.createUpdate(sql)
                    .bind("password", newPassword) // Nên mã hóa trước khi gọi
                    .bind("username", username)
                    .execute();
        }
    }
    //khi dang nhap gg insert user
    public void insert(Users user) {
        String sql = "INSERT INTO users ( Id ,Username, Password, Email, FullName, is_verified, login_type) " +
                "VALUES (:id, :username, :password, :email, :fullName, :isVerified, :loginType)";

        try (Handle handle = jdbi.open()) {
                handle.createUpdate(sql)
                        .bind("id",user.getId())
                        .bind("username", user.getUsername())
                        .bind("password", user.getPassword())
                        .bind("email", user.getEmail())
                        .bind("fullName", user.getFullName())
                        .bind("isVerified", user.getIs_verified())
                        .bind("loginType", user.getLoginType())
                        .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    // Phương thức main để kiểm tra và truy vấn dữ liệu
    public static void main(String[] args) {
        // Lấy danh sách người dùng từ cơ sở dữ liệu
        Map<String, Users> userList = loadData();

        // In danh sách người dùng
        for (Users user : userList.values()) {
            System.out.println(user);
        }

        // Kiểm tra đăng nhập
        UserDAO dao = new UserDAO();

        dao.updateVerificationToken("gatrong015@gmail.com","f",null);

//        String passwordHash = PasswordUtils.hashPassword("admin123");
//        Users userAdmin = new Users("admin",passwordHash,"admin@gmail.com","admin","Admin");
//        System.out.println(dao.addAdmin(userAdmin));

//        System.out.println(dao.checkLogin("hmc", "524173"));
//        System.out.println(dao.checkLogin("tqc", "1234"));
//        System.out.println(dao.checkLogin("tqcc", "123"));

//        System.out.println(dao.userExists("hmc2"));

//        System.out.println(dao.passwordRecorvery("hmc","gatrong015@gmail.com"));

//        System.out.println(dao.changeInfor("hmc","HMC","0123456789","gatrong015@gmail.com","Bình phước"));
    }
}
