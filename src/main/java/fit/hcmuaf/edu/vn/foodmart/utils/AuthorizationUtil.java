package fit.hcmuaf.edu.vn.foodmart.utils;

import fit.hcmuaf.edu.vn.foodmart.model.Users;

public class AuthorizationUtil {

    public static boolean canViewDashboard(Users user) {
        return user != null;
    }

    public static boolean canViewProducts(Users user) {
        return user != null && (
                user.getRole().equals("Admin") || user.getRole().equals("WarehouseManager")
        );
    }

    public static boolean canViewCustomers(Users user) {
        return user != null && user.getRole().equals("Admin");
    }

    public static boolean canViewOrders(Users user) {
        return user != null && (
                user.getRole().equals("Admin") || user.getRole().equals("OrderConfirmator")
        );
    }

    public static boolean canViewStatistics(Users user) {
        return user != null && user.getRole().equals("Admin");
    }

    public static boolean canViewSuppliers(Users user) {
        return user != null && (
                user.getRole().equals("Admin") || user.getRole().equals("WarehouseManager")
        );
    }

    public static boolean canViewVouchers(Users user) {
        return user != null && user.getRole().equals("Admin");
    }

    public static boolean canViewActivityLogs(Users user) {
        return user != null && user.getRole().equals("Admin");
    }

    public static boolean canViewShipping(Users user) {
        return user != null && (
                user.getRole().equals("Admin") || user.getRole().equals("OrderConfirmator")
        );
    }
}
