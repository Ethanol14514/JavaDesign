/*
 * Created by JFormDesigner on Thu Jun 19 21:40:00 HKT 2025
 */

package org.teamethanol.boolManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author 35535
 */
public class Home extends JFrame {
    public Home() {
        UserDatabaseManager.init(); // 初始化用户数据库
        initComponents();
        loginButton.addActionListener(e -> showLoginDialog());
        adminButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (adminButton.isEnabled()) {
                    Admin admin = new Admin();
                    admin.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Home.this, "您没有管理员权限！", "权限不足", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        queryButton.addActionListener(e -> showBookQueryDialog());
        actionButton.addActionListener(e -> showBorrowReturnDialog());
        exploreButton.addActionListener(e -> showExploreBooksDialog());
        infoButton.addActionListener(e -> showUserInfoDialog());
        exitButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(Home.this, "确定要退出吗？", "退出", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Home.this.dispose();
                System.exit(0);
            }

        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        loginButton = new JButton();
        label4 = new JLabel();
        status = new JTextField();
        separator2 = new JSeparator();
        label3 = new JLabel();
        queryButton = new JButton();
        actionButton = new JButton();
        exploreButton = new JButton();
        infoButton = new JButton();
        separator3 = new JSeparator();
        label5 = new JLabel();
        adminButton = new JButton();
        buttonBar = new JPanel();
        exitButton = new JButton();

        //======== this ========
        setTitle("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- label1 ----
                label1.setText("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 10f));
                contentPanel.add(label1, "cell 0 0 4 1");

                //---- label2 ----
                label2.setText("\u8bf7\u5148\u767b\u5f55");
                contentPanel.add(label2, "cell 0 1 4 1");

                //---- loginButton ----
                loginButton.setText("\u767b\u5f55");
                contentPanel.add(loginButton, "cell 0 2");

                //---- label4 ----
                label4.setText("\u767b\u5f55\u72b6\u6001");
                contentPanel.add(label4, "cell 1 2");

                //---- status ----
                status.setEditable(false);
                status.setText("\u672a\u767b\u5f55");
                status.setEnabled(false);
                contentPanel.add(status, "cell 2 2 19 1");
                contentPanel.add(separator2, "cell 0 3 21 1");

                //---- label3 ----
                label3.setText("\u666e\u901a\u7528\u6237\u64cd\u4f5c");
                contentPanel.add(label3, "cell 0 5");

                //---- queryButton ----
                queryButton.setText("\u4fe1\u606f\u67e5\u8be2");
                queryButton.setEnabled(false);
                contentPanel.add(queryButton, "cell 0 6");

                //---- actionButton ----
                actionButton.setText("\u501f/\u8fd8");
                actionButton.setEnabled(false);
                contentPanel.add(actionButton, "cell 1 6");

                //---- exploreButton ----
                exploreButton.setText("\u6d4f\u89c8");
                exploreButton.setEnabled(false);
                contentPanel.add(exploreButton, "cell 2 6");

                //---- infoButton ----
                infoButton.setText("\u4e2a\u4eba\u4fe1\u606f");
                infoButton.setEnabled(false);
                contentPanel.add(infoButton, "cell 3 6");
                contentPanel.add(separator3, "cell 0 7 21 1");

                //---- label5 ----
                label5.setText("\u7ba1\u7406\u5458\u64cd\u4f5c");
                contentPanel.add(label5, "cell 0 8");

                //---- adminButton ----
                adminButton.setText("\u7ba1\u7406\u5458\u64cd\u4f5c");
                adminButton.setEnabled(false);
                contentPanel.add(adminButton, "cell 0 10");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- exitButton ----
                exitButton.setText("\u9000\u51fa");
                buttonBar.add(exitButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "用户名:", usernameField,
            "密码:", passwordField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "登录", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (UserDatabaseManager.checkLogin(username, password)) {
                boolean isAdmin = UserDatabaseManager.isAdmin(username);
                if (isAdmin) {
                    status.setText(username + "（管理员）已登录");
                    adminButton.setEnabled(true);
                } else {
                    status.setText(username + "已登录");
                    adminButton.setEnabled(false);
                }
                queryButton.setEnabled(true);
                actionButton.setEnabled(true);
                exploreButton.setEnabled(true);
                infoButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 多条件图书查询
    private void showBookQueryDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField isbnField = new JTextField();
        Object[] message = {
            "书名(可选):", titleField,
            "作者(可选):", authorField,
            "出版社(可选):", publisherField,
            "ISBN(可选):", isbnField
        };
        int result = JOptionPane.showConfirmDialog(this, message, "图书多条件查询", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String publisher = publisherField.getText().trim();
            String isbn = isbnField.getText().trim();
            java.sql.ResultSet rs = DatabaseManager.queryBooksByConditions(title, author, publisher, isbn);
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            try {
                while (rs != null && rs.next()) {
                    String bookNumber = rs.getString("book_number");
                    if (bookNumber == null || !bookNumber.endsWith("00")) continue;
                    sb.append("书名: ").append(rs.getString("title"))
                      .append(", 作者: ").append(rs.getString("author"))
                      .append(", ISBN: ").append(rs.getString("isbn"))
                      .append(", 出版社: ").append(rs.getString("publisher"))
                      .append(", 出版时间: ").append(rs.getInt("publish_year"))
                      .append(", 库存: ").append(rs.getInt("stock"))
                      .append(", 价格: ").append(rs.getDouble("price"))
                      .append(", 书号: ").append(bookNumber)
                      .append("\n");
                }
            } catch (Exception ex) {
                sb.append("查询失败");
            }
            if (sb.length() == 0) sb.append("无符合条件的图书");
            textArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "查询结果", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 用户信息及借阅情况
    private void showUserInfoDialog() {
        String username = status.getText().replace("已登录", "").replace("（管理员）", "").trim();
        if (username.isEmpty() || "未登录".equals(username)) {
            JOptionPane.showMessageDialog(this, "请先登录！", "未登录", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            java.sql.ResultSet rs = DatabaseManager.queryUserBorrowedBooks(username);
            StringBuilder sb = new StringBuilder();
            while (rs != null && rs.next()) {
                sb.append("书名: ").append(rs.getString("title"))
                  .append(", 作者: ").append(rs.getString("author"))
                  .append(", ISBN: ").append(rs.getString("isbn"))
                  .append(", 书号: ").append(rs.getString("book_number"))
                  .append(", 借阅日期: ").append(rs.getString("borrow_start"))
                  .append(", 应还日期: ").append(rs.getString("borrow_end"))
                  .append("\n");
            }
            if (sb.length() == 0) sb.append("无已借阅书籍");
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "我的借阅信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询失败");
        }
    }

    // 借还书功能（支持书号或ISBN）
    private void showBorrowReturnDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField bookNumberField = new JTextField();
        JTextField isbnField = new JTextField();
        panel.add(new JLabel("书籍编号(可选):"));
        panel.add(bookNumberField);
        panel.add(new JLabel("ISBN(可选):"));
        panel.add(isbnField);
        int result = JOptionPane.showConfirmDialog(this, panel, "借阅/归还图书", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;
        String bookNumber = bookNumberField.getText().trim();
        String isbn = isbnField.getText().trim();
        if (bookNumber.isEmpty() && isbn.isEmpty()) return;
        String username = status.getText().replace("已登录", "").replace("（管理员）", "").trim();
        try {
            if (!bookNumber.isEmpty()) {
                // 先按书号处理
                java.sql.ResultSet rs = DatabaseManager.queryBookByBookNumber(bookNumber);
                if (rs != null && rs.next()) {
                    int isBorrowed = rs.getInt("isBorrowed");
                    String borrower = rs.getString("borrower");
                    if (isBorrowed == 0) {
                        // 借书
                        String borrowStart = java.time.LocalDate.now().toString();
                        String borrowEnd = java.time.LocalDate.now().plusDays(30).toString();
                        boolean ok = DatabaseManager.updateBorrowStatus(bookNumber, true, username, borrowStart, borrowEnd);
                        if (ok) {
                            JOptionPane.showMessageDialog(this, "借阅成功，书号：" + bookNumber + "，应还日期：" + borrowEnd);
                        } else {
                            JOptionPane.showMessageDialog(this, "借阅失败");
                        }
                    } else if (isBorrowed == 1 && username.equals(borrower)) {
                        // 归还
                        boolean ok = DatabaseManager.updateBorrowStatus(bookNumber, false, null, null, null);
                        if (ok) {
                            JOptionPane.showMessageDialog(this, "归还成功，书号：" + bookNumber);
                        } else {
                            JOptionPane.showMessageDialog(this, "归还失败");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "该书已被他人借阅，或您无权归还");
                    }
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "未找到该书号");
                    return;
                }
            }
            // 按ISBN处理（与原逻辑一致）
            java.sql.ResultSet rs = DatabaseManager.queryBookCopiesForBorrow(isbn);
            boolean found = false;
            while (rs != null && rs.next()) {
                String bNum = rs.getString("book_number");
                int isBorrowed = rs.getInt("isBorrowed");
                if (isBorrowed == 0) {
                    String borrowStart = java.time.LocalDate.now().toString();
                    String borrowEnd = java.time.LocalDate.now().plusDays(30).toString();
                    boolean ok = DatabaseManager.updateBorrowStatus(bNum, true, username, borrowStart, borrowEnd);
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "借阅成功，书号：" + bNum + "，应还日期：" + borrowEnd);
                    } else {
                        JOptionPane.showMessageDialog(this, "借阅失败");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                // 尝试归还
                rs = DatabaseManager.queryBookCopiesByUser(isbn, username);
                boolean returned = false;
                while (rs != null && rs.next()) {
                    String bNum = rs.getString("book_number");
                    int isBorrowed = rs.getInt("isBorrowed");
                    if (isBorrowed == 1) {
                        boolean ok = DatabaseManager.updateBorrowStatus(bNum, false, null, null, null);
                        if (ok) {
                            JOptionPane.showMessageDialog(this, "归还成功，书号：" + bNum);
                        } else {
                            JOptionPane.showMessageDialog(this, "归还失败");
                        }
                        returned = true;
                        break;
                    }
                }
                if (!returned) {
                    JOptionPane.showMessageDialog(this, "无可借副本，且您未借阅此书");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "操作失败");
        }
    }

    // 浏览所有主信息图书
    private void showExploreBooksDialog() {
        try {
            java.sql.ResultSet rs = DatabaseManager.queryMainBooks();
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            while (rs != null && rs.next()) {
                sb.append("书名: ").append(rs.getString("title"))
                  .append(", 作者: ").append(rs.getString("author"))
                  .append(", ISBN: ").append(rs.getString("isbn"))
                  .append(", 出版社: ").append(rs.getString("publisher"))
                  .append(", 出版时间: ").append(rs.getInt("publish_year"))
                  .append(", 库存: ").append(rs.getInt("stock"))
                  .append(", 价格: ").append(rs.getDouble("price"))
                  .append(", 书号: ").append(rs.getString("book_number"))
                  .append("\n");
            }
            if (sb.length() == 0) sb.append("无书籍信息");
            textArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scrollPane, "浏览图书", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询失败");
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JButton loginButton;
    private JLabel label4;
    private JTextField status;
    private JSeparator separator2;
    private JLabel label3;
    private JButton queryButton;
    private JButton actionButton;
    private JButton exploreButton;
    private JButton infoButton;
    private JSeparator separator3;
    private JLabel label5;
    private JButton adminButton;
    private JPanel buttonBar;
    private JButton exitButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
