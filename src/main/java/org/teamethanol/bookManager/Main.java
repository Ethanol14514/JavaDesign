package org.teamethanol.bookManager;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTArcDarkIJTheme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatMTArcDarkIJTheme.setup();
        DatabaseManager.init(); // 初始化数据库连接
        Home home = new Home();
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
