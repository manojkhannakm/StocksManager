package com.manojkhannakm.stocksmanager.tabs.about;

import com.manojkhannakm.stocksmanager.StocksManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Manoj Khanna
 */

public class AboutTab extends JPanel {

    public AboutTab() {
        setLayout(null);
        setBackground(Color.WHITE);
    }

    public void addComponents() {
        JLabel title = new JLabel("Stocks Manager v2.0.2");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
        title.setSize(title.getPreferredSize());
        StocksManager.setCenterAlign(this, title, 0, -35);
        add(title);

        JLabel name = new JLabel("Developed by: Manoj Khanna");
        name.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        name.setSize(name.getPreferredSize());
        StocksManager.setCenterAlign(this, name, 0, 0);
        add(name);

        JLabel email = new JLabel("manojkhannakm@gmail.com");
        email.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        email.setSize(email.getPreferredSize());
        StocksManager.setCenterAlign(this, email, 0, 35);
        add(email);

    }

}
