package org.teamethanol.bookManager;

import java.io.File;
import java.sql.*;
import java.security.MessageDigest;

/**
 * 使用的为SQLite作为数据库存储，其他数据库应当进行相应修改。
 */

public class UserDatabaseManager {
    private static final String DB_FILE = "user.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE;
    private static Connection connection;

    public static void init() {
        try {
            File dbFile = new File(DB_FILE);
            boolean exists = dbFile.exists();
            connection = DriverManager.getConnection(DB_URL);
            if (!exists) {
                createUserTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "is_admin INTEGER NOT NULL DEFAULT 0" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 初始管理员密码存为MD5
        String adminHash = md5("passw0rd");
        sql = "INSERT INTO users (username, password, is_admin) VALUES ('admin', '" + adminHash + "', 1);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取MD5哈希
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, md5(password));
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAdmin(String username) {
        String sql = "SELECT is_admin FROM users WHERE username=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("is_admin") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addUser(String username, String password, boolean isAdmin) {
        String sql = "INSERT INTO users (username, password, is_admin) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, md5(password));
            pstmt.setInt(3, isAdmin ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(String username, String password, boolean isAdmin) {
        String sql = "UPDATE users SET password=?, is_admin=? WHERE username=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, md5(password));
            pstmt.setInt(2, isAdmin ? 1 : 0);
            pstmt.setString(3, username);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // 判断用户是否存在
    public static boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
