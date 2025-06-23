/*
 * Created by JFormDesigner on Thu Jun 19 22:14:31 HKT 2025
 */

package org.teamethanol.boolManager;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author 35535
 */
public class Admin extends JFrame {
    public Admin() {
        initComponents();
        bookAction.addActionListener(e -> showBookActionDialog());
        queryUser.addActionListener(e -> showQueryUserDialog());
        queryBook.addActionListener(e -> showQueryAllBooksDialog());
        editUser.addActionListener(e -> showEditUserDialog());
        okButton.addActionListener(e -> {
            this.dispose();
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        separator1 = new JSeparator();
        label3 = new JLabel();
        label4 = new JLabel();
        bookAction = new JButton();
        queryUser = new JButton();
        queryBook = new JButton();
        editUser = new JButton();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("\u7ba1\u7406\u7cfb\u7edf");
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
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- label1 ----
                label1.setText("\u6570\u636e\u7ba1\u7406\u754c\u9762");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 10f));
                contentPanel.add(label1, "cell 0 0 4 1");

                //---- label2 ----
                label2.setText("\u9009\u62e9\u64cd\u4f5c\uff1a");
                contentPanel.add(label2, "cell 0 1 2 1");
                contentPanel.add(separator1, "cell 0 2 12 1");

                //---- label3 ----
                label3.setText("\u4e66\u7c4d\u76f8\u5173");
                contentPanel.add(label3, "cell 0 3");

                //---- label4 ----
                label4.setText("\u7528\u6237\u76f8\u5173");
                contentPanel.add(label4, "cell 8 3");

                //---- bookAction ----
                bookAction.setText("\u6dfb\u52a0/\u5220\u9664\u4e66\u7c4d");
                contentPanel.add(bookAction, "cell 0 4");

                //---- queryUser ----
                queryUser.setText("\u67e5\u8be2\u7528\u6237\u903e\u671f");
                contentPanel.add(queryUser, "cell 8 4");

                //---- queryBook ----
                queryBook.setText("\u67e5\u8be2\u6240\u6709\u4e66\u7c4d");
                contentPanel.add(queryBook, "cell 0 5");

                //---- editUser ----
                editUser.setText("\u4fee\u6539\u7528\u6237\u4fe1\u606f");
                contentPanel.add(editUser, "cell 8 5");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("\u5173\u95ed");
                buttonBar.add(okButton, "cell 0 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // 图书添加/删除
    private void showBookActionDialog() {
        String[] options = {"添加图书", "删除图书"};
        int choice = JOptionPane.showOptionDialog(this, "请选择操作：", "图书操作", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) { // 添加
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField isbnField = new JTextField();
            JTextField publisherField = new JTextField();
            JTextField yearField = new JTextField();
            JTextField stockField = new JTextField();
            JTextField priceField = new JTextField();
            Object[] message = {
                "书名:", titleField,
                "作者:", authorField,
                "ISBN号:", isbnField,
                "出版社:", publisherField,
                "出版时间:", yearField,
                "库存量:", stockField,
                "价格:", priceField
            };
            int result = JOptionPane.showConfirmDialog(this, message, "添加图书", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String isbn = isbnField.getText();
                    String publisher = publisherField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    int stock = Integer.parseInt(stockField.getText());
                    double price = Double.parseDouble(priceField.getText());

                    // 生成书号前缀：ISBN后6位
                    String isbnSuffix = isbn.replaceAll("[- ]", "");
                    if (isbnSuffix.length() < 6) throw new Exception();
                    isbnSuffix = isbnSuffix.substring(isbnSuffix.length() - 6);

                    // 先插入主信息（00号，stock为总量）
                    String bookNumberMain = isbnSuffix + "00";
                    boolean ok = DatabaseManager.addBookWithNumber(title, author, isbn, publisher, year, stock, price, bookNumberMain, false, null, null, null);
                    if (!ok) {
                        JOptionPane.showMessageDialog(this, "添加失败，ISBN格式不正确或已存在");
                        return;
                    }
                    // 再插入每本单独的副本（01~N，stock=1）
                    for (int i = 1; i <= stock; i++) {
                        String bookNumber = isbnSuffix + String.format("%02d", i);
                        DatabaseManager.addBookWithNumber(title, author, isbn, publisher, year, 1, price, bookNumber, false, null, null, null);
                    }
                    JOptionPane.showMessageDialog(this, "添加成功，主书号为：" + bookNumberMain + "，副本共" + stock + "本");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "输入格式有误");
                }
            }
        } else if (choice == 1) { // 删除
            String isbn = JOptionPane.showInputDialog(this, "请输入要删除的ISBN号:");
            if (isbn != null && !isbn.isEmpty()) {
                try {
                    boolean ok = DatabaseManager.deleteBook(isbn);
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "删除成功");
                    } else {
                        JOptionPane.showMessageDialog(this, "删除失败，未找到该ISBN");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "输入格式有误");
                }
            }
        }
    }

    // 查询所有书籍（支持主信息/全部副本切换）
    private void showQueryAllBooksDialog() {
        String[] options = {"仅主信息", "全部副本"};
        int mode = JOptionPane.showOptionDialog(this, "请选择显示模式：", "显示选项", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        try {
            java.sql.ResultSet rs = DatabaseManager.queryAllBooks();
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            while (rs != null && rs.next()) {
                String bookNumber = rs.getString("book_number");
                if (mode == 0 && (bookNumber == null || !bookNumber.endsWith("00"))) {
                    continue;
                }
                sb.append("书名: ").append(rs.getString("title"))
                  .append(", 作者: ").append(rs.getString("author"))
                  .append(", ISBN: ").append(rs.getString("isbn"))
                  .append(", 出版社: ").append(rs.getString("publisher"))
                  .append(", 出版时间: ").append(rs.getInt("publish_year"))
                  .append(", 库存: ").append(rs.getInt("stock"))
                  .append(", 价格: ").append(rs.getDouble("price"))
                  .append(", 书号: ").append(bookNumber)
                  .append(", 借出: ").append(rs.getInt("isBorrowed") == 1 ? "是" : "否");
                if (rs.getInt("isBorrowed") == 1) {
                    sb.append(", 借阅人: ").append(rs.getString("borrower"))
                      .append(", 借阅日期: ").append(rs.getString("borrow_start"))
                      .append(", 应还日期: ").append(rs.getString("borrow_end"));
                }
                sb.append("\n");
            }
            if (sb.length() == 0) sb.append("无书籍信息");
            textArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(this, scrollPane, mode == 0 ? "所有主信息书籍" : "所有书籍副本", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询失败");
        }
    }

    // 查询用户逾期（真正实现逾期判断）
    private void showQueryUserDialog() {
        String username = JOptionPane.showInputDialog(this, "请输入用户名:");
        if (username == null || username.isEmpty()) return;
        try {
            java.sql.ResultSet rs = DatabaseManager.queryUserBorrowedBooks(username);
            StringBuilder sb = new StringBuilder();
            java.time.LocalDate today = java.time.LocalDate.now();
            int overdueCount = 0;
            while (rs != null && rs.next()) {
                String end = rs.getString("borrow_end");
                if (end != null && !end.isEmpty()) {
                    java.time.LocalDate due = java.time.LocalDate.parse(end);
                    if (due.isBefore(today)) {
                        overdueCount++;
                        sb.append("书名: ").append(rs.getString("title"))
                          .append(", 书号: ").append(rs.getString("book_number"))
                          .append(", 应还日期: ").append(end)
                          .append(", 已逾期").append("\n");
                    }
                }
            }
            if (overdueCount == 0) {
                sb.append("该用户无逾期借阅");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "逾期查询", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "查询失败");
        }
    }

    // 用户信息增删改（增加校验）
    private void showEditUserDialog() {
        String[] options = {"添加用户", "删除用户", "修改用户"};
        int choice = JOptionPane.showOptionDialog(this, "请选择操作：", "用户操作", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) { // 添加
            JTextField usernameField = new JTextField();
            JTextField passwordField = new JTextField();
            JCheckBox adminBox = new JCheckBox("管理员");
            Object[] message = {
                "用户名:", usernameField,
                "密码:", passwordField,
                adminBox
            };
            int result = JOptionPane.showConfirmDialog(this, message, "添加用户", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "用户名和密码不能为空");
                    return;
                }
                if (UserDatabaseManager.userExists(username)) {
                    JOptionPane.showMessageDialog(this, "用户名已存在");
                    return;
                }
                UserDatabaseManager.addUser(username, password, adminBox.isSelected());
                JOptionPane.showMessageDialog(this, "添加成功");
            }
        } else if (choice == 1) { // 删除
            String username = JOptionPane.showInputDialog(this, "请输入要删除的用户名:");
            if (username != null && !username.isEmpty()) {
                if (!UserDatabaseManager.userExists(username)) {
                    JOptionPane.showMessageDialog(this, "用户不存在");
                    return;
                }
                boolean ok = UserDatabaseManager.deleteUser(username);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败，未找到该用户");
                }
            }
        } else if (choice == 2) { // 修改
            String username = JOptionPane.showInputDialog(this, "请输入要修改的用户名:");
            if (username != null && !username.isEmpty()) {
                if (!UserDatabaseManager.userExists(username)) {
                    JOptionPane.showMessageDialog(this, "用户不存在");
                    return;
                }
                JTextField passwordField = new JTextField();
                JCheckBox adminBox = new JCheckBox("管理员");
                Object[] message = {
                    "新密码:", passwordField,
                    adminBox
                };
                int result = JOptionPane.showConfirmDialog(this, message, "修改用户", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    boolean ok = UserDatabaseManager.updateUser(username, passwordField.getText(), adminBox.isSelected());
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "修改成功");
                    } else {
                        JOptionPane.showMessageDialog(this, "修改失败");
                    }
                }
            }
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JSeparator separator1;
    private JLabel label3;
    private JLabel label4;
    private JButton bookAction;
    private JButton queryUser;
    private JButton queryBook;
    private JButton editUser;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
