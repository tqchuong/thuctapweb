package fit.hcmuaf.edu.vn.foodmart.dao;

import fit.hcmuaf.edu.vn.foodmart.dao.db.DBConnect;
import fit.hcmuaf.edu.vn.foodmart.model.Activity_log;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.ArrayList;

import java.util.List;

public class Activity_logDAO {
    private static final Jdbi jdbi = DBConnect.getJdbi();

    public static List<Activity_log> getAllLogs() {
        List<Activity_log> logs = new ArrayList<>();
        String sql = "SELECT * FROM activity_log ORDER BY time_log DESC";

        try (Handle handle = jdbi.open()) {
            logs = handle.createQuery(sql)
                    .mapToBean(Activity_log.class)
                    .list();
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy dữ liệu activity_log: " + e.getMessage());
            e.printStackTrace();
        }

        return logs;
    }
    // 👉 Hàm main để test
    public static void main(String[] args) {
        List<Activity_log> logs = getAllLogs();
        for (Activity_log log : logs) {
            System.out.println("ID: " + log.getUser_id());
            System.out.println("Người dùng: " + log.getUsername());
            System.out.println("Hành động: " + log.getAction());
            System.out.println("Mức độ: " + log.getLevel_log());
            System.out.println("IP: " + log.getIp_address());
            System.out.println("Thời gian: " + log.getTime_log());
            System.out.println("Trang: " + log.getSource_page());
            System.out.println("Tài nguyên: " + log.getResource());
            System.out.println("Dữ liệu cũ: " + log.getOld_data());
            System.out.println("Dữ liệu mới: " + log.getNew_data());
            System.out.println("-------------------------------------");
        }
    }
}
