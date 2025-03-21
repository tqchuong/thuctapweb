package fit.hcmuaf.edu.vn.foodmart.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Users implements Serializable {

    private int Id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    private String role;
    private String userStatus;
    private Timestamp created_at;
    private String verification_token;  //link xác thực
    private boolean is_verified;    //đã xác thực chưa?
    private Timestamp token_expiry; //thời hạn xác thực
    private int loginAttempts; // Số lần đăng nhập sai
    private Timestamp lockTime; // Thời gian bị khóa

    public Users() {
    }


    public Users(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }


    public Users(String username, String password, String email, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Users(String username, String password, String email, String phone, String verification_token, boolean b) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.verification_token = verification_token;
        this.is_verified = b;
    }

    public Timestamp getLockTime() {
        return lockTime;
    }

    public void setLockTime(Timestamp lockTime) {
        this.lockTime = lockTime;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public String getVerification_token() {
        return verification_token;
    }

    public void setVerification_token(String verification_token) {
        this.verification_token = verification_token;
    }

    public Timestamp getToken_expiry() {
        return token_expiry;
    }

    public void setToken_expiry(Timestamp token_expiry) {
        this.token_expiry = token_expiry;
    }

//    public boolean isVerified() {
//        return is_verified;
//    }
//
//    public void setVerified(boolean is_verified) {
//        this.is_verified = is_verified;
//    }


    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", created_at=" + created_at +
                ", verification_token='" + verification_token + '\'' +
                ", is_verified=" + is_verified +
                ", token_expiry=" + token_expiry +
                ", loginAttempts=" + loginAttempts +
                ", lockTime=" + lockTime +
                '}';
    }
}