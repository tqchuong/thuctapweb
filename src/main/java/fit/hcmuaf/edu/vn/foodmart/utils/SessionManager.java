package fit.hcmuaf.edu.vn.foodmart.utils;

import jakarta.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    // Dùng ConcurrentHashMap để đảm bảo thread-safe
    static final Map<String, List<HttpSession>> sessionMap = new ConcurrentHashMap<>();

    // Thêm session khi login thành công
    public static void addSession(String username, HttpSession session) {
        sessionMap.computeIfAbsent(username, k -> Collections.synchronizedList(new ArrayList<>())).add(session);
    }

    // Xóa session khi logout
    public static void removeSession(String username, HttpSession session) {
        List<HttpSession> sessions = sessionMap.get(username);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    // Xóa tất cả session của người dùng khi đổi quyền
    public static void invalidateAllSessions(String username) {
        List<HttpSession> sessions = sessionMap.get(username);
        if (sessions != null) {
            synchronized (sessions) {
                for (HttpSession s : sessions) {
                    try {
                        s.invalidate();
                    } catch (Exception ignored) {}
                }
                sessions.clear();
            }
            sessionMap.remove(username);
        }
    }




    // Kiểm tra session hợp lệ (dùng cho filter)
    public static boolean isSessionValid(String username, HttpSession session) {
        List<HttpSession> sessions = sessionMap.get(username);
        return sessions != null && sessions.contains(session);
    }
}