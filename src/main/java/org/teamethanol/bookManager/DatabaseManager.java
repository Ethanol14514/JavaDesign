package org.teamethanol.bookManager;

import java.io.File;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_FILE = "data.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE;
    private static Connection connection;

    public static void init() {
        try {
            File dbFile = new File(DB_FILE);
            boolean exists = dbFile.exists();
            connection = DriverManager.getConnection(DB_URL);
            if (!exists) {
                System.out.println("数据库文件不存在，已创建新数据库: " + DB_FILE);
                createBookTable();
            } else {
                System.out.println("数据库文件已存在，已连接: " + DB_FILE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createBookTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "author TEXT NOT NULL, " +
                "isbn TEXT NOT NULL, " +
                "issn TEXT, " +
                "ean13 TEXT, " +
                "type TEXT NOT NULL, " + // 新增字段，区分图书/期刊类型
                "publisher TEXT, " +
                "publish_year INTEGER, " +
                "stock INTEGER, " +
                "price REAL, " +
                "book_number TEXT NOT NULL, " +
                "isBorrowed INTEGER DEFAULT 0, " +
                "borrower TEXT, " +
                "borrow_start TEXT, " +
                "borrow_end TEXT, " +
                "UNIQUE(book_number)" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 增加图书（ISBN校验）
    public static boolean addBook(String title, String author, String isbn, String publisher, int publishYear, int stock, double price) {
        if (!isValidISBN(isbn)) return false;
        String sql = "INSERT INTO books (title, author, isbn, publisher, publish_year, stock, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, publishYear);
            pstmt.setInt(6, stock);
            pstmt.setDouble(7, price);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ISBN格式校验（支持ISBN-10和ISBN-13）
    public static boolean isValidISBN(String isbn) {
        isbn = isbn.replaceAll("[- ]", "");
        if (isbn.length() == 10) {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                if (!Character.isDigit(isbn.charAt(i))) return false;
                sum += (isbn.charAt(i) - '0') * (10 - i);
            }
            char last = isbn.charAt(9);
            sum += (last == 'X' || last == 'x') ? 10 : (Character.isDigit(last) ? (last - '0') : -1);
            return sum % 11 == 0;
        } else if (isbn.length() == 13) {
            int sum = 0;
            for (int i = 0; i < 13; i++) {
                if (!Character.isDigit(isbn.charAt(i))) return false;
                int digit = isbn.charAt(i) - '0';
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            return sum % 10 == 0;
        }
        return false;
    }

    // 删除图书
    public static boolean deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除期刊（通过issn和ean13联合删除）
    public static boolean deleteBookByIssnAndEan13(String issn, String ean13) {
        String sql = "DELETE FROM books WHERE issn = ? AND ean13 = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, issn);
            pstmt.setString(2, ean13);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 修改图书
    public static boolean updateBook(String isbn, String title, String author, String publisher, int publishYear, int stock, double price) {
        String sql = "UPDATE books SET title=?, author=?, publisher=?, publish_year=?, stock=?, price=? WHERE isbn=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, publisher);
            pstmt.setInt(4, publishYear);
            pstmt.setInt(5, stock);
            pstmt.setDouble(6, price);
            pstmt.setString(7, isbn);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 添加带书号和借阅信息的图书
    public static boolean addBookWithNumber(String title, String author, String isbn, String publisher, int publishYear, int stock, double price, String bookNumber, boolean isBorrowed, String borrower, String borrowStart, String borrowEnd) {
        if (!isValidISBN(isbn)) return false;
        String sql = "INSERT INTO books (title, author, isbn, publisher, publish_year, stock, price, book_number, isBorrowed, borrower, borrow_start, borrow_end) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, publishYear);
            pstmt.setInt(6, stock);
            pstmt.setDouble(7, price);
            pstmt.setString(8, bookNumber);
            pstmt.setInt(9, isBorrowed ? 1 : 0);
            pstmt.setString(10, borrower);
            pstmt.setString(11, borrowStart);
            pstmt.setString(12, borrowEnd);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 添加带书号和借阅信息的图书（支持type字段）
    public static boolean addBookWithNumber(String title, String author, String isbn, String type, String publisher, int publishYear, int stock, double price, String bookNumber, boolean isBorrowed, String borrower, String borrowStart, String borrowEnd) {
        String sql = "INSERT INTO books (title, author, isbn, type, publisher, publish_year, stock, price, book_number, isBorrowed, borrower, borrow_start, borrow_end) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, type);
            pstmt.setString(5, publisher);
            pstmt.setInt(6, publishYear);
            pstmt.setInt(7, stock);
            pstmt.setDouble(8, price);
            pstmt.setString(9, bookNumber);
            pstmt.setInt(10, isBorrowed ? 1 : 0);
            pstmt.setString(11, borrower);
            pstmt.setString(12, borrowStart);
            pstmt.setString(13, borrowEnd);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 添加带书号和借阅信息的图书（支持type/issn/ean13字段）
    public static boolean addBookWithNumber(String title, String author, String isbn, String issn, String ean13, String type, String publisher, int publishYear, int stock, double price, String bookNumber, boolean isBorrowed, String borrower, String borrowStart, String borrowEnd) {
        String sql = "INSERT INTO books (title, author, isbn, issn, ean13, type, publisher, publish_year, stock, price, book_number, isBorrowed, borrower, borrow_start, borrow_end) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, issn);
            pstmt.setString(5, ean13);
            pstmt.setString(6, type);
            pstmt.setString(7, publisher);
            pstmt.setInt(8, publishYear);
            pstmt.setInt(9, stock);
            pstmt.setDouble(10, price);
            pstmt.setString(11, bookNumber);
            pstmt.setInt(12, isBorrowed ? 1 : 0);
            pstmt.setString(13, borrower);
            pstmt.setString(14, borrowStart);
            pstmt.setString(15, borrowEnd);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查询主书号（后两位00）图书
    public static ResultSet queryMainBooks() {
        String sql = "SELECT * FROM books WHERE substr(book_number, -2, 2) = '00'";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询某本副本（通过book_number）
    public static ResultSet queryBookByBookNumber(String bookNumber) {
        String sql = "SELECT * FROM books WHERE book_number = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bookNumber);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 更新借阅状态
    public static boolean updateBorrowStatus(String bookNumber, boolean isBorrowed, String borrower, String borrowStart, String borrowEnd) {
        String sql = "UPDATE books SET isBorrowed=?, borrower=?, borrow_start=?, borrow_end=? WHERE book_number=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, isBorrowed ? 1 : 0);
            pstmt.setString(2, borrower);
            pstmt.setString(3, borrowStart);
            pstmt.setString(4, borrowEnd);
            pstmt.setString(5, bookNumber);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查询图书（可按ISBN号查询单本）
    public static ResultSet queryBook(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询所有图书
    public static ResultSet queryAllBooks() {
        String sql = "SELECT * FROM books";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询指定ISBN后6位的所有书号（book_number）
    public static ResultSet queryBookNumberByIsbnSuffix(String isbnSuffix) {
        String sql = "SELECT book_number FROM books WHERE substr(book_number, 1, 6) = ? ORDER BY book_number DESC";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbnSuffix);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 多条件组合查询（只查主信息）
    public static ResultSet queryBooksByConditions(String title, String author, String publisher, String isbn) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");
        if (title != null && !title.isEmpty()) sql.append(" AND title LIKE ?");
        if (author != null && !author.isEmpty()) sql.append(" AND author LIKE ?");
        if (publisher != null && !publisher.isEmpty()) sql.append(" AND publisher LIKE ?");
        if (isbn != null && !isbn.isEmpty()) sql.append(" AND isbn LIKE ?");
        // 只查主信息
        sql.append(" AND substr(book_number, -2, 2) = '00'");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql.toString());
            int idx = 1;
            if (title != null && !title.isEmpty()) pstmt.setString(idx++, "%" + title + "%");
            if (author != null && !author.isEmpty()) pstmt.setString(idx++, "%" + author + "%");
            if (publisher != null && !publisher.isEmpty()) pstmt.setString(idx++, "%" + publisher + "%");
            if (isbn != null && !isbn.isEmpty()) pstmt.setString(idx++, "%" + isbn + "%");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询可借副本
    public static ResultSet queryBookCopiesForBorrow(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ? AND substr(book_number, -2, 2) != '00' ORDER BY book_number";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询当前用户已借副本
    public static ResultSet queryBookCopiesByUser(String isbn, String username) {
        String sql = "SELECT * FROM books WHERE isbn = ? AND borrower = ? AND isBorrowed = 1 AND substr(book_number, -2, 2) != '00'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.setString(2, username);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 查询用户已借阅的所有书籍
    public static ResultSet queryUserBorrowedBooks(String username) {
        String sql = "SELECT * FROM books WHERE borrower = ? AND isBorrowed = 1 ORDER BY borrow_start DESC";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
